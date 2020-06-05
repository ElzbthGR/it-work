package ru.kpfu.itis.mappers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class DtoShortMapper<E, D> extends DtoMapper<E, D> {

    @Nullable
    public abstract <SD extends D> SD apply(@Nullable E entity, @Nonnull Supplier<SD> dtoSupplier);
}
