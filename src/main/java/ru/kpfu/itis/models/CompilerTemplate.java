package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compiler_templates")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "compiler_templates_seq", allocationSize = 1)
public class CompilerTemplate extends AbstractEntity {

    @Column(unique = true)
    private Long compilerId;
    private String baseTemplate;
}
