package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.models.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswersRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByTaskId(Long taskId);

    @Query("select id from Answer where taskId = :taskId and correct = true")
    List<Long> findIdsByTaskIdAndCorrectTrue(Long taskId);

    void deleteAllByTaskId(Long taskId);
}
