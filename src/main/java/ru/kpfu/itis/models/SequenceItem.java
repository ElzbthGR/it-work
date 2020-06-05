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
@Table(name = "sequence_items")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "sequence_items_seq", allocationSize = 1)
public class SequenceItem extends AbstractAuditableEntity{
    public static final String TASK_ID_COLUMN = "task_id";

    @Column(name = TASK_ID_COLUMN)
    private Long taskId;
    private Long itemOrder;
    private String description;
}
