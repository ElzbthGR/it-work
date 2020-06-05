package ru.kpfu.itis.models.enums.sorting;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import java.util.function.Function;
import java.util.stream.Stream;

public interface ISortType {

    ComparableExpressionBase[] getExpressions();

    default OrderSpecifier[] of(Order order) {
        Function<ComparableExpressionBase, OrderSpecifier> orderMapper = order == Order.DESC
                ? ComparableExpressionBase::desc
                : ComparableExpressionBase::asc;
        return Stream.of(getExpressions())
                .map(orderMapper)
                .map(OrderSpecifier::nullsLast)
                .toArray(OrderSpecifier[]::new);
    }
}
