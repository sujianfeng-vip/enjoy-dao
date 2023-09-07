package vip.sujianfeng.enjoydao.model;

import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.annotations.TbFieldInt;
import vip.sujianfeng.enjoydao.annotations.TbFieldLong;
import vip.sujianfeng.enjoydao.annotations.TbKeyField;

/**
 * @author SuJianFeng
 * @date 2022/04/30 9:40
 **/
public class AbstractBizModel extends AbstractMasterModel{

    @ApiModelProperty("创建时间（只读）")
    @TbFieldLong(tableField = "create_time", label = "创建时间", isSystemField = true)
    private Integer createTime;

    @ApiModelProperty("最后修改时间（只读）")
    @TbFieldLong(tableField = "update_time", label = "最后修改时间", isSystemField = true)
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
