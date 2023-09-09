package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.enjoydao.annotations.TbExpressionField;

import java.lang.reflect.Field;

/**
 * author SuJianFeng
 * createTime 2022/5/9
 **/
public class TbDefineExpressionField {

    public static TbDefineExpressionField instance(TbTableSql owner, Field field, TbExpressionField expressionField) {
        field.setAccessible(true);
        TbDefineExpressionField result = new TbDefineExpressionField();
        result.expression = expressionField.value();
        result.fieldName = field.getName();
        owner.getExprFieldList().add(result);
        return result;
    }

    private String expression;
    private String fieldName;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
