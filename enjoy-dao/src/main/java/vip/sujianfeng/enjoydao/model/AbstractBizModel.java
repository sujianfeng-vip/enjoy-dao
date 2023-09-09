package vip.sujianfeng.enjoydao.model;

import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.annotations.TbFieldInt;
import vip.sujianfeng.enjoydao.annotations.TbFieldLong;
import vip.sujianfeng.enjoydao.annotations.TbKeyField;

/**
 * author SuJianFeng
 * createTime 2022/04/30 9:40
 **/
public class AbstractBizModel extends AbstractMasterModel{

    @ApiModelProperty("Creation time (read-only)")
    @TbFieldLong(tableField = "create_time", label = "Creation time", isSystemField = true)
    private Integer createTime;

    @ApiModelProperty("Last modification time (read-only)")
    @TbFieldLong(tableField = "update_time", label = "last-modified", isSystemField = true)
    private Integer updateTime;

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

}
