package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.models.Code;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.custom.TasksCustomRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaDeletableRepository<Task, Long>, TasksCustomRepository {

    @Modifying
    @Query("update Task set deleted = true where id = :taskId")
    void markAsDeleted(Long taskId);

    @Query("select type from Task where id = :taskId and deleted = false")
    Task.Type findTypeByTaskIdAndDeletedFalse(Long taskId);

    @Query(value = "select template from tasks t join codes c on t.id = c.task_id " +
            "where c.compiler_id = :compilerId and t.id = :taskId", nativeQuery = true)
    String findTemplateByTaskIdAndCompilerId(Long taskId, Long compilerId);
}
