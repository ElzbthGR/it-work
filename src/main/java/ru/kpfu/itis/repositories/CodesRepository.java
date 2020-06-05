package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Code;

import java.util.Optional;

public interface CodesRepository extends JpaRepository<Code, Long> {

    void deleteAllByTaskId(Long taskId);

    Optional<Code> findByTaskIdAndCompilerId(Long taskId, Long compilerId);
}
