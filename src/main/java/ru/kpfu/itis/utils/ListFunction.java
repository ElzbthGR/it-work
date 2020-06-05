package ru.kpfu.itis.utils;

import java.util.List;
import java.util.function.Function;

public interface ListFunction<T, R> extends Function<List<T>, List<R>> {
}
