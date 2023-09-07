package vip.sujianfeng.enjoydao.generate.models;

import vip.sujianfeng.utils.comm.ConvertUtils;

public class MySqlTableColumn {
    private String tableSchema;
    private String tableName;
    private String columnName;
    private String columnType;
    private String columnComment;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        //替换换行符
        return ConvertUtils.cStr(columnComment).replace("\n", "-").replace("\r", "-");
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
