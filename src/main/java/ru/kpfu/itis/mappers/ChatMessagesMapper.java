package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.ChatMessageDto;
import ru.kpfu.itis.models.ChatMessage;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class ChatMessagesMapper extends DtoMapper<ChatMessage, ChatMessageDto> {

    @Nullable
    @Override
    public ChatMessageDto apply(@Nullable ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        ChatMessageDto dto = new ChatMessageDto();
        dto.setUserMessage(chatMessage.getUserMessage());
        dto.setServerMessage(chatMessage.getServerMessage());
        return dto;
    }
}
