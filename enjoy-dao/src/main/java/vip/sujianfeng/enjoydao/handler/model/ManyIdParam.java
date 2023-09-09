package vip.sujianfeng.enjoydao.handler.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 * description
 **/
@ApiModel(description = "多个主键参数")
public class ManyIdParam {
    @ApiModelProperty(value = "主键列表", required = true)
    private List<String> ids = new ArrayList<>();

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
