package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.enjoydao.annotations.*;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:45
 * 数据库表定义
 **/
public class TbTableSql extends TbTableBaseSql {
    private static Logger logger = LoggerFactory.getLogger(TbTableSql.class);
    private Class<?> t = null;

    private AbstractOpModel model;
    private String tableAlias;

    public static  TbTableSql getTableSql(SqlAdapter sqlAdapter, Class<?> t){
        return new TbTableSql(sqlAdapter, t);
    }

    public static  TbTableSql getTableSql(SqlAdapter sqlAdapter, AbstractOpModel model){
        return new TbTableSql(sqlAdapter, model);
    }

    public static String getTableName(Class<?> t){
        TbDefineTable tbDefineTable = TbDefineTable.instance(t);
        return tbDefineTable != null ? tbDefineTable.getTableName() : null;
    }
    public TbTableSql(SqlAdapter sqlAdapter, String tableName, TbDefineTable defineTable, TbDefineField keyField) {
        super(sqlAdapter, tableName, defineTable, keyField);
    }

    public TbTableSql(SqlAdapter sqlAdapter, AbstractOpModel model) {
        this(sqlAdapter, model.getClass(), model);
    }


    public TbTableSql(SqlAdapter sqlAdapter, Class<?> t) {
        this(sqlAdapter, t, null);
    }

    public TbTableSql(SqlAdapter sqlAdapter, Class<?> t, AbstractOpModel model) {
        super( sqlAdapter, null, null, null);
        this.t = t;
        this.model = model;
        this.setDefineTable(TbDefineTable.instance(t));
        if (getDefineTable() == null) throw new RuntimeException(String.format("%s: 缺少@TbTable注解", t.getName()));
        this.setTableName(this.getDefineTable().getTableName());
        this.setTableAlias(this.getDefineTable().getTableAlias());
        initField(t);
    }

    private void initField(Class<?> tmpClass){
        if (tmpClass == null) return;
        Field[] fields = tmpClass.getDeclaredFields();
        for (Field field : fields){
            try {
                //主键字段
                TbKeyField tbKeyField = field.getAnnotation(TbKeyField.class);
                if (tbKeyField != null){
                    this.setKeyField(TbDefineField.instance(this, field, tbKeyField));
                }
                //uuid字段
                TbFieldUuid tbFieldUuid = field.getAnnotation(TbFieldUuid.class);
                if (tbFieldUuid != null){
                    TbDefineField.instance(this, field, tbFieldUuid);
                }
                //LongIntId字段
                TbFieldLongIntId tbFieldLongIntId = field.getAnnotation(TbFieldLongIntId.class);
                if (tbFieldLongIntId != null){
                    TbDefineField.instance(this, field, tbFieldLongIntId);
                }
                //long字段
                TbFieldLong tbFieldLong= field.getAnnotation(TbFieldLong.class);
                if (tbFieldLong != null){
                    TbDefineField.instance(this, field, tbFieldLong);
                }
                //int字段
                TbFieldInt tbFieldInt = field.getAnnotation(TbFieldInt.class);
                if (tbFieldInt != null){
                    TbDefineField.instance(this, field, tbFieldInt);
                }
                //String字段
                TbFieldString tbFieldString = field.getAnnotation(TbFieldString.class);
                if (tbFieldString != null){
                    TbDefineField.instance(this, field, tbFieldString);
                }
                //Timestamp字段
                TbFieldTimestamp tbFieldTimestamp = field.getAnnotation(TbFieldTimestamp.class);
                if (tbFieldTimestamp != null){
                    TbDefineField.instance(this, field, tbFieldTimestamp);
                }
                //Boolean字段
                TbFieldBoolean tbFieldBoolean = field.getAnnotation(TbFieldBoolean.class);
                if (tbFieldBoolean != null){
                    TbDefineField.instance(this, field, tbFieldBoolean);
                }
                //普通字段
                TbField tbField = field.getAnnotation(TbField.class);
                if (tbField != null){
                    TbDefineField.instance(this, field, tbField);
                }
                //关联字段
                TbRelationField rlsField = field.getAnnotation(TbRelationField.class);
                if (rlsField != null){
                    TbDefineRelationField.instance(this, field, rlsField);
                }
                //表达式字段
                TbExpressionField expressionField = field.getAnnotation(TbExpressionField.class);
                if (expressionField != null) {
                    TbDefineExpressionField.instance(this, field, expressionField);
                }
            } catch (IllegalAccessException e) {
                logger.error(e.toString(), e);
            }
        }
        initField(tmpClass.getSuperclass());
    }

     public String getId() {
        return model != null ? model.getId() : null;
    }

    public void setId(String id){
        if (model != null){
            model.setId(id);
        }
    }

    public AbstractOpModel getModel() {
        return model;
    }

    public void setModel(AbstractOpModel model) {
        this.model = model;
    }

    public Class<?> getT() {
        return t != null ? t : model != null ? model.getClass() : null;
    }

    public void setT(Class<?> t) {
        this.t = t;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }
}
