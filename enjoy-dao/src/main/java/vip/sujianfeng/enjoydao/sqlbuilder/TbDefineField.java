package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;
import vip.sujianfeng.enjoydao.annotations.*;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;

import java.lang.reflect.Field;

import static vip.sujianfeng.utils.comm.StringUtilsEx.isNotEmpty;


/**
 * author SuJianFeng
 * createTime 2019/1/30 11:46
 * 实体字段定义
 **/
public class TbDefineField {

    public static TbDefineField instance(TbTableSql owner, Field field, TbField tbField) throws IllegalAccessException {
        TbDefineField tbDefineField = new TbDefineField();
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(tbField.fieldType());
        defineField.setLength(tbField.length());
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setAllowDuplicates(tbField.allowDuplicates());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        if (defineField.getFieldType() == TbDefineFieldType.ftBoolean){
            defineField.setValue(ConvertUtils.cInt(value));
        }else{
            defineField.setValue(value);
        }
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return tbDefineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldUuid tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftString);
        defineField.setLength(50);
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldBoolean tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftBoolean);
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldTimestamp tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftTimestamp);
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldString tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftString);
        defineField.setLength(tbField.length());
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldLongIntId tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftLongInt);
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldLong tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setFieldType(TbDefineFieldType.ftLongInt);
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbFieldInt tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setTableFieldAlias(tbField.tableFieldAlias());
        defineField.setFieldType(TbDefineFieldType.ftInt);
        defineField.setAllowEmpty(tbField.allowEmpty());
        defineField.setLabel(tbField.label());
        defineField.setVisible(tbField.visible());
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        if (!tbField.isSystemField()){
            owner.getAllUpdateFields().add(defineField);
        }
        return defineField;
    }

    public static TbDefineField instance(TbTableSql owner, Field field, TbKeyField tbField) throws IllegalAccessException {
        TbDefineField defineField = new TbDefineField();
        defineField.setKeyField(true);
        defineField.setField(field.getName());
        defineField.setTableField(isNotEmpty(tbField.tableField()) ? tbField.tableField() : field.getName());
        defineField.setFieldType(TbDefineFieldType.ftString);
        defineField.setLength(50);
        defineField.setAllowEmpty(false);
        defineField.setLabel(tbField.label());
        defineField.setVisible(false);
        field.setAccessible(true);
        Object value = owner.getModel() != null ? field.get(owner.getModel()) : null;
        defineField.setValue(value);
        owner.getFieldList().add(defineField);
        return defineField;
    }

    /**
     * 表字段名
     */
    private String tableField;
    /**
     * 字段别名
     */
    private String tableFieldAlias;
    /**
     * 类属性名
     */
    private String field;
    private TbDefineFieldType fieldType;
    private int length;
    private String label;
    /**
     * 是否允许为空
     */
    private boolean allowEmpty = true;
    /**
     * 是否允许重复
     */
    private boolean allowDuplicates = true;
    private Object value;
    private boolean keyField = false;
    private boolean visible;

    public String getDefineFieldTypeSql(SqlAdapter sqlAdapter){
        return sqlAdapter.getDefineFieldTypeSql(this);
    }

    public String getSqlValue(SqlAdapter sqlAdapter){
        return sqlAdapter.getSqlValue(this);
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public TbDefineFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(TbDefineFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLabel() {
        if (StringUtilsEx.isEmpty(label)){
            return field;
        }
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public String getTableFieldAlias() {
        return tableFieldAlias;
    }

    public void setTableFieldAlias(String tableFieldAlias) {
        this.tableFieldAlias = tableFieldAlias;
    }


    public boolean isKeyField() {
        return keyField;
    }

    public void setKeyField(boolean keyField) {
        this.keyField = keyField;
    }

    public boolean isAllowDuplicates() {
        return allowDuplicates;
    }

    public void setAllowDuplicates(boolean allowDuplicates) {
        this.allowDuplicates = allowDuplicates;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
