package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "codes")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "codes_seq", allocationSize = 1)
public class Code extends AbstractAuditableEntity {
    public static final String TASK_ID_COLUMN = "task_id";
    public static final Long DEFAULT_COMPILER_ID = 10L;

    @Column(name = TASK_ID_COLUMN)
    private Long taskId;

    private Long compilerId;
    private String compilerName;
    private String template;
    private String output;

    @Builder.Default
    private Boolean accepted = Boolean.TRUE;
}
