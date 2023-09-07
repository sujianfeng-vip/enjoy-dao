package vip.sujianfeng.enjoydao.handler.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.sqlcondition.ISqlConditionBuilder;
import vip.sujianfeng.utils.comm.StringUtilsEx;

/**
 * @Author SuJianFeng
 * @Date 2022/9/21
 * @Description
 **/
@ApiModel(description = "查询分页数据")
public abstract class PageParam {
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("每页笔数")
    private int pageSize;
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("排序字段")
    private String orderBy;

    public <T extends PageParam> T clone(Class<T> t) {
        return JSON.parseObject(JSON.toJSONString(this), t);
    }

    private ISqlConditionBuilder builder = null;

    protected abstract ISqlConditionBuilder createBuilder(TbDao tbDao);

    public ISqlConditionBuilder sqlConditionBuilder(TbDao tbDao) {
        if (builder != null) {
            return builder;
        }
        builder = createBuilder(tbDao);
        builder.setPageNo(this.pageNo);
        builder.setPageSize(this.pageSize);
        if (StringUtilsEx.isNotEmpty(this.orderBy)) {
            builder.setOrderBy(this.orderBy);
        }
        builder.getExpression().append(" and a.state = 0 ");
        return builder;
    }

    public void betweenWhere(ISqlConditionBuilder builder, String field, long beginTime, long endTime) {
        String key1 = builder.newKey();
        String key2 = builder.newKey();
        builder.putParam(key1, beginTime).put(key2, endTime);
        if (beginTime > 0 && endTime > 0){
            builder.getExpression().append(" and ").append(field).appendFormater(" between {%s} and {%s}", key1, key2);
            return;
        }
        if (beginTime > 0){
            builder.getExpression().append(" and ").append(field).appendFormater(" > {%s} ", key1);
            return;
        }
        if (endTime > 0){
            builder.getExpression().append(" and ").append(field).appendFormater(" < {%s} ", key2);
            return;
        }
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
