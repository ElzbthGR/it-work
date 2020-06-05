package ru.kpfu.itis.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTaskResponsesDto extends VerificationTaskResponsesShortDto{
    private String text;
    private List<FileDto> responseFiles;
    private FileDto userFile;
    private List<FileDto> files;
    private String description;
}
