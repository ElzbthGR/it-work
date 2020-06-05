package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.models.CompilerTemplate;

import java.util.Optional;

public interface CompilerTemplatesRepository extends JpaRepository<CompilerTemplate, Long> {

    @Query("select baseTemplate from CompilerTemplate where compilerId = :compilerId")
    Optional<String> findTemplateByCompilerId(Long compilerId);
}
