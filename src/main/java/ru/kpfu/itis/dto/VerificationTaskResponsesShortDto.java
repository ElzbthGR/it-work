package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Task;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTaskResponsesShortDto {
    private Long taskId;
    private String title;
    private Task.RoleType roleType;
    private Long sendDateTimeMillis;
    private Task.Type type;
    private Boolean passed;
    private UUID code;
}
