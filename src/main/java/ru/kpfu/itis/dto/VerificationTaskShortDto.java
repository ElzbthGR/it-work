package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.Task;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationTaskShortDto {
    private Long userId;
    private Long taskId;
    private String email;
    private String title;
    private Task.RoleType roleType;
    private Long sendDateTime;
    private UUID code;
}
