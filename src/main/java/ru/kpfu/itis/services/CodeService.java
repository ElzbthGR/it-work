package ru.kpfu.itis.services;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.CodeResultDto;
import ru.kpfu.itis.dto.forms.CodeForm;
import ru.kpfu.itis.dto.forms.TaskForm;
import ru.kpfu.itis.dto.sphereEngine.CompilerDto;
import ru.kpfu.itis.dto.sphereEngine.SubmissionDto;
import ru.kpfu.itis.dto.sphereEngine.SubmissionForm;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.models.Code;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.models.UserTask;
import ru.kpfu.itis.repositories.CodesRepository;
import ru.kpfu.itis.repositories.CompilerTemplatesRepository;
import ru.kpfu.itis.repositories.UserTasksRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {

    private final SphereEngineService sphereEngineService;

    private final CodesRepository codesRepository;
    private final CompilerTemplatesRepository compilerTemplatesRepository;
    private final UserTasksRepository userTasksRepository;

    public Task addCodes(final Task task, final List<CodeForm> forms) {
        log.info("Adding codes to task with id {}", task.getId());
        if (!task.getType().equals(Task.Type.CODE)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        if (forms == null) {
            return null;
        }
        List<Code> codes = task.getCodes();
        if (!CollectionUtils.isEmpty(codes)) {
            codes.clear();
        }
        List<Code> newCodes = Lists.newArrayList();
        forms.forEach(form -> newCodes.add(
                Code.builder()
                        .accepted(form.getAccepted())
                        .compilerId(form.getCompilerId())
                        .compilerName(getCompilerName(form.getCompilerId()))
                        .output(form.getOutput())
                        .template(form.getTemplate())
                        .taskId(task.getId())
                        .build()
                )
        );
        codesRepository.saveAll(newCodes);
        task.setCodes(newCodes);
        return task;
    }

    public void passTask(final Long userId, final Long taskId) {
        log.info("Passing task with id {} for user with id {}", taskId, userId);
        UserTask.Id id = UserTask.Id.builder()
                .taskId(taskId)
                .userId(userId)
                .build();
        UserTask userTask = UserTask.builder()
                .id(id)
                .build();
        userTasksRepository.save(userTask);
    }

    public CodeResultDto checkCode(final Long taskId, final Long userId, final SubmissionForm form) {
        log.info("Checking code for task with id {}", taskId);
        if (form == null) {
            return null;
        }
        CodeResultDto result = getResult(form);
        Code code = get(taskId, form.getCompilerId());
        if (code.getOutput() == null) {
            if (result.getStatus().equals(CodeResultDto.ACCEPTED_STATUS)) {
                passTask(userId, taskId);
            }
        } else {
            if (code.getOutput().equals(result.getOutputLog())) {
                passTask(userId, taskId);
            }
        }
        return result;
    }

    public CodeResultDto getResult(final SubmissionForm form) {
        log.info("Getting result of task with type CODE");
        String compilationLog = null;
        String outputLog = null;
        String errorLog = null;
        String sourceLog = null;
        Long submissionId = sphereEngineService.getSubmissionId(form);
        SubmissionDto submission = sphereEngineService.get(submissionId);
        while (submission.isExecuting()) {
            submission = sphereEngineService.get(submissionId);
        }
        if (submission.getResult().getStreams().getCmpinfo() != null) {
            compilationLog = sphereEngineService.get(submission.getResult().getStreams().getCmpinfo().getUri());
        }
        if (submission.getResult().getStreams().getOutput() != null) {
            outputLog = sphereEngineService.get(submission.getResult().getStreams().getOutput().getUri());
        }
        if (submission.getResult().getStreams().getError() != null) {
            errorLog = sphereEngineService.get(submission.getResult().getStreams().getError().getUri());
        }
        if (submission.getResult().getStreams().getSource() != null) {
            sourceLog = sphereEngineService.get(submission.getResult().getStreams().getSource().getUri());
        }
        CodeResultDto dto = CodeResultDto.builder()
                .compilationLog(compilationLog)
                .outputLog(outputLog)
                .errorLog(errorLog)
                .sourceLog(sourceLog)
                .time(submission.getResult().getTime())
                .status(submission.getResult().getStatus().getName())
                .build();
        return dto;
    }

    public List<CompilerDto> getCompilers() {
        log.info("Getting all compilers");
        return sphereEngineService.getCompilers();
    }

    public Code get(final Long taskId, final Long compilerId) {
        log.info("Getting code by task with id {} and compiler with id {}", taskId, compilerId);
        return codesRepository.findByTaskIdAndCompilerId(taskId, compilerId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Code by task with id %s and compiler with id %s not found", taskId, compilerId))
                );
    }

    public String getCompilerName(final Long compilerId) {
        log.info("Getting compiler name by id {}", compilerId);
        CompilerDto compilerDto = getCompilers().stream()
                .filter(compiler -> compiler.getId().equals(compilerId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Compiler with id %s not found", compilerId)));
        return compilerDto.getName();
    }

    public String getBaseTemplate(final Long compilerId) {
        log.info("Getting base template by compiler with id {}", compilerId);
        return compilerTemplatesRepository.findTemplateByCompilerId(compilerId)
                .orElseThrow(() -> new NotFoundException(String.format("Template by compilerId %s not found", compilerId)));
    }

    @Transactional
    public void removeAll(final Long taskId) {
        log.info("Removing all codes by task with id {}", taskId);
        codesRepository.deleteAllByTaskId(taskId);
    }
}
