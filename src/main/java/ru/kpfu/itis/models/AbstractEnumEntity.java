package ru.kpfu.itis.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractEnumEntity extends AbstractEntity {

    public static final String NAME_COLUMN = "name";

    @Column(name = NAME_COLUMN)
    private String name;
}
