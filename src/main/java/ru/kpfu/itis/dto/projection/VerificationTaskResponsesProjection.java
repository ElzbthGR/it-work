package ru.kpfu.itis.dto.projection;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.FileData;
import ru.kpfu.itis.models.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class VerificationTaskResponsesProjection extends VerificationTaskProjection {
    private Long taskResponseId;
    private String text;
    private List<FileData> responseFiles;
    private Boolean passed;

    public VerificationTaskResponsesProjection(Long userId, Long taskId, String email, String title,
                                               Task.RoleType roleType, LocalDateTime sendDateTime,
                                               UUID code, String description, Task.Type type, FileData userFile,
                                               String text, Long taskResponseId, Boolean passed) {
        super(userId, taskId, email, title, roleType, sendDateTime, code, description, type, userFile);
        this.text = text;
        this.taskResponseId = taskResponseId;
        this.passed = passed;
    }
}
