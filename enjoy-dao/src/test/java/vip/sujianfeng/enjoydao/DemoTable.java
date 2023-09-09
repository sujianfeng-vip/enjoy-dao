package vip.sujianfeng.enjoydao;

import vip.sujianfeng.enjoydao.annotations.TbFieldString;
import vip.sujianfeng.enjoydao.annotations.TbTableUuid;
import vip.sujianfeng.enjoydao.model.AbstractBizModel;

/**
 * author SuJianFeng
 * createTime 2022/6/21
 * description
 **/
@TbTableUuid(table = "test1")
public class DemoTable extends AbstractBizModel {
    @TbFieldString
    private String field1;
    @TbFieldString
    private String field2;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
