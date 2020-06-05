package ru.kpfu.itis.mappers;

import ru.kpfu.itis.dto.EnumEntityDto;
import ru.kpfu.itis.models.AbstractEnumEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class EnumEntitiesMapper<E extends AbstractEnumEntity> extends DtoMapper<E, EnumEntityDto> {

    @Nullable
    @Override
    public EnumEntityDto apply(@Nullable E enumEntity) {
        if (enumEntity == null) {
            return null;
        }
        EnumEntityDto json = new EnumEntityDto();
        json.setId(enumEntity.getId());
        json.setName(enumEntity.getName());
        return json;
    }
}
