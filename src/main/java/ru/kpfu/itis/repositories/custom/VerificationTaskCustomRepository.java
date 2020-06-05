package ru.kpfu.itis.repositories.custom;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;

import java.util.UUID;

@Repository
public interface VerificationTaskCustomRepository {

    Page<VerificationTaskProjection> page(final PageableForm form);

    Page<VerificationTaskResponsesProjection> pageResponses(final Long userId, final PageableForm form);

    VerificationTaskProjection get(final UUID code);

    VerificationTaskResponsesProjection getResponse(final UUID code);
}
