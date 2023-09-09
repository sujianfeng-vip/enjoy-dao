package vip.sujianfeng.enjoydao.condition;

import java.io.Serializable;

/**
 * author Xiao-Bai
 * createTime 2022/12/6 0006 13:59
 */
public class ColumnFieldCache implements Serializable {


    private final String field;

    private final String getter;

    private final String column;

    public ColumnFieldCache(String field, String getter, String column) {
        this.field = field;
        this.getter = getter;
        this.column = column;
    }

    public String getField() {
        return field;
    }

    public String getGetter() {
        return getter;
    }

    public String getColumn() {
        return column;
    }
}
