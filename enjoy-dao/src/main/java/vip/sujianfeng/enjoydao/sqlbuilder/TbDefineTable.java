package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.annotations.TbTable;
import vip.sujianfeng.enjoydao.annotations.TbTableLongIntId;
import vip.sujianfeng.enjoydao.annotations.TbTableUuid;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;

/**
 * author SuJianFeng
 * createTime 2019/9/20 8:54
 **/
public class TbDefineTable {

    public static TbDefineTable instance(Class<?> t){
        TbDefineTable result = null;
        TbTable tbTable = t.getAnnotation(TbTable.class);
        if (tbTable != null){
            result = TbDefineTable.instance(tbTable);
        }else{
            TbTableUuid tbTableUuid = t.getAnnotation(TbTableUuid.class);
            if (tbTableUuid != null){
                result = TbDefineTable.instance(tbTableUuid);
            }else{
                TbTableLongIntId tbTableLongIntId = t.getAnnotation(TbTableLongIntId.class);
                if (tbTableLongIntId != null){
                    result = TbDefineTable.instance(tbTableLongIntId);
                }
            }
        }
        if (result != null){
            if (StringUtilsEx.isEmpty(result.getTableName())){
                result.setTableName(StringUtilsEx.rightStr(t.getName(), "."));
            }
        }
        return result;
    }

    private static TbDefineTable instance(TbTable tbTable) {
        TbDefineTable result = new TbDefineTable();
        result.tableName = tbTable.table();
        result.tableAlias = tbTable.tableAlias();
        result.orderBy = tbTable.orderBy();
        return result;
    }

    private static TbDefineTable instance(TbTableUuid tbTable){
        TbDefineTable result = new TbDefineTable();
        result.tableName = tbTable.table();
        result.tableAlias = tbTable.tableAlias();
        result.orderBy = tbTable.orderBy();
        return result;
    }

    private static TbDefineTable instance(TbTableLongIntId tbTable){
        TbDefineTable result = new TbDefineTable();
        result.tableName = tbTable.table();
        result.tableAlias = tbTable.tableAlias();
        result.orderBy = tbTable.orderBy();
        return result;
    }

    private String tableName;
    private String tableAlias;
    private String orderBy;
    private SqlAdapter sqlAdapter;

    public String getKeyDefine(SqlAdapter sqlAdapter){
        return sqlAdapter.getKeyDefine();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
