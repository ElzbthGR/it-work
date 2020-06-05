package ru.kpfu.itis.models;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.kpfu.itis.models.enums.FileExtension;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "files_seq", allocationSize = 1)
public class FileData extends AbstractAuditableDeletableEntity{
    private String originalName;
    private String path;
    private String uuid;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] file;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    private Long creatorId;
    private boolean used;

    @Enumerated(EnumType.STRING)
    private FileExtension extension;

    public enum ContentType {
        IMAGE, VIDEO, DOCUMENT
    }
}
