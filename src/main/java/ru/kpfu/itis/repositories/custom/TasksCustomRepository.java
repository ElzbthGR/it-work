package ru.kpfu.itis.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dto.CodeDto;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.dto.projection.CompilerProjection;
import ru.kpfu.itis.models.Task;

import java.util.List;

@Repository
public interface TasksCustomRepository {

    Page<Task> page(Task.RoleType roleType, PageableForm form);

    Page<Task> page(Long userId, PageableForm form);

    List<CompilerProjection> findCodeCompilerIdAndCompilerNameByTaskId(Long taskId);
}
