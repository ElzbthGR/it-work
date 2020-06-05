package ru.kpfu.itis.mappers;

import com.google.common.base.Function;
import ru.kpfu.itis.dto.PageableListDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DtoMapper<E, D> implements Function<E, D> {

    public List<D> apply(List<E> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items
                .stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    public PageableListDto<D> apply(Page<E> page) {
        PageableListDto<D> dto = new PageableListDto<>();
        dto.setData(apply(page.getContent()));
        dto.setPage(page);
        return dto;
    }
}
