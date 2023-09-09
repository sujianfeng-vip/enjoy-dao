package vip.sujianfeng.enjoydao.condition;

import java.util.function.Consumer;

/**
 * author Xiao-Bai
 * createTime 2022/3/15 15:43
 **/
@SuppressWarnings("all")
public interface QueryFunction<Children, T, Param> {

    @SuppressWarnings("all")
    Children select(Param... columns);

    Children select(Consumer<SelectFunc<T>> consumer);
    Children select(SelectFunc<T> selectFunc);

    Children groupBy(Param... columns);

    Children having(boolean condition, String havingSql, Object... params);
    default Children having(String havingSql, Object... params) {
        return having(true, havingSql, params);
    }

    Children pageParams(boolean condition, Integer pageIndex, Integer pageSize);
    default Children pageParams(Integer pageIndex, Integer pageSize) {
        return pageParams(true, pageIndex, pageSize);
    }


    Children orderByAsc(boolean condition, Consumer<OrderByFunc<T>> consumer);
    default Children orderByAsc(Consumer<OrderByFunc<T>> consumer) {
        return orderByAsc(true, consumer);
    }

    Children orderByAsc(boolean condition, OrderByFunc<T> orderByFunc);
    default Children orderByAsc(OrderByFunc<T> orderByFunc) {
        return orderByAsc(true, orderByFunc);
    }


    Children orderByDesc(boolean condition, Consumer<OrderByFunc<T>> consumer);
    default Children orderByDesc(Consumer<OrderByFunc<T>> consumer) {
        return orderByDesc(true, consumer);
    }

    Children orderByDesc(boolean condition, OrderByFunc<T> orderByFunc);
    default Children orderByDesc(OrderByFunc<T> orderByFunc) {
        return orderByDesc(true, orderByFunc);
    }



    Children orderByAsc(boolean condition, Param... columns);
    default Children orderByAsc(Param... columns) {
        return orderByAsc(true, columns);
    }

    Children orderByDesc(boolean condition, Param... columns);
    default Children orderByDesc(Param... columns) {
        return orderByDesc(true, columns);
    }



    Children orderByAsc(boolean condition, Param column);
    default Children orderByAsc(Param column) {
        return orderByAsc(true, column);
    }

    Children orderByDesc(boolean condition, Param column);
    default Children orderByDesc(Param column) {
        return orderByDesc(true, column);
    }

}
