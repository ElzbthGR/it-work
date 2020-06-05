package ru.kpfu.itis.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.models.FileData;
import ru.kpfu.itis.models.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class VerificationTaskProjection {
    private Long userId;
    private Long taskId;
    private String email;
    private String title;
    private Task.RoleType roleType;
    private LocalDateTime sendDateTime;
    private UUID code;
    private String description;
    private Task.Type type;
    private List<FileData> files;
    private FileData userFile;

    public VerificationTaskProjection(Long userId, Long taskId, String email, String title,
                                      Task.RoleType roleType, LocalDateTime sendDateTime,
                                      UUID code, String description, Task.Type type, FileData userFile) {
        this.userId = userId;
        this.taskId = taskId;
        this.email = email;
        this.title = title;
        this.roleType = roleType;
        this.sendDateTime = sendDateTime;
        this.code = code;
        this.description = description;
        this.type = type;
        this.userFile = userFile;
    }
}
