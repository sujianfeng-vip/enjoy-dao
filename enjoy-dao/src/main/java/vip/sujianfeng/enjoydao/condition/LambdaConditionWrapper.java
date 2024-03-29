package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.enums.DbSymbol;
import vip.sujianfeng.enjoydao.condition.enums.SqlLike;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.util.Collection;

/**
 * author Xiao-Bai
 * createTime 2022/3/3 17:17
 **/
public class LambdaConditionWrapper<T> extends ConditionAdapter<T, LambdaConditionWrapper<T>>
        implements Wrapper<SFunction<T, ?>, LambdaConditionWrapper<T>> {


    @Override
    protected LambdaConditionWrapper<T> getInstance() {
        return new LambdaConditionWrapper<>(getEntityClass());
    }

    @Override
    public LambdaConditionWrapper<T> eq(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.EQUALS, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> ne(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.NOT_EQUALS, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> ge(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.GREATER_THAN_EQUALS, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> le(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.LESS_THAN_EQUALS, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> lt(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.LESS_THAN, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> gt(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.GREATER_THAN, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> in(boolean condition, SFunction<T, ?> column, Collection<?> val) {
        return adapter(DbSymbol.IN, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> inSql(boolean condition, SFunction<T, ?> column, String inSql, Object... params) {
        appendInSql(parseColumn(column), DbSymbol.IN, inSql, params);
        return childrenClass;
    }

    @Override
    public LambdaConditionWrapper<T> notIn(boolean condition, SFunction<T, ?> column, Collection<?> val) {
        return adapter(DbSymbol.NOT_IN, condition, column, val);
    }

    @Override
    public LambdaConditionWrapper<T> notInSql(boolean condition, SFunction<T, ?> column, String inSql, Object... params) {
        appendInSql(parseColumn(column), DbSymbol.NOT_IN, inSql, params);
        return childrenClass;
    }

    @Override
    public LambdaConditionWrapper<T> exists(boolean condition, String existsSql) {
        return adapter(DbSymbol.EXISTS, condition, null, existsSql);
    }

    @Override
    public LambdaConditionWrapper<T> notExists(boolean condition, String notExistsSql) {
        return adapter(DbSymbol.NOT_EXISTS, condition, null, notExistsSql);
    }

    @Override
    public LambdaConditionWrapper<T> like(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.LIKE, condition, column, val, SqlLike.LIKE);
    }

    @Override
    public LambdaConditionWrapper<T> notLike(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.NOT_LIKE, condition, column, val, SqlLike.LIKE);
    }

    @Override
    public LambdaConditionWrapper<T> likeLeft(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.LIKE, condition, column, val, SqlLike.LEFT);
    }

    @Override
    public LambdaConditionWrapper<T> likeRight(boolean condition, SFunction<T, ?> column, Object val) {
        return adapter(DbSymbol.LIKE, condition, column, val, SqlLike.RIGHT);
    }

    @Override
    public LambdaConditionWrapper<T> between(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        return adapter(DbSymbol.BETWEEN, condition, column, val1, val2);
    }

    @Override
    public LambdaConditionWrapper<T> notBetween(boolean condition, SFunction<T, ?> column, Object val1, Object val2) {
        return adapter(DbSymbol.NOT_BETWEEN, condition, column, val1, val2);
    }

    @Override
    public LambdaConditionWrapper<T> isNull(boolean condition, SFunction<T, ?> column) {
        adapter(DbSymbol.IS_NULL, condition, column);
        return childrenClass;
    }

    @Override
    public LambdaConditionWrapper<T> isNotNull(boolean condition, SFunction<T, ?> column) {
        return adapter(DbSymbol.IS_NOT_NULL, condition, column);
    }

    public DefaultConditionWrapper<T> toDefault() {
        return new DefaultConditionWrapper<>(this);
    }

    public LambdaConditionWrapper(Class<T> entityClass) {
        wrapperInitialize(entityClass);
    }

    public LambdaConditionWrapper(Class<T> entityClass, TableSupport tableSupport) {
        this.wrapperInitialize(entityClass, tableSupport);
    }


    LambdaConditionWrapper(ConditionWrapper<T> wrapper) {
        this.dataStructureInit();
        this.setEntityClass(wrapper.getEntityClass());
        this.setColumnParseHandler(wrapper.getColumnParseHandler());
        this.setTableSupport(wrapper.getTableSupport());
        this.setLastCondition(wrapper.getLastCondition());
        this.addCondition(wrapper.getFinalConditional());
        this.addParams(wrapper.getParamValues());
        this.setSelectColumns(wrapper.getSelectColumns());
        this.setPageParams(wrapper.getPageIndex(), wrapper.getPageSize());
        this.setPrimaryTable(wrapper.getPrimaryTable());
    }


    @Override
    public T getThisEntity() {
        throw new UnsupportedOperationException();
    }
}
