package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.UserTask;

public interface UserTasksRepository extends JpaRepository<UserTask, UserTask.Id> {
}
