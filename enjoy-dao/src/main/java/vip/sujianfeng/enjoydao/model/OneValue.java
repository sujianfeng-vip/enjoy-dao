package vip.sujianfeng.enjoydao.model;

/**
 * author SuJianFeng
 * createTime 2022/10/25
 * description
 **/
public class OneValue {
    private String id;
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
