package vip.sujianfeng.enjoydao.condition.enums;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

/**
 * author Xiao-Bai
 * createTime 2022/2/16 18:05
 **/
public enum SqlLike {

    LIKE,

    LEFT,

    RIGHT;

    public static String sqlLikeConcat(SqlLike sqlLike) {
        String sql = Constants.EMPTY;
        switch (sqlLike) {
            case LEFT:
                sql = "CONCAT('%', ?)";
                break;
            case RIGHT:
                sql = "CONCAT(?, '%')";
                break;
            case LIKE:
                sql = "CONCAT('%', ?, '%')";
                break;
        }
        return sql;
    }

}
