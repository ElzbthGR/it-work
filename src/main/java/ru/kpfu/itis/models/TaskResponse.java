package ru.kpfu.itis.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_responses")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "task_responses_seq", allocationSize = 1)
public class TaskResponse extends AbstractAuditableDeletableEntity {
    private String text;
    private Boolean passed;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = MediaFile.TABLE_NAME,
            joinColumns = @JoinColumn(name = MediaFile.TASK_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = MediaFile.MEDIA_FILE_ID_COLUMN))
    private List<FileData> files;

    @Data
    @Entity
    @Builder
    @Table(name = TaskResponse.MediaFile.TABLE_NAME)
    public static class MediaFile implements Serializable {

        public static final String TABLE_NAME = "task_response_media_file";
        public static final String TASK_ID_COLUMN = "task_response_id";
        public static final String MEDIA_FILE_ID_COLUMN = "media_file_id";

        @Id
        @Column(name = TaskResponse.MediaFile.TASK_ID_COLUMN)
        private Long taskResponseId;

        @Id
        @ManyToOne
        @JoinColumn(name = TaskResponse.MediaFile.MEDIA_FILE_ID_COLUMN)
        private FileData file;
    }
}
