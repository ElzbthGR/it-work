package ru.kpfu.itis.mappers;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.TaskShortDto;
import ru.kpfu.itis.models.Task;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

@Component
public class TasksShortMapper extends DtoShortMapper<Task, TaskShortDto> {

    @Nullable
    @Override
    public <SD extends TaskShortDto> SD apply(@Nullable Task task, @Nonnull Supplier<SD> dtoSupplier) {
        if (task == null) {
            return null;
        }
        SD dto = dtoSupplier.get();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        return dto;
    }

    @Nullable
    @Override
    public TaskShortDto apply(@Nullable Task task) {
        return apply(task, TaskShortDto::new);
    }
}
