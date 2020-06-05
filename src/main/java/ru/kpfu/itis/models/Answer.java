package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "answers_seq", allocationSize = 1)
public class Answer extends AbstractAuditableEntity {
    public static final String TASK_ID_COLUMN = "task_id";

    @Column(name = TASK_ID_COLUMN)
    private Long taskId;

    private String explanation;
    private String description;

    @Builder.Default
    private Boolean correct = Boolean.FALSE;
}
