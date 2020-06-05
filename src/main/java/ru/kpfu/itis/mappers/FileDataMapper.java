package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.FileDto;
import ru.kpfu.itis.models.FileData;

import javax.annotation.Nullable;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class FileDataMapper extends DtoMapper<FileData, FileDto> {

    @Nullable
    @Override
    public FileDto apply(@Nullable FileData file) {
        if (file == null) {
            return null;
        }
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setPath(file.getPath());
        dto.setContentType(file.getContentType());
        dto.setFile(file.getFile());
        return dto;
    }
}
