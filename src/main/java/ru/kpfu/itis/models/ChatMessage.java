package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_messages")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "chat_messages_seq", allocationSize = 1)
public class ChatMessage extends AbstractAuditableEntity {
    public static final String TASK_ID_COLUMN = "task_id";

    @Column(name = TASK_ID_COLUMN)
    private Long taskId;
    private String userMessage;
    private String serverMessage;
    private Boolean rightMessage;
    private Boolean start;
}
