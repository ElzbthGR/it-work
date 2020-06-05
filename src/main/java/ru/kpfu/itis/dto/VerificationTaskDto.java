package ru.kpfu.itis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.kpfu.itis.models.Task;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VerificationTaskDto extends VerificationTaskShortDto {
    private String description;
    private Task.Type type;
    private List<FileDto> files;
    private FileDto userFile;
}
