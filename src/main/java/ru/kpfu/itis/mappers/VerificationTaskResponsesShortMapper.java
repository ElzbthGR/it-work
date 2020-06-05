package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.VerificationTaskResponsesShortDto;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;
import ru.kpfu.itis.utils.DateTimeUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class VerificationTaskResponsesShortMapper extends DtoShortMapper<VerificationTaskResponsesProjection, VerificationTaskResponsesShortDto> {

    @Nullable
    @Override
    public <SD extends VerificationTaskResponsesShortDto> SD apply(@Nullable VerificationTaskResponsesProjection projection,
                                                                   @Nonnull Supplier<SD> dtoSupplier) {
        if (projection == null) {
            return null;
        }
        SD dto = dtoSupplier.get();
        dto.setTaskId(projection.getTaskId());
        dto.setTitle(projection.getTitle());
        dto.setRoleType(projection.getRoleType());
        dto.setSendDateTimeMillis(DateTimeUtils.toMillis(projection.getSendDateTime()));
        dto.setType(projection.getType());
        dto.setPassed(projection.getPassed());
        dto.setCode(projection.getCode());
        return dto;
    }

    @Nullable
    @Override
    public VerificationTaskResponsesShortDto apply(@Nullable VerificationTaskResponsesProjection projection) {
        return apply(projection, VerificationTaskResponsesShortDto::new);
    }
}
