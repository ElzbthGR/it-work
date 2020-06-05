package ru.kpfu.itis.services;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.PageableListDto;
import ru.kpfu.itis.dto.RoleTypeDto;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.models.Task;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesService {

    public PageableListDto<RoleTypeDto> page(final PageableForm form) {
        log.info("Page role types");
        List<RoleTypeDto> dtos = Lists.newArrayList();
        for (Task.RoleType roleType : Task.RoleType.values()) {
            dtos.add(new RoleTypeDto(roleType, roleType.getName()));
        }
        Page<RoleTypeDto> page = new PageImpl<>(dtos, form.getPageRequest(), dtos.size());
        PageableListDto<RoleTypeDto> dto = new PageableListDto<>();
        dto.setData(dtos);
        dto.setPage(page);
        return dto;
    }
}
