package vip.sujianfeng.enjoydao.sqlbuilder;


import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;

/**
 * author SuJianFeng
 * createTime 2019/1/30 11:45
 **/
public class TbSelectSql extends TbTableSql {

    public <T> TbSelectSql(SqlAdapter sqlAdapter, Class<T> t, int pageIndex, int pageSize, String orderBy, String... selectFields) {
        super(sqlAdapter, t);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderBy = this.getDefineTable().getOrderBy();
        if (StringUtilsEx.isNotEmpty(orderBy)){
            this.orderBy = orderBy;
        }
        this.selectFields = selectFields;
    }
    private String condition;
    private int pageIndex;
    private int pageSize;
    private String orderBy;
    private String[] selectFields;

    public String getJoinTableSql(){
        StringBuilderEx sql= new StringBuilderEx();
        for (TbDefineRelationField rlsField: getRlsFieldList()){
            String joinLine = this.getSqlAdapter().getLeftJoinSql(rlsField);
            if (!sql.toString().contains(joinLine)){
                sql.appendRow(joinLine);
            }
        }
        return sql.toString();
    }

    public String getSelectSql(){
        boolean all = selectFields == null || selectFields.length == 0;
        StringBuilderEx sql= new StringBuilderEx();
        for (TbDefineField field: getFieldList()){
            if (all || ConvertUtils.oneOfString(field.getField(), selectFields)){
                if (sql.length() > 0) sql.append(",");
                sql.append(this.getSqlAdapter().getSelectFieldSql(field));
            }
        }
        for (TbDefineRelationField rlsField: getRlsFieldList()){
            if (all || ConvertUtils.oneOfString(rlsField.getField(), selectFields)){
                sql.append(this.getSqlAdapter().getSelectFieldSql(rlsField));
            }
        }
        for (TbDefineExpressionField exprField : this.getExprFieldList()) {
            sql.append(this.getSqlAdapter().getSelectFieldSql(exprField));
        }
        return sql.toString();
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginRow() {
        return (pageIndex - 1) * pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
