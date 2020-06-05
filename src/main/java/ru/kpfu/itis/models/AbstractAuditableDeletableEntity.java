package ru.kpfu.itis.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class AbstractAuditableDeletableEntity extends AbstractAuditableEntity {
    private Boolean deleted = Boolean.FALSE;
}
