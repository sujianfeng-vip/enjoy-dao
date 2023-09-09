package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.enums.DbSymbol;
import vip.sujianfeng.enjoydao.condition.enums.SqlLike;
import vip.sujianfeng.enjoydao.condition.enums.SqlOrderBy;
import vip.sujianfeng.enjoydao.condition.utils.CustomUtil;
import vip.sujianfeng.enjoydao.condition.utils.DbUtil;
import vip.sujianfeng.enjoydao.condition.utils.JudgeUtil;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * author Xiao-Bai
 * createTime 2021/12/13 9:23
 **/
@SuppressWarnings("all")
public abstract class ConditionAssembly<T, R, Children> extends ConditionWrapper<T>
        implements ConditionSplicer<Children>, QueryFunction<Children, T, R> {

    protected abstract Children adapter(DbSymbol dbSymbol, boolean condition, R column);

    protected abstract Children adapter(DbSymbol dbSymbol, boolean condition, String sqlColumn);

    protected abstract Children adapter(DbSymbol dbSymbol, boolean condition, R column, Object val);

    protected abstract Children adapter(DbSymbol dbSymbol, boolean condition, R column, Object val1, Object val2);

    protected abstract Children adapter(DbSymbol dbSymbol, boolean condition, R column, String expression);

    protected abstract Children getInstance();
    public Children addCutsomizeSql(String customizeSql, Object... params) {
        if (JudgeUtil.isEmpty(customizeSql)) {
            return childrenClass;
        }
        this.addCustomizeSql(customizeSql);
        this.addParams(Arrays.stream(params).collect(Collectors.toList()));
        return childrenClass;
    }

    public Children addCutsomizeSql(boolean condition, String customizeSql, Object... params) {
        if (condition) {
            return addCutsomizeSql(customizeSql, params);
        }
        return childrenClass;
    }

    protected void appendCondition(DbSymbol dbSymbol, boolean condition, String column, Object val1, Object val2, String expression) {

        if(!condition || !appendState) {
            return;
        }
        column = this.checkedColumn(dbSymbol, column);
        this.handleFinalCondition(dbSymbol, column, val1, val2, expression);

        if(CustomUtil.isNotBlank(getLastCondition())) {
            addCondition(getLastCondition());
            setLastCondition(Constants.EMPTY);
        }
        if(appendSybmol.equals(Constants.OR)) {
            appendSybmol = Constants.AND;
        }
    }

    private String checkedColumn(DbSymbol dbSymbol, String column) {
        if(CustomUtil.isBlank(column) && !ALLOW_NOT_ALIAS.contains(dbSymbol)) {
            throw new CustomCheckException("column cannot be empty");
        }
        if(!column.contains(Constants.POINT)) {
            column = DbUtil.fullSqlColumn(getTableSupport().alias(), column);
        }
        return column;
    }

    private void handleFinalCondition(DbSymbol dbSymbol, String column, Object val1, Object val2, String expression) {
        switch (dbSymbol) {
            case EQUALS:
            case NOT_EQUALS:
            case LESS_THAN:
            case GREATER_THAN:
            case LESS_THAN_EQUALS:
            case GREATER_THAN_EQUALS:
                setLastCondition(DbUtil.applyCondition(appendSybmol, column, dbSymbol.getSymbol()));
                CustomUtil.addParams(getParamValues(), val1);
                break;

            case LIKE:
            case NOT_LIKE:
                setLastCondition(DbUtil.applyCondition(appendSybmol,
                        column, dbSymbol.getSymbol(), SqlLike.sqlLikeConcat((SqlLike) val2)));
                addParams(val1);
                break;

            case IN:
            case NOT_IN:
                ConditionOnInsqlAssembly(dbSymbol, column, val1);
                break;

            case EXISTS:
            case NOT_EXISTS:
                setLastCondition(DbUtil.applyExistsCondition(appendSybmol, dbSymbol.getSymbol(), expression));
                break;

            case BETWEEN:
            case NOT_BETWEEN:
                ConditionOnSqlBetweenAssembly(dbSymbol, column, val1, val2);
                break;

            case IS_NULL:
            case IS_NOT_NULL:
                setLastCondition(DbUtil.applyIsNullCondition(appendSybmol, column, dbSymbol.getSymbol()));
                break;

            case ORDER_BY:
            case ORDER_BY_ASC:
            case ORDER_BY_DESC:
                getOrderBy().add(column);
                break;

            case GROUP_BY:
                getGroupBy().add(column);
                break;

            case HAVING:
                getHaving().append(column);
                getHavingParams().addAll((List<Object>) val1);
                break;
        }
    }

    private void ConditionOnSqlBetweenAssembly(DbSymbol dbSymbol, String column, Object val1, Object val2) {
        if(!CustomUtil.isBasicType(val1) || !CustomUtil.isBasicType(val2)) {
            throw new IllegalArgumentException("val1 or val2 can only be basic types");
        }
        if(JudgeUtil.isEmpty(val1) || JudgeUtil.isEmpty(val2)) {
            throw new NullPointerException("At least one null value exists between val1 and val2");
        }
        setLastCondition(String.format(" %s %s %s", appendSybmol, column, dbSymbol.getSymbol()));
        addParams(val1, val2);
    }

    private void ConditionOnInsqlAssembly(DbSymbol dbSymbol, String column, Object val) {
        StringJoiner symbol = new StringJoiner(Constants.SEPARATOR_COMMA_2);
        if (CustomUtil.isBasicType(val)) {
            addParams(val);

        } else if (val.getClass().isArray()) {
            int len = Array.getLength(val);
            for (int i = 0; i < len; i++) {
                symbol.add(Constants.QUEST);
                addParams(Array.get(val, i));
            }

        } else if (val instanceof Collection) {
            Collection<?> objects = (Collection<?>) val;
            addParams(objects);
            IntStream.range(0, objects.size()).forEach(x -> symbol.add(Constants.QUEST));
        }
        setLastCondition(DbUtil.applyInCondition(appendSybmol, column, dbSymbol.getSymbol(), symbol.toString()));
    }

    protected void appendMaxCond(DbSymbol prefix, String condition) {
        addCondition(String.format(" %s (%s)", prefix.getSymbol(), DbUtil.trimSqlCondition(condition)));
    }

    protected void appendInSql(String column, DbSymbol dbSymbol, String condition, Object... params) {
        column = this.checkedColumn(dbSymbol, column);
        addCondition(DbUtil.applyInCondition(appendSybmol, column, dbSymbol.getSymbol(), condition));
        if (params.length > 0) {
            addParams(params);
        }
    }

    protected String orderByField(String column, SqlOrderBy orderBy) {
        return DbUtil.sqlSelectWrapper(column, orderBy.getName());
    }

    protected Children spliceCondition(boolean condition, boolean spliceType, ConditionWrapper<T> wrapper) {
        if(condition && Objects.nonNull(wrapper)) {
            this.mergeConditionWrapper(spliceType, wrapper);
        }
        appendState = true;
        return childrenClass;
    }

    protected Children doSelectSqlFunc(Consumer<SelectFunc<T>> consumer) {
        SelectFunc<T> sqlFunc = new SelectFunc<>(getEntityClass());
        consumer.accept(sqlFunc);
        this.mergeSelect(sqlFunc.getColumns().split(String.valueOf(Constants.CENTER_LINE)));
        return childrenClass;
    }

    protected void mergeConditionWrapper(boolean spliceType, ConditionWrapper<T> conditionEntity) {

        if (Objects.nonNull(conditionEntity.getSelectColumns())) {
            mergeSelect(conditionEntity.getSelectColumns());
        }
        if (JudgeUtil.isNotEmpty(conditionEntity.getFinalConditional())) {
            mergeCondition(spliceType, conditionEntity);
        }

        if (JudgeUtil.isNotEmpty(conditionEntity.getGroupBy())) {
            mergeGroupBy(conditionEntity);
        }

        if (JudgeUtil.isNotEmpty(conditionEntity.getHaving())) {
            mergeHaving(conditionEntity);
        }
        if (JudgeUtil.isNotEmpty(conditionEntity.getOrderBy())) {
            mergeOrderBy(conditionEntity);
        }

    }

    private void mergeCondition(boolean spliceType, ConditionWrapper<T> conditionEntity) {
        appendMaxCond(spliceType ? DbSymbol.AND : DbSymbol.OR, conditionEntity.getFinalConditional());
        addParams(conditionEntity.getParamValues());
    }

    private void mergeOrderBy(ConditionWrapper<T> conditionEntity) {
        getOrderBy().merge(conditionEntity.getOrderBy());
    }

    private void mergeGroupBy(ConditionWrapper<T> conditionEntity) {
        getGroupBy().merge(conditionEntity.getOrderBy());
    }

    private void mergeHaving(ConditionWrapper<T> conditionEntity) {
        if (JudgeUtil.isEmpty(getHaving()) && JudgeUtil.isNotEmpty(conditionEntity.getHaving())) {
            getHaving().append(conditionEntity.getHaving());
        } else if (JudgeUtil.isNotEmpty(getHaving()) && JudgeUtil.isNotEmpty(conditionEntity.getHaving())) {
            getHaving().append(String.format(" and %s ", conditionEntity.getHaving()));
        }
    }

    protected Children mergeConsmerCondition(boolean condition, boolean spliceType, Consumer<Children> consumer) {
        if (condition) {
            Children instance = getInstance();
            consumer.accept(instance);
            return spliceCondition(true, spliceType, (ConditionWrapper<T>) instance);
        }
        return childrenClass;
    }

    @Override
    public Children having(boolean condition, String havingSql, Object... params) {
        appendCondition(DbSymbol.HAVING, condition, havingSql, params, null, null);
        return childrenClass;
    }

    @Override
    public Children pageParams(boolean condition, Integer pageIndex, Integer pageSize) {
        if((Objects.isNull(pageIndex) || Objects.isNull(pageSize))) {
            throw new CustomCheckException("Missing paging parameter:pageIndex: %s, pageSize: %s", pageIndex, pageSize);
        }
        setPageParams(pageIndex, pageSize);
        return childrenClass;
    }

    public Children onlyPrimary() {
        setPrimaryTable();
        return childrenClass;
    }


    protected final Children childrenClass = (Children) this;
    protected static String appendSybmol = Constants.AND;

    private final static List<DbSymbol> ALLOW_NOT_ALIAS = Arrays.asList(DbSymbol.EXISTS, DbSymbol.NOT_EXISTS);

    protected static boolean appendState = true;



}
