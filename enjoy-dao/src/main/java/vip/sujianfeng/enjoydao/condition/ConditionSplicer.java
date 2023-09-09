package vip.sujianfeng.enjoydao.condition;

import java.util.function.Consumer;

/**
 * author Xiao-Bai
 * createTime 2022/3/13 23:43
 */
public interface ConditionSplicer<Children> {

    Children or(boolean condition, Children wrapper);
    default Children or(Children wrapper) {
       return or(true, wrapper);
    }

    Children or(boolean condition, Consumer<Children> consumer);
    default Children or(Consumer<Children> consumer) {
        return or(true, consumer);
    }


    Children or(boolean condition);
    default Children or() {
        return or(true);
    }


    Children and(boolean condition, Children wrapper);
    default Children and(Children wrapper) {
        return and(true, wrapper);
    }

    Children and(boolean condition, Consumer<Children> consumer);
    default Children and(Consumer<Children> consumer) {
        return and(true, consumer);
    }


}
