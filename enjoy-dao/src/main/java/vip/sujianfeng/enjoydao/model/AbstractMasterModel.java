package vip.sujianfeng.enjoydao.model;

import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.annotations.TbFieldInt;
import vip.sujianfeng.enjoydao.annotations.TbFieldLong;
import vip.sujianfeng.enjoydao.annotations.TbKeyField;

/**
 * @author SuJianFeng
 * @date 2022/04/30 9:40
 **/
public class AbstractMasterModel extends AbstractOpModel{

    @ApiModelProperty("主键id")
    @TbKeyField
    private String id;

    @ApiModelProperty("数据状态：0-正常，1-删除，2-注销")
    @TbFieldInt(tableField = "state", label = "数据状态：0-正常，1-删除，2-注销", isSystemField = true)
    private Integer state;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
