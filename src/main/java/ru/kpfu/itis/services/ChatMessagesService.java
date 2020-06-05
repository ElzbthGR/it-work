package ru.kpfu.itis.services;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.ChatMessageResponseDto;
import ru.kpfu.itis.dto.forms.ChatMessageForm;
import ru.kpfu.itis.dto.forms.MessageSendForm;
import ru.kpfu.itis.dto.forms.TaskForm;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.exceptions.ValidationException;
import ru.kpfu.itis.models.ChatMessage;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.ChatMessagesRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessagesService {

    private final ChatMessagesRepository chatMessagesRepository;

    public Task addChatMessage(final Task task, final List<ChatMessageForm> forms) {
        log.info("Adding chat messages to task with id {}", task.getId());
        if (!task.getType().equals(Task.Type.CHAT)) {
            throw new ValidationException("Type does not match", TaskForm.TYPE_FIELD);
        }
        if (forms == null) {
            return null;
        }
        List<ChatMessage> messages = task.getChatMessages();
        validateChatMessages(forms);
        if (!CollectionUtils.isEmpty(messages)) {
            messages.clear();
        }
        List<ChatMessage> newMessages = Lists.newArrayList();
        forms.forEach(form -> newMessages.add(
                ChatMessage.builder()
                        .userMessage(form.getUserMessage())
                        .serverMessage(form.getServerMessage())
                        .taskId(task.getId())
                        .rightMessage(form.getRight())
                        .start(form.getStart())
                        .build()
        ));
        chatMessagesRepository.saveAll(newMessages);
        task.setChatMessages(newMessages);
        return task;
    }

    private void validateChatMessages(final List<ChatMessageForm> forms) {
        long countRight = forms.stream().filter(ChatMessageForm::getRight).count();
        if (countRight != 1) {
            throw  new ValidationException("Messages must contain one right answer", ChatMessageForm.RIGHT_FIELD);
        }
        long countStart = forms.stream().filter(ChatMessageForm::getStart).count();
        if (countStart != 1 && countStart != 0) {
            throw  new ValidationException("Messages must contain none or one start message", ChatMessageForm.RIGHT_FIELD);
        }
    }

    public ChatMessageResponseDto checkChatMessages(final Long taskId, final MessageSendForm form) {
        log.info("Checking chat messages with user message \"{}\" for task with id {}", form.getUserMessage(), taskId);
        ChatMessage chatMessage = chatMessagesRepository.findByTaskIdAndUserMessage(taskId, form.getUserMessage())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Chat message by task with id %s and user message \"%s\"", taskId, form.getUserMessage()))
                );
        ChatMessageResponseDto dto = new ChatMessageResponseDto();
        dto.setServerMessage(chatMessage.getServerMessage());
        dto.setRightAnswer(chatMessage.getRightMessage());
        return dto;
    }

    @Transactional
    public void removeAll(final Long taskId) {
        log.info("Removing all chat messages by task with id {}", taskId);
        chatMessagesRepository.deleteAllByTaskId(taskId);
    }
}
