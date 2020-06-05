package ru.kpfu.itis.repositories.custom;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.dto.projection.VerificationTaskProjection;
import ru.kpfu.itis.dto.projection.VerificationTaskResponsesProjection;
import ru.kpfu.itis.models.*;
import ru.kpfu.itis.repositories.custom.helpers.RepositoryHelper;

import javax.persistence.EntityManager;
import java.util.UUID;

@RequiredArgsConstructor
public class VerificationTaskCustomRepositoryImpl implements VerificationTaskCustomRepository {

    private final QFileData qFileData = QFileData.fileData;
    private final QTask_VerificationTask qVerificationTask = QTask_VerificationTask.verificationTask;
    private final QUser qUser = QUser.user;
    private final QTask qTask = QTask.task;
    private final QTaskResponse qTaskResponse = QTaskResponse.taskResponse;
    private final QTaskResponse_MediaFile qTaskResponseMediaFile = QTaskResponse_MediaFile.mediaFile;

    private final EntityManager entityManager;

    @Override
    public Page<VerificationTaskProjection> page(PageableForm form) {
        Expression<VerificationTaskProjection> taskProjection = Projections.constructor(
                VerificationTaskProjection.class,
                qVerificationTask.id.userId,
                qVerificationTask.id.taskId,
                qUser.email,
                qTask.title,
                qTask.roleType,
                qVerificationTask.sendDateTime,
                qVerificationTask.code,
                qTask.description,
                qTask.type,
                qFileData

        );
        final JPAQuery<VerificationTaskProjection> query = new JPAQuery<>(entityManager)
                .select(taskProjection)
                .from(qVerificationTask)
                .join(qTask)
                .on(qTask.id.eq(qVerificationTask.id.taskId))
                .join(qFileData)
                .on(qFileData.id.eq(qVerificationTask.id.fileId))
                .join(qUser)
                .on(qUser.id.eq(qVerificationTask.id.userId))
                .where(qVerificationTask.taskResponse.isNull())
                .orderBy(qVerificationTask.sendDateTime.desc())
                .limit(form.getLimit())
                .offset(form.getOffset());
        Page<VerificationTaskProjection> page = RepositoryHelper.pageBy(query, form);
        if (page.getTotalElements() != 0) {
            page.getContent()
                    .forEach(this::setFiles);
        }
        return page;
    }

    @Override
    public Page<VerificationTaskResponsesProjection> pageResponses(Long userId, PageableForm form) {
        Expression<VerificationTaskResponsesProjection> projection = Projections.constructor(
                VerificationTaskResponsesProjection.class,
                qVerificationTask.id.userId,
                qVerificationTask.id.taskId,
                qUser.email,
                qTask.title,
                qTask.roleType,
                qVerificationTask.sendDateTime,
                qVerificationTask.code,
                qTask.description,
                qTask.type,
                qFileData,
                qTaskResponse.text,
                qTaskResponse.id,
                qTaskResponse.passed
        );
        final JPAQuery<VerificationTaskResponsesProjection> query = new JPAQuery<>(entityManager)
                .select(projection)
                .from(qVerificationTask)
                .join(qTask)
                .on(qTask.id.eq(qVerificationTask.id.taskId))
                .join(qFileData)
                .on(qFileData.id.eq(qVerificationTask.id.fileId))
                .join(qUser)
                .on(qUser.id.eq(qVerificationTask.id.userId))
                .join(qTaskResponse)
                .on(qVerificationTask.taskResponseId.eq(qTaskResponse.id))
                .where(qVerificationTask.taskResponse.isNotNull()
                        .and(qVerificationTask.id.userId.eq(userId)))
                .orderBy(qVerificationTask.sendDateTime.desc())
                .limit(form.getLimit())
                .offset(form.getOffset());
        Page<VerificationTaskResponsesProjection> page = RepositoryHelper.pageBy(query, form);
        if (page.getTotalElements() != 0) {
            page.getContent()
                    .forEach(p -> {
                        setFiles(p);
                        setResponseFiles(p);
                    });
        }
        return page;
    }

    @Override
    public VerificationTaskProjection get(UUID code) {
        Expression<VerificationTaskProjection> taskProjection = Projections.constructor(
                VerificationTaskProjection.class,
                qVerificationTask.id.userId,
                qVerificationTask.id.taskId,
                qUser.email,
                qTask.title,
                qTask.roleType,
                qVerificationTask.sendDateTime,
                qVerificationTask.code,
                qTask.description,
                qTask.type,
                qFileData
        );
        final JPAQuery<VerificationTaskProjection> query = new JPAQuery<>(entityManager)
                .select(taskProjection)
                .from(qVerificationTask)
                .join(qTask)
                .on(qTask.id.eq(qVerificationTask.id.taskId))
                .join(qFileData)
                .on(qFileData.id.eq(qVerificationTask.id.fileId))
                .join(qUser)
                .on(qUser.id.eq(qVerificationTask.id.userId))
                .where(qVerificationTask.code.eq(code));
        VerificationTaskProjection verificationTaskProjection = query.fetchOne();
        if (verificationTaskProjection != null) {
            setFiles(verificationTaskProjection);
        }
        return verificationTaskProjection;
    }

    @Override
    public VerificationTaskResponsesProjection getResponse(UUID code) {
        Expression<VerificationTaskResponsesProjection> projection = Projections.constructor(
                VerificationTaskResponsesProjection.class,
                qVerificationTask.id.userId,
                qVerificationTask.id.taskId,
                qUser.email,
                qTask.title,
                qTask.roleType,
                qVerificationTask.sendDateTime,
                qVerificationTask.code,
                qTask.description,
                qTask.type,
                qFileData,
                qTaskResponse.text,
                qTaskResponse.id,
                qTaskResponse.passed
        );
        final JPAQuery<VerificationTaskResponsesProjection> query = new JPAQuery<>(entityManager)
                .select(projection)
                .from(qVerificationTask)
                .join(qTask)
                .on(qTask.id.eq(qVerificationTask.id.taskId))
                .join(qFileData)
                .on(qFileData.id.eq(qVerificationTask.id.fileId))
                .join(qUser)
                .on(qUser.id.eq(qVerificationTask.id.userId))
                .join(qTaskResponse)
                .on(qVerificationTask.taskResponseId.eq(qTaskResponse.id))
                .where(qVerificationTask.taskResponse.isNotNull()
                        .and(qVerificationTask.code.eq(code)));
        VerificationTaskResponsesProjection verificationTaskResponsesProjection = query.fetchOne();
        if (verificationTaskResponsesProjection != null) {
            setFiles(verificationTaskResponsesProjection);
            setResponseFiles(verificationTaskResponsesProjection);
        }
        return verificationTaskResponsesProjection;
    }

    private void setFiles(final VerificationTaskProjection projection) {
        final JPAQuery<FileData> query = new JPAQuery<FileData>(entityManager)
                .from(qFileData)
                .join(qVerificationTask)
                .on(qFileData.id.eq(qVerificationTask.id.taskId))
                .where(qFileData.id.eq(projection.getTaskId()));
        projection.setFiles(query.fetch());
    }

    private void setResponseFiles(final VerificationTaskResponsesProjection projection) {
        final JPAQuery<FileData> query = new JPAQuery<FileData>(entityManager)
                .from(qFileData)
                .join(qTaskResponseMediaFile)
                .on(qFileData.id.eq(qTaskResponseMediaFile.file.id))
                .where(qTaskResponseMediaFile.taskResponseId.eq(projection.getTaskResponseId()));
        projection.setResponseFiles(query.fetch());
    }
}
