package vip.sujianfeng.enjoydao.sqlcondition;

import vip.sujianfeng.utils.comm.GuidUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.HashMap;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2022/9/2
 **/
public class SqlConditionBuilder<T extends SqlConditionBuilder<?>> implements ISqlConditionBuilder {
    private T me;
    private StringBuilderEx expression = new StringBuilderEx();
    private Map<String, Object> paramMap = new HashMap<>();
    private int pageNo = 1;
    private int pageSize = 10;
    private String orderBy;

    public String newKey() {
        return GuidUtils.buildGuid().substring(0, 8);
    }

    public <C extends SqlConditionBuilder<T>> C clone(Class<C> t) {
        C result = null;
        try {
            result = t.newInstance();
            result.setExpression(new StringBuilderEx(this.expression.toString()));
            result.setParamMap(new HashMap<>(this.paramMap));
            result.setPageNo(this.pageNo);
            result.setPageSize(this.pageSize);
            result.setOrderBy(this.orderBy);
            result.setMe((T) result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public T block(String expr) {
        return block(true, expr);
    }

    public T block(boolean condition, String expr) {
        if (condition) {
            this.expression.append(expr);
        }
        return me;
    }

    public Map<String, Object> putParam(String key, Object value) {
        this.paramMap.put(key, value);
        return this.paramMap;
    }

    public T and(ISqlConditionBuild<T> build) {
        return and(true, build);
    }

    public T and(boolean condition, ISqlConditionBuild<T> build) {
        if (condition) {
            this.expression.append(" and");
            build.build(me);
        }
        return me;
    }

    public T and() {
        this.expression.append(" and ");
        return me;
    }

    public T or(ISqlConditionBuild<T> build) {
        return or(true, build);
    }

    public T or(boolean condition, ISqlConditionBuild<T> build) {
        if (condition) {
            this.expression.append(" or");
            build.build(me);
        }
        return me;
    }

    public T or() {
        this.expression.append(" or ");
        return me;
    }

    public T newPar(ISqlConditionBuild<T> build) {
        return newPar(true, build);
    }

    public T newPar(boolean condition, ISqlConditionBuild<T> build) {
        if (condition) {
            this.expression.append(" (");
            build.build(me);
            this.expression.append(" )");
        }
        return me;
    }

    public T page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        return me;
    }

    public final T orderBy(SqlConditionField<?>... fields) {
        this.orderBy = "";
        for (SqlConditionField<?> field : fields) {
            if (StringUtilsEx.isNotEmpty(this.orderBy)) {
                this.orderBy += ", ";
            }
            switch (field.getSortType()) {
                case Asc:
                    this.orderBy += String.format(" %s asc", field.getField());
                    break;
                case Desc:
                    this.orderBy += String.format(" %s desc", field.getField());
                    break;
            }
        }
        return me;
    }

    public SqlConditionBuilder() {
        this.me = (T) this;
    }

    public StringBuilderEx getExpression() {
        return expression;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setMe(T me) {
        this.me = me;
    }

    public void setExpression(StringBuilderEx expression) {
        this.expression = expression;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
