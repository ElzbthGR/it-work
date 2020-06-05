package ru.kpfu.itis.mappers;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.VerificationTaskShortDto;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;
import ru.kpfu.itis.utils.DateTimeUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

@Component
public class VerificationTasksShortMapper extends DtoShortMapper<VerificationTaskProjection, VerificationTaskShortDto> {

    @Nullable
    @Override
    public <SD extends VerificationTaskShortDto> SD apply(@Nullable VerificationTaskProjection projection,
                                                          @Nonnull Supplier<SD> dtoSupplier) {
        if (projection == null) {
            return null;
        }
        SD dto = dtoSupplier.get();
        dto.setCode(projection.getCode());
        dto.setEmail(projection.getEmail());
        dto.setRoleType(projection.getRoleType());
        dto.setSendDateTime(DateTimeUtils.toMillis(projection.getSendDateTime()));
        dto.setTaskId(projection.getTaskId());
        dto.setUserId(projection.getUserId());
        dto.setTitle(projection.getTitle());
        return dto;
    }

    @Nullable
    @Override
    public VerificationTaskShortDto apply(@Nullable VerificationTaskProjection projection) {
        return apply(projection, VerificationTaskShortDto::new);
    }
}
