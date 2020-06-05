package ru.kpfu.itis.repositories.custom.helpers;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import ru.kpfu.itis.dto.forms.PageableForm;
import ru.kpfu.itis.models.enums.sorting.ISortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.annotation.Nullable;

/**
 * @author Dmitry Sadchikov
 */
public abstract class RepositoryHelper {

    public static <E> Page<E> pageBy(JPAQuery<E> jpaQuery, PageableForm form) {
        return pageBy(jpaQuery.fetchResults(), form);
    }

    public static <E> Page<E> pageBy(QueryResults<E> queryResults, PageableForm form) {
        return new PageImpl<>(queryResults.getResults(), form.getPageRequest(), queryResults.getTotal());
    }

    public static OrderSpecifier[] getOrders(@Nullable ISortType sortType, @Nullable Order order) {
        if (sortType == null) {
            return new OrderSpecifier[0];
        }
        if (order == null) {
            order = Order.ASC;
        }
        return sortType.of(order);
    }
}
