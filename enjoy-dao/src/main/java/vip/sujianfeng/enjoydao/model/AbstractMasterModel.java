package vip.sujianfeng.enjoydao.model;

import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.annotations.TbFieldInt;
import vip.sujianfeng.enjoydao.annotations.TbFieldLong;
import vip.sujianfeng.enjoydao.annotations.TbKeyField;

/**
 * author SuJianFeng
 * createTime 2022/04/30 9:40
 **/
public class AbstractMasterModel extends AbstractOpModel{

    @ApiModelProperty("keyId")
    @TbKeyField
    private String id;

    @ApiModelProperty("Data status: 0-normal, 1-deleted, 2-logged out")
    @TbFieldInt(tableField = "state", label = "Data status: 0-normal, 1-deleted, 2-logged out", isSystemField = true)
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
