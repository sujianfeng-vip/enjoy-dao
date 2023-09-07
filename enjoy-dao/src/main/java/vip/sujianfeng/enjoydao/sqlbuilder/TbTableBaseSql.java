package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:45
 * 数据库表定义
 **/
public class TbTableBaseSql {

    private String tableName;
    private TbDefineTable defineTable;
    private TbDefineField keyField;
    private SqlAdapter sqlAdapter;

    public TbTableBaseSql(SqlAdapter sqlAdapter, String tableName, TbDefineTable defineTable, TbDefineField keyField) {
        this.sqlAdapter = sqlAdapter;
        this.tableName = tableName;
        this.defineTable = defineTable;
        this.keyField = keyField;
    }

    /**
     * 所有实字段
     */
    private List<TbDefineField> fieldList = new ArrayList<>();
    /**
     * 关联字段集合
     */
    private List<TbDefineRelationField> rlsFieldList = new ArrayList<>();
    /**
     * 表达式字段
     */
    private List<TbDefineExpressionField> exprFieldList = new ArrayList<>();

    /**
     * 参与更新（update或insert）字段（不包含：系统字段）
     */
    private List<TbDefineField> allUpdateFields = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        if (this.defineTable != null){
            this.defineTable.setTableName(tableName);
        }
    }

    public List<TbDefineField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<TbDefineField> fieldList) {
        this.fieldList = fieldList;
    }


    public List<TbDefineField> getAllUpdateFields() {
        return allUpdateFields;
    }

    public void setAllUpdateFields(List<TbDefineField> allUpdateFields) {
        this.allUpdateFields = allUpdateFields;
    }

    public List<TbDefineRelationField> getRlsFieldList() {
        return rlsFieldList;
    }

    public void setRlsFieldList(List<TbDefineRelationField> rlsFieldList) {
        this.rlsFieldList = rlsFieldList;
    }

    public TbDefineField getKeyField() {
        return keyField;
    }

    public TbDefineTable getDefineTable() {
        return defineTable;
    }

    public void setDefineTable(TbDefineTable defineTable) {
        this.defineTable = defineTable;
    }

    public void setKeyField(TbDefineField keyField) {
        this.keyField = keyField;
    }

    public SqlAdapter getSqlAdapter() {
        return sqlAdapter;
    }

    public List<TbDefineExpressionField> getExprFieldList() {
        return exprFieldList;
    }

    public void setExprFieldList(List<TbDefineExpressionField> exprFieldList) {
        this.exprFieldList = exprFieldList;
    }
}
