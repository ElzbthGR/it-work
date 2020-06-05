package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private ChatMessageDto startMessage;
    private List<String> userMessages;
}
