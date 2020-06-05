package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeResultDto {
    public static final String ACCEPTED_STATUS = "accepted";

    String compilationLog;
    String outputLog;
    String errorLog;
    String sourceLog;
    String status;
    Double time;
}
