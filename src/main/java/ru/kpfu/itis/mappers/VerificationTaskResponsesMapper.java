package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.VerificationTaskResponsesDto;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class VerificationTaskResponsesMapper extends DtoMapper<VerificationTaskResponsesProjection, VerificationTaskResponsesDto> {

    private final VerificationTaskResponsesShortMapper verificationTaskResponsesShortMapper;
    private final FileDataMapper fileDataMapper;

    @Nullable
    @Override
    public VerificationTaskResponsesDto apply(@Nullable VerificationTaskResponsesProjection projection) {
        if (projection == null) {
            return null;
        }
        VerificationTaskResponsesDto dto = verificationTaskResponsesShortMapper.apply(
                projection, VerificationTaskResponsesDto::new
        );
        if (dto == null) {
            return null;
        }
        dto.setDescription(projection.getDescription());
        dto.setText(projection.getText());
        dto.setUserFile(fileDataMapper.apply(projection.getUserFile()));
        dto.setFiles(fileDataMapper.apply(projection.getFiles()));
        dto.setResponseFiles(fileDataMapper.apply(projection.getResponseFiles()));
        return dto;
    }
}
