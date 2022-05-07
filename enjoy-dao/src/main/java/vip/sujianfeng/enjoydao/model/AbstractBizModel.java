package vip.sujianfeng.enjoydao.model;

import vip.sujianfeng.enjoydao.annotations.TbFieldInt;
import vip.sujianfeng.enjoydao.annotations.TbFieldLong;
import vip.sujianfeng.enjoydao.annotations.TbKeyField;

/**
 * @author SuJianFeng
 * @date 2022/04/30 9:40
 **/
public class AbstractBizModel extends AbstractOpModel{
    @TbKeyField
    private String id;

    @TbFieldInt(tableField = "biz_status", label = "业务类型：0-启用，1-禁用")
    private Integer bizStatus;

    @TbFieldLong(tableField = "create_time", label = "创建时间", isSystemField = true)
    private Integer createTime;

    @TbFieldLong(tableField = "update_time", label = "最后修改时间", isSystemField = true)
    private Integer updateTime;

    @TbFieldInt(tableField = "state", label = "数据状态：0-正常，1-删除", isSystemField = true)
    private Integer state;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Integer bizStatus) {
        this.bizStatus = bizStatus;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
