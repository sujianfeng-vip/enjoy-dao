package vip.sujianfeng.enjoydao.sqlcondition;


import java.util.Map;

/**
 * @Author SuJianFeng
 * @Date 2022/9/14
 * @Description
 **/
public interface ISqlConditionBuilder extends ISqlNameSeatBuilder {
    String newKey();
    Map<String, Object> putParam(String key, Object value);
    int getPageNo();
    int getPageSize();
    String getOrderBy();
    void setPageNo(int pageNo);
    void setPageSize(int pageSize);
    void setOrderBy(String orderBy);
}
