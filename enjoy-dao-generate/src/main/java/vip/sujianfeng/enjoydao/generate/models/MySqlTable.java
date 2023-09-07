package vip.sujianfeng.enjoydao.generate.models;

import vip.sujianfeng.utils.comm.ConvertUtils;

public class MySqlTable {
    private String tableSchema;
    private String tableName;
    private String tableComment;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableComment() {
        //替换换行符
        return ConvertUtils.cStr(tableComment).replace("\n", "-").replace("\r", "-");
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableNameForClass() {
        return ConvertUtils.cStr(tableName).replace(".", "_");
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
