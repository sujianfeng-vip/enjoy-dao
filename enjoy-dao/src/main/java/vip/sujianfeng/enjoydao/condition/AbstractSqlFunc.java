package vip.sujianfeng.enjoydao.condition;


import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.enums.SqlAggregate;
import vip.sujianfeng.enjoydao.condition.support.ColumnParseHandler;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author Xiao-Bai
 * createTime 2022/3/19 17:28
 **/
@SuppressWarnings("all")
public abstract class AbstractSqlFunc<T, Children> {


    public abstract Children sum(SFunction<T, ?> column);
    public abstract Children sum(boolean isNullToZero, SFunction<T, ?> column);


    public abstract Children avg(SFunction<T, ?> column);
    public abstract Children avg(boolean isNullToZero, SFunction<T, ?> column);

    public Children count(SFunction<T, ?> column) {
        return count(column, false);
    }
    public abstract Children count(SFunction<T, ?> column, boolean distinct);

    public abstract Children ifNull(SFunction<T, ?> column, Object elseVal);

    public abstract Children max(SFunction<T, ?> column);
    public abstract Children max(boolean isNullToZero, SFunction<T, ?> column);

    public abstract Children min(SFunction<T, ?> column);
    public abstract Children min(boolean isNullToZero, SFunction<T, ?> column);

    private ColumnParseHandler<T> columnParseHandler;

    private Map<String, String> fieldMapper;

    private Map<String, String> columnMapper;

    private List<String> sqlFragments;

    private String alias;


    protected void initNeed(Class<T> cls) {
        TableSupport tableSupport = new TableSimpleSupport<>(cls);
        columnParseHandler = new DefaultColumnParseHandler<>(cls, tableSupport);
        fieldMapper = tableSupport.fieldMap();
        columnMapper = tableSupport.columnMap();
        alias = tableSupport.alias();
        sqlFragments = new ArrayList<>();
    }

    protected String formatRex(SqlAggregate aggregate, Boolean distinct) {
        return formatRex(aggregate, false, distinct);
    }

    protected String formatRex(SqlAggregate aggregate) {
        return formatRex(aggregate, false, false);
    }

    protected String formatRex(SqlAggregate aggregate, boolean isNullToZero,  Boolean distinct) {
        String template = Constants.EMPTY;
        switch (aggregate) {
            case SUM:
            case MAX:
            case MIN:
            case AVG:
                template = isNullToZero ? "IFNULL(%s(%s), 0) %s" : "%s(%s) %s";
                break;
            case COUNT:
                template = distinct ? "%s(DISTINCT %s) %s" : "%s(%s) %s";
                break;
            case IFNULL:
                template = "%s(%s, %s) %s";
                break;
        }
        return template;
    }


    protected Children doFunc(String format, Object... params) {
        sqlFragments.add(String.format(format, params));
        return childrenClass;
    }

    public List<String> getSqlFragments() {
        return sqlFragments;
    }

    protected String getColumns() {
        return sqlFragments.stream().collect(Collectors.joining(String.valueOf(Constants.CENTER_LINE)));
    }

    protected ColumnParseHandler<T> columnParseHandler() {
        return columnParseHandler;
    }

    protected Map<String, String> getFieldMapper() {
        return fieldMapper;
    }

    public String getAlias() {
        return alias;
    }

    public Map<String, String> getColumnMapper() {
        return columnMapper;
    }

    private final Children childrenClass = (Children) this;
}
