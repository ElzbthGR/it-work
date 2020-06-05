package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kpfu.itis.models.FileData;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByUuid(String uuid);

    List<FileData> findAllByIdAndDeletedFalse(List<Long> fileIds);

    @Modifying
    @Query("update FileData set used = true where id = :fileId and deleted = false")
    void markAsUsed(Long fileId);

    @Modifying
    @Query("update FileData set used = true where id in :fileIds and deleted = false")
    void markAllAsUsed(List<Long> fileIds);
}
