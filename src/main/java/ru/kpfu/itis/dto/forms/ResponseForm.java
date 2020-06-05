package ru.kpfu.itis.dto.forms;

import lombok.Data;

import java.util.List;

@Data
public class ResponseForm {
    private List<Long> answerIds;
    private List<Long> sequenceItemIds;
    private Long fileId;
}
