package ru.kpfu.itis.repositories.custom;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.dto.projection.CompilerProjection;
import ru.kpfu.itis.models.QCode;
import ru.kpfu.itis.models.QTask;
import ru.kpfu.itis.models.QUserTask;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.custom.helpers.RepositoryHelper;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class TasksCustomRepositoryImpl implements TasksCustomRepository {

    private final QTask qTask = QTask.task;
    private final QUserTask qUserTask = QUserTask.userTask;
    private final QCode qCode = QCode.code;

    private final EntityManager entityManager;

    @Override
    public Page<Task> page(Task.RoleType roleType, PageableForm form) {
        JPAQuery<Task> query = new JPAQuery<>(entityManager)
                .select(qTask)
                .from(qTask)
                .where(qTask.roleType.eq(roleType))
                .orderBy(qTask.id.desc())
                .limit(form.getLimit())
                .offset(form.getOffset());
        return RepositoryHelper.pageBy(query, form);
    }

    @Override
    public Page<Task> page(Long userId, PageableForm form) {
        JPAQuery<Task> query = new JPAQuery<>(entityManager)
                .select(qTask)
                .from(qUserTask)
                .leftJoin(qTask)
                .on(qUserTask.id.taskId.eq(qTask.id))
                .where(qUserTask.id.userId.eq(userId))
                .limit(form.getLimit())
                .offset(form.getOffset());
        return RepositoryHelper.pageBy(query, form);
    }

    @Override
    public List<CompilerProjection> findCodeCompilerIdAndCompilerNameByTaskId(Long taskId) {
        Expression<CompilerProjection> projection = Projections.constructor(
                CompilerProjection.class,
                qCode.compilerId,
                qCode.compilerName
        );
        JPAQuery<CompilerProjection> query = new JPAQuery<>(entityManager)
                .select(projection)
                .from(qTask)
                .join(qCode)
                .on(qTask.id.eq(qCode.taskId))
                .where(qTask.id.eq(taskId));
        return query.fetch();
    }
}
