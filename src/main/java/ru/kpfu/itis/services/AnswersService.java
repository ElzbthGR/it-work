package ru.kpfu.itis.services;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.ExplanationDto;
import ru.kpfu.itis.dto.TestResponseDto;
import ru.kpfu.itis.dto.forms.AnswerForm;
import ru.kpfu.itis.dto.forms.TaskForm;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.models.Answer;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.AnswersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswersService {

    private final AnswersRepository answersRepository;

    public Task addAnswers(final Task task, final List<AnswerForm> forms) {
        log.info("Adding answers to task with id {}", task.getId());
        if (!task.getType().equals(Task.Type.TEST)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        if (forms == null) {
            return null;
        }
        List<Answer> answers = task.getAnswers();
        if (!CollectionUtils.isEmpty(answers)) {
            answers.clear();
        }
        List<Answer> newAnswers = Lists.newArrayList();
        forms.forEach(form -> newAnswers.add(
                Answer.builder()
                        .description(form.getDescription())
                        .correct(form.getCorrect())
                        .explanation(form.getExplanation())
                        .taskId(task.getId())
                        .build()
                )
        );
        answersRepository.saveAll(newAnswers);
        task.setAnswers(newAnswers);
        return task;
    }

    public TestResponseDto checkAnswers(final Long taskId, final List<Long> answerIds) {
        log.info("Checking answers with ids {} for task with id {}", answerIds, taskId);
        if (CollectionUtils.isEmpty(answerIds)) {
            throw new ValidationException("There are no answers", Answer.ID_COLUMN_NAME);
        }
        answerIds.forEach(answerId -> {
            if (answerId <= 0) {
                throw new ValidationException("Ids cannot be <= 0", Answer.ID_COLUMN_NAME);
            }
        });
        List<Long> rightAnswerIds = answersRepository.findIdsByTaskIdAndCorrectTrue(taskId);
        if (CollectionUtils.isEmpty(rightAnswerIds)) {
            throw new NotFoundException(String.format("Task with id %s not contains correct answers", taskId));
        }
        boolean rightAnswer = CollectionUtils.isEqualCollection(rightAnswerIds, answerIds);
        List<Answer> answers = getAll(taskId);
        List<ExplanationDto> explanations = null;
        TestResponseDto dto = new TestResponseDto();
        dto.setRightAnswer(rightAnswer);
        if (rightAnswer) {
            explanations = answers.stream()
                    .map(answer -> new ExplanationDto(answer.getId(), answer.getExplanation()))
                    .collect(Collectors.toList());
        }
        dto.setExplanations(explanations);
        return dto;
    }

    public List<Answer> getAll(final Long taskId) {
        log.info("Getting all answers by task with id {}", taskId);
        return answersRepository.findAllByTaskId(taskId);
    }

    @Transactional
    public void removeAll(final Long taskId) {
        log.info("Removing all answers by task with id {}", taskId);
        answersRepository.deleteAllByTaskId(taskId);
    }
}
