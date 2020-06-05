package ru.kpfu.itis.mappers;

import ru.kpfu.itis.dto.PageableListDto;
import ru.kpfu.itis.utils.ListFunction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PageableListMapper<E, J> {

    public PageableListDto<J> apply(Page<E> page, ListFunction<E, J> listMapper) {
        PageableListDto<J> json = new PageableListDto<>();
        json.setData(listMapper.apply(page.getContent()));
        json.setPage(page);
        return json;
    }

    public PageableListDto<J> simpleApply(Page<E> page, Function<E, J> mapper) {
        PageableListDto<J> json = new PageableListDto<>();
        json.setData(
                page.getContent()
                        .stream()
                        .map(mapper)
                        .collect(Collectors.toList())
        );
        json.setPage(page);
        return json;
    }
}
