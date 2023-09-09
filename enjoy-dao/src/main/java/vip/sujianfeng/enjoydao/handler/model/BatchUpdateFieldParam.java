package vip.sujianfeng.enjoydao.handler.model;

import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/28
 **/
public class BatchUpdateFieldParam {
    private List<String> ids;
    private String fieldName;
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
