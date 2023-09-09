package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.annotations.TbRelationField;
import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;

import java.lang.reflect.Field;

/**
 * author SuJianFeng
 * createTime 2019/2/2 17:24
 * 关联字段定义
 **/
public class TbDefineRelationField {

    public static TbDefineRelationField instance(TbTableSql owner, Field field, TbRelationField rlsField) throws IllegalAccessException {
        TbDefineRelationField defineRlsField = new TbDefineRelationField();
        defineRlsField.setJoinTable(rlsField.joinTable());
        defineRlsField.setJoinTableAlias(rlsField.joinTableAlias());
        defineRlsField.setJoinCondition(rlsField.joinCondition());
        defineRlsField.setField(field.getName());
        defineRlsField.setSrcField(rlsField.srcField());
        defineRlsField.setSrcFieldAlias(rlsField.srcFieldAlias());
        defineRlsField.setVisible(rlsField.visible());
        owner.getRlsFieldList().add(defineRlsField);
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        if (defineRlsField.getFieldType() == TbDefineFieldType.ftBoolean){
            defineRlsField.setValue(ConvertUtils.cInt(value));
        }else{
            defineRlsField.setValue(value);
        }
        return defineRlsField;
    }

    private String joinTable;
    private String joinTableAlias;
    private String joinCondition;
    private String field;
    private String srcField;
    private String srcFieldAlias;
    private TbDefineFieldType fieldType;
    private Object value;
    private boolean visible;

    public String getTableField(){
        if (StringUtilsEx.isNotEmpty(srcFieldAlias)){
            return srcFieldAlias;
        }
        return srcField;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getJoinTableAlias() {
        return joinTableAlias;
    }

    public void setJoinTableAlias(String joinTableAlias) {
        this.joinTableAlias = joinTableAlias;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public String getSrcField() {
        return srcField;
    }

    public void setSrcField(String srcField) {
        this.srcField = srcField;
    }

    public String getSrcFieldAlias() {
        return srcFieldAlias;
    }

    public void setSrcFieldAlias(String srcFieldAlias) {
        this.srcFieldAlias = srcFieldAlias;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TbDefineFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(TbDefineFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
