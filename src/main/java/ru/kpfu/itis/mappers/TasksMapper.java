package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.models.ChatMessage;
import ru.kpfu.itis.models.Code;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.ChatMessagesRepository;
import ru.kpfu.itis.services.TasksService;
import ru.kpfu.itis.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TasksMapper extends DtoMapper<Task, TaskDto> {

    private final ChatMessagesRepository chatMessagesRepository;

    private final FileDataMapper fileDataMapper;
    private final AnswersMapper answersMapper;
    private final SequenceItemsMapper sequenceItemsMapper;
    private final TasksShortMapper tasksShortMapper;
    private final CodesMapper codesMapper;
    private final ChatMessagesMapper chatMessagesMapper;

    @Nullable
    @Override
    public TaskDto apply(@Nullable Task task) {
        if (task == null) {
            return null;
        }
        TaskDto dto = tasksShortMapper.apply(task,TaskDto::new);
        if (dto == null) {
            return null;
        }
        List<SequenceItemDto> sequenceItemDtos = sequenceItemsMapper.apply(task.getSequenceItems());
        Collections.shuffle(sequenceItemDtos);
        List<AnswerDto> answerDtos = answersMapper.apply(task.getAnswers());
        Collections.shuffle(answerDtos);
        Optional<CodeDto> codeDto = codesMapper.apply(task.getCodes())
                .stream()
                .filter(code -> code.getCompiler().getId().equals(Code.DEFAULT_COMPILER_ID))
                .findFirst();
        Optional<ChatMessage> chatMessage = Optional.empty();
        if (!CollectionUtils.isEmpty(task.getChatMessages())) {
            chatMessage = task.getChatMessages()
                    .stream()
                    .filter(ChatMessage::getStart)
                    .findFirst();
        }
        ChatMessageDto chatMessageDto = chatMessage.map(chatMessagesMapper::apply).orElse(null);
        ChatDto chatDto = new ChatDto();
        chatDto.setStartMessage(chatMessageDto);
        chatDto.setUserMessages(chatMessagesRepository.findUserMessageByTaskId(task.getId())
                .stream().filter(StringUtils::isNotBlank)
                .collect(Collectors.toList())
        );
        dto.setDescription(task.getDescription());
        dto.setType(task.getType());
        dto.setRoleType(task.getRoleType());
        dto.setFiles(fileDataMapper.apply(task.getFiles()));
        dto.setAnswers(answerDtos);
        dto.setSequenceItems(sequenceItemDtos);
        dto.setCode(codeDto.orElse(null));
        dto.setChat(chatDto);
        return dto;
    }
}
