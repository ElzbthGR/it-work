package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.kpfu.itis.models.Task;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto extends TaskShortDto {
    private String description;
    private Task.RoleType roleType;
    private Task.Type type;
    private List<FileDto> files;
    private List<AnswerDto> answers;
    private List<SequenceItemDto> sequenceItems;
    private CodeDto code;
    private ChatDto chat;
}
