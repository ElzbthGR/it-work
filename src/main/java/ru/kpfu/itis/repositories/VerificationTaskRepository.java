package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.custom.VerificationTaskCustomRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTaskRepository extends JpaRepository<Task.VerificationTask, Task.VerificationTask.Id>, VerificationTaskCustomRepository {

    Optional<Task.VerificationTask> findByCode(UUID code);
}
