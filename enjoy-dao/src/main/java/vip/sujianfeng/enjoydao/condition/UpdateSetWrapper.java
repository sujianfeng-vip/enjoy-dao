package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.support.ColumnParseHandler;
import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.condition.utils.CustomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * author Xiao-Bai
 * createTime 2022/8/6 17:45
 */
public abstract class UpdateSetWrapper<T> {


    private final StringJoiner sqlSetter;

    private final List<Object> setParams;

    private final Class<T> entityClass;

    private final ColumnParseHandler<T> columnParseHandler;

    public List<Object> getSetParams() {
        return setParams;
    }

    public UpdateSetWrapper(Class<T> entityClass) {
        this.sqlSetter = new StringJoiner(Constants.SEPARATOR_COMMA_2);
        this.setParams = new ArrayList<>();
        this.entityClass = entityClass;
        TableSimpleSupport<T> simpleSupport = new TableSimpleSupport<>(entityClass);
        this.columnParseHandler = new DefaultColumnParseHandler<>(entityClass, simpleSupport);
    }

    public Class<T> thisEntityClass() {
        return entityClass;
    }

    protected ColumnParseHandler<T> getColumnParseHandler() {
        return columnParseHandler;
    }

    protected void addSqlSetter(StringJoiner sqlSetter) {
        this.sqlSetter.merge(sqlSetter);
    }

    protected void addSqlSetter(String sqlSetter) {
        this.sqlSetter.add(sqlSetter);
    }

    protected void addParams(Object val) {
        Asserts.notNull(val, "params cannot be empty");
        CustomUtil.addParams(this.setParams, val);
    }

    public StringJoiner getSqlSetter() {
        return sqlSetter;
    }
}
