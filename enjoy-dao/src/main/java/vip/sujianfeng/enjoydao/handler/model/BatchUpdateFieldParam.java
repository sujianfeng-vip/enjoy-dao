package vip.sujianfeng.enjoydao.handler.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/28
 * @Description
 **/
@ApiModel(description = "批量更新指定栏位参数")
public class BatchUpdateFieldParam {
    @ApiModelProperty("主键列表")
    private List<String> ids;
    @ApiModelProperty("字段名（例如：biz_status）")
    private String fieldName;
    @ApiModelProperty("字段值")
    private Object fieldValue;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
