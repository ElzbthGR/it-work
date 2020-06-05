package ru.kpfu.itis.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.SequenceItemDto;
import ru.kpfu.itis.models.SequenceItem;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class SequenceItemsMapper extends DtoMapper<SequenceItem, SequenceItemDto> {

    @Nullable
    @Override
    public SequenceItemDto apply(@Nullable SequenceItem item) {
        if (item == null) {
            return null;
        }
        SequenceItemDto dto = new SequenceItemDto();
        dto.setId(item.getId());
        dto.setDescription(item.getDescription());
        dto.setItemOrder(item.getItemOrder());
        return dto;
    }
}
