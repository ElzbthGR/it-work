package ru.kpfu.itis.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "tasks_seq", allocationSize = 1)
public class Task extends AbstractAuditableDeletableEntity {

    @Column(columnDefinition = "text")
    private String description;
    private String title;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = Answer.TASK_ID_COLUMN)
    private List<Answer> answers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = Answer.TASK_ID_COLUMN)
    private List<Code> codes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = SequenceItem.TASK_ID_COLUMN)
    private List<SequenceItem> sequenceItems;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = ChatMessage.TASK_ID_COLUMN)
    private List<ChatMessage> chatMessages;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = MediaFile.TABLE_NAME,
            joinColumns = @JoinColumn(name = MediaFile.TASK_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = MediaFile.MEDIA_FILE_ID_COLUMN))
    private List<FileData> files;

    @Data
    @Entity
    @Table(name = MediaFile.TABLE_NAME)
    public static class MediaFile implements Serializable {

        public static final String TABLE_NAME = "task_media_file";
        public static final String TASK_ID_COLUMN = "task_id";
        public static final String MEDIA_FILE_ID_COLUMN = "media_file_id";

        @Id
        @Column(name = MediaFile.TASK_ID_COLUMN)
        private Long taskId;

        @Id
        @ManyToOne
        @JoinColumn(name = MediaFile.MEDIA_FILE_ID_COLUMN)
        private FileData file;
    }

    @Data
    @Builder
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = VerificationTask.TABLE_NAME)
    public static class VerificationTask implements Serializable {

        public static final String TABLE_NAME = "verification_tasks";
        public static final String TASK_RESPONSE_ID_COLUMN = "task_response_id";

        @EmbeddedId
        private Id id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = Id.TASK_ID_COLUMN, insertable = false, updatable = false)
        private Task task;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = Id.USER_ID_COLUMN, insertable = false, updatable = false)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = Id.FILE_ID_COLUMN, insertable = false, updatable = false)
        private FileData file;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = TASK_RESPONSE_ID_COLUMN, insertable = false, updatable = false)
        private TaskResponse taskResponse;

        @Column(name = TASK_RESPONSE_ID_COLUMN)
        private Long taskResponseId;

        @Builder.Default
        @org.hibernate.annotations.Type(type = "java.time.LocalDateTime")
        private LocalDateTime sendDateTime = LocalDateTime.now();

        @Builder.Default
        @Column(columnDefinition = "uuid")
        private UUID code = UUID.randomUUID();

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @Embeddable
        public static class Id implements Serializable {

            public static final String TASK_ID_COLUMN = "task_id";
            public static final String USER_ID_COLUMN = "user_id";
            public static final String FILE_ID_COLUMN = "file_id";

            @Column(name = TASK_ID_COLUMN)
            private Long taskId;

            @Column(name = USER_ID_COLUMN)
            private Long userId;

            @Column(name =  FILE_ID_COLUMN)
            private Long fileId;
        }
    }

    @AllArgsConstructor
    public enum RoleType {
        PROGRAMMER("Программист"),
        ANALYST("Системный аналитик"),
        TESTER("Специалист по тестированию");

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum Type {
        CODE, SEQUENCE, TEST, COMMON, UML, CHAT
    }
}
