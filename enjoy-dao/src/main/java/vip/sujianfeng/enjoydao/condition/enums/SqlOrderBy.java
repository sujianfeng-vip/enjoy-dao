package vip.sujianfeng.enjoydao.condition.enums;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

/**
 * author Xiao-Bai
 * createTime 2022/3/5 22:53
 */
public enum SqlOrderBy {

    ASC(Constants.ASC),

    DESC(Constants.DESC);


    private final String name;

    SqlOrderBy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
