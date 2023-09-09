package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.enums.SqlAggregate;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.lang.reflect.Field;

/**
 * author Xiao-Bai
 * createTime 2022/3/15 19:55
 **/
public class SelectFunc<T> extends AbstractSqlFunc<T, SelectFunc<T>> {

    public SelectFunc(Class<T> entityClass) {
        super.initNeed(entityClass);
    }

    @Override
    public final SelectFunc<T> sum(SFunction<T, ?> column) {
        String field = columnParseHandler().parseToField(column);
        return doFunc(formatRex(SqlAggregate.SUM), SqlAggregate.SUM, getFieldMapper().get(field), field);
    }

    @Override
    public SelectFunc<T> sum(boolean isNullToZero, SFunction<T, ?> column) {
        String field = columnParseHandler().parseToField(column);
        return doFunc(formatRex(SqlAggregate.SUM, isNullToZero), SqlAggregate.SUM, getFieldMapper().get(field), field);
    }

    public final SelectFunc<T> sum(SFunction<T, ?> column, SFunction<T, ?> alias) {
        String field = columnParseHandler().parseToField(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.SUM), SqlAggregate.SUM, getFieldMapper().get(field), aliasField);
    }

    public final SelectFunc<T> sum(boolean isNullToZero, SFunction<T, ?> column, SFunction<T, ?> alias) {
        String field = columnParseHandler().parseToField(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.SUM, isNullToZero), SqlAggregate.SUM, getFieldMapper().get(field), aliasField);
    }

    @Override
    public final SelectFunc<T> avg(SFunction<T, ?> column) {
        String field = columnParseHandler().parseToField(column);
        return doFunc(formatRex(SqlAggregate.AVG), SqlAggregate.AVG, getFieldMapper().get(field), field);
    }

    @Override
    public SelectFunc<T> avg(boolean isNullToZero, SFunction<T, ?> column) {
        String field = columnParseHandler().parseToField(column);
        return doFunc(formatRex(SqlAggregate.AVG, isNullToZero), SqlAggregate.AVG, getFieldMapper().get(field), field);
    }

    public final SelectFunc<T> avg(SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.AVG), SqlAggregate.AVG, originColumn, aliasField);
    }

    public final SelectFunc<T> avg(boolean isNullToZero, SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.AVG, isNullToZero), SqlAggregate.AVG, originColumn, aliasField);
    }

    @Override
    public final SelectFunc<T> count(SFunction<T, ?> column, boolean distinct) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String field = getColumnMapper().get(originColumn);
        return  doFunc(formatRex(SqlAggregate.IFNULL, distinct), SqlAggregate.COUNT, originColumn, field);
    }

    public final SelectFunc<T> count(SFunction<T, ?> column, boolean distinct, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.COUNT, distinct), SqlAggregate.COUNT, originColumn, aliasField);
    }
    public final SelectFunc<T> count(SFunction<T, ?> column, SFunction<T, ?> alias) {
        return count(column, false, alias);
    }

    public SelectFunc<T> countOne(SFunction<T, ?> alias) {
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc("%s(1) %s", SqlAggregate.COUNT, aliasField);
    }

    public SelectFunc<T> countAll(SFunction<T, ?> alias) {
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc("%s(*) %s", SqlAggregate.COUNT, aliasField);
    }

    @Override
    public final SelectFunc<T> ifNull(SFunction<T, ?> column, Object elseVal) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String field = getColumnMapper().get(originColumn);
        if (elseVal instanceof CharSequence) {
            elseVal = new StringBuilder().append(Constants.SINGLE_QUOTES).append(elseVal).append(Constants.SINGLE_QUOTES);
        }
        return doFunc(formatRex(SqlAggregate.IFNULL), SqlAggregate.IFNULL, originColumn, elseVal, field);
    }

    public final SelectFunc<T> ifNull(SFunction<T, ?> column, Object elseVal, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        Field targetField = columnParseHandler().loadFields().stream()
                .filter(x -> x.getName().equals(getColumnMapper().get(originColumn)))
                .findFirst()
                .orElseThrow(() -> new CustomCheckException("not found field: " + getColumnMapper().get(originColumn)));
        if (targetField.getType().equals(CharSequence.class)) {
            elseVal = new StringBuilder().append(Constants.SINGLE_QUOTES).append(elseVal).append(Constants.SINGLE_QUOTES);
        }
        return doFunc(formatRex(SqlAggregate.IFNULL), SqlAggregate.IFNULL, originColumn, elseVal, aliasField);
    }

    @Override
    public final SelectFunc<T> max(SFunction<T, ?> column) {
        String originColumn = columnParseHandler().parseToColumn(column);
        return doFunc(formatRex(SqlAggregate.MAX), SqlAggregate.MAX, originColumn, getColumnMapper().get(originColumn));
    }

    @Override
    public SelectFunc<T> max(boolean isNullToZero, SFunction<T, ?> column) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String formatRex = formatRex(SqlAggregate.MAX, isNullToZero, false);
        return doFunc(formatRex, SqlAggregate.MAX, originColumn, getColumnMapper().get(originColumn));
    }

    public final SelectFunc<T> max(SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        return doFunc(formatRex(SqlAggregate.MAX), SqlAggregate.MAX, originColumn, aliasField);
    }

    public final SelectFunc<T> max(boolean isNullToZero, SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        String formatRex = formatRex(SqlAggregate.MAX, isNullToZero, false);
        return doFunc(formatRex, SqlAggregate.MAX, originColumn, aliasField);
    }

    @Override
    public final SelectFunc<T> min(SFunction<T, ?> column) {
        String originColumn = columnParseHandler().parseToColumn(column);
       return doFunc(formatRex(SqlAggregate.MIN), SqlAggregate.MIN, originColumn, getColumnMapper().get(originColumn));
    }

    @Override
    public SelectFunc<T> min(boolean isNullToZero, SFunction<T, ?> column) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String formatRex = formatRex(SqlAggregate.MIN, isNullToZero, false);
        return doFunc(formatRex, SqlAggregate.MIN, originColumn, getColumnMapper().get(originColumn));
    }

    public final SelectFunc<T> min(SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        String formatRex = formatRex(SqlAggregate.MIN);
        return doFunc(formatRex, SqlAggregate.MIN, originColumn, aliasField);
    }

    public final SelectFunc<T> min(boolean isNullToZero, SFunction<T, ?> column, SFunction<T, ?> alias) {
        String originColumn = columnParseHandler().parseToColumn(column);
        String aliasField = columnParseHandler().parseToField(alias);
        String formatRex = formatRex(SqlAggregate.MIN, isNullToZero, false);
        return doFunc(formatRex, SqlAggregate.MIN, originColumn, aliasField);
    }

}
