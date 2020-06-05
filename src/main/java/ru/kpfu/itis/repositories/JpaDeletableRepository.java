package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.AbstractAuditableDeletableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaDeletableRepository<E extends AbstractAuditableDeletableEntity, ID> extends JpaRepository<E, ID> {

    boolean existsByIdAndDeletedFalse(ID id);

    Optional<E> findByIdAndDeletedFalse(ID id);

    List<E> findAllByDeletedFalse();

    List<E> findAllByIdInAndDeletedFalse(Iterable<ID> ids);
}
