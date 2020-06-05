package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.models.SequenceItem;

import java.util.List;

public interface SequencesRepository extends JpaRepository<SequenceItem, Long> {

    @Query("select id from SequenceItem where taskId = :taskId order by itemOrder asc")
    List<Long> findIdsByTaskIdOrderByItemOrderAsc(Long taskId);

    void deleteAllByTaskId(Long taskId);
}
