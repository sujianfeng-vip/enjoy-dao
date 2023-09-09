package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.support.TableSupport;

import java.util.function.Consumer;

/**
 * author Xiao-Bai
 * createTime 2022/5/19 14:31
 **/
public class EmptyConditionWrapper<T> extends ConditionAdapter<T, EmptyConditionWrapper<T>>{
    @Override
    public T getThisEntity() {
        throw new UnsupportedOperationException();
    }

    public EmptyConditionWrapper(Class<T> entityClass) {
        wrapperInitialize(entityClass);
    }

    public EmptyConditionWrapper(Class<T> entityClass, TableSupport tableSupport) {
        this.wrapperInitialize(entityClass, tableSupport);
    }

    @Override
    protected EmptyConditionWrapper<T> getInstance() {
        return new EmptyConditionWrapper<>(getEntityClass());
    }

    @Override
    public EmptyConditionWrapper<T> or(boolean condition, EmptyConditionWrapper<T> wrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyConditionWrapper<T> or(boolean condition, Consumer<EmptyConditionWrapper<T>> consumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyConditionWrapper<T> or(boolean condition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyConditionWrapper<T> and(boolean condition, EmptyConditionWrapper<T> wrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyConditionWrapper<T> and(boolean condition, Consumer<EmptyConditionWrapper<T>> consumer) {
        throw new UnsupportedOperationException();
    }
}
