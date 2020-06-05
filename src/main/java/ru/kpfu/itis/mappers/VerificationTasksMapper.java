package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.VerificationTaskDto;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class VerificationTasksMapper extends DtoMapper<VerificationTaskProjection, VerificationTaskDto> {

    private final VerificationTasksShortMapper verificationTasksShortMapper;
    private final FileDataMapper fileDataMapper;

    @Nullable
    @Override
    public VerificationTaskDto apply(@Nullable VerificationTaskProjection projection) {
        if (projection == null) {
            return null;
        }
        VerificationTaskDto dto = verificationTasksShortMapper.apply(projection, VerificationTaskDto::new);
        if (dto == null) {
            return null;
        }
        dto.setDescription(projection.getDescription());
        dto.setType(projection.getType());
        dto.setUserFile(fileDataMapper.apply(projection.getUserFile()));
        dto.setFiles(fileDataMapper.apply(projection.getFiles()));
        return dto;
    }
}
