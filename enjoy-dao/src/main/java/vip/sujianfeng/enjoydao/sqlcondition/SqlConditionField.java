package vip.sujianfeng.enjoydao.sqlcondition;

import vip.sujianfeng.utils.comm.HumpNameUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.Arrays;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class SqlConditionField<T extends SqlConditionBuilder<?>> {
    private T builder;
    private String field;
    private String fieldName;
    private SqlFieldSortType sortType = SqlFieldSortType.Asc;


    public T lessThan(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" < {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T lessThanOrEqual(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" <= {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T moreThan(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" > {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T moreThanOrEqual(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" >= {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T like(String value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" like {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T notLike(String expression) {
        this.builder.getExpression().append(" ").append(field).append(" not (like ").append(expression).append(")");
        return this.builder;
    }

    public T between(Object from, Object to) {
        String key1 = this.builder.newKey();
        String key2 = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" between {%s} and {%s}", key1, key2);
        this.builder.putParam(key1, from);
        this.builder.putParam(key2, to);
        return this.builder;
    }

    public T notBetween(Object from, Object to) {
        this.builder.getExpression().append(" not (");
        this.between(from, to);
        this.builder.getExpression().append(")");
        return this.builder;
    }

    public T equalsValue(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" = {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T notEqualsValue(Object value) {
        String key = this.builder.newKey();
        this.builder.getExpression().append(" ").append(field).appendFormater(" != {%s}", key);
        this.builder.putParam(key, value);
        return this.builder;
    }

    public T eq(Object value) {
        return equalsValue(value);
    }

    public T notEq(Object value) {
        return notEqualsValue(value);
    }

    public T isNull() {
        this.builder.getExpression().append(" ").append(field).append(" is null");
        return this.builder;
    }
    public T isNotNull() {
        this.builder.getExpression().append(" not (").append(field).append(" is null)");
        return this.builder;
    }

    public T in(Object... values) {
        return in(Arrays.asList(values));
    }

    public T notIn(Object... values) {
        return notIn(Arrays.asList(values));
    }

    public T in(List<Object> values) {
        this.inItems(values);
        return this.builder;
    }

    public T notIn(List<Object> values) {
        this.builder.getExpression().append(" ").append(field).append(" not in ( ");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                this.builder.getExpression().append(",");
            }
            String key = this.builder.newKey();
            this.builder.getExpression().appendFormater("{%s}", key);
            this.builder.putParam(key, values.get(i));

        }
        this.builder.getExpression().append(")");
        return this.builder;
    }

    private T inItems(List<Object> values) {
        this.builder.getExpression().append(" ").append(field).append(" in ( ");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                this.builder.getExpression().append(",");
            }
            String key = this.builder.newKey();
            this.builder.getExpression().appendFormater("{%s}", key);
            this.builder.putParam(key, values.get(i));

        }
        this.builder.getExpression().append(")");
        return this.builder;
    }

    public SqlConditionField<T> asc() {
        this.sortType = SqlFieldSortType.Asc;
        return this;
    }

    public SqlConditionField<T> desc() {
        this.sortType = SqlFieldSortType.Desc;
        return this;
    }

    public SqlConditionField(T builder, String field) {
        this.builder = builder;
        this.field = field;
        this.fieldName = HumpNameUtils.underLineToHump(StringUtilsEx.rightStr(field, "."));
    }

    public T getBuilder() {
        return builder;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public SqlFieldSortType getSortType() {
        return sortType;
    }
}
