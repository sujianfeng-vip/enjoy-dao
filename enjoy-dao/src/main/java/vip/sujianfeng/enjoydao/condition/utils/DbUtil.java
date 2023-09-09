package vip.sujianfeng.enjoydao.condition.utils;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

/**
 * author Xiao-Bai
 * createTime 2022/4/18 21:48
 */
public class DbUtil {


    public static String wrapperSqlColumn(String wrapperColumn, String fieldName, boolean isNullToEmpty) {
        boolean hasIfNull = RexUtil.hasRegex(wrapperColumn, RexUtil.sql_if_null);
        if (isNullToEmpty && !hasIfNull) {
            return DbUtil.ifNull(wrapperColumn, fieldName);
        }
        return sqlSelectWrapper(wrapperColumn, fieldName);
    }


    public static String ifNull(String column) {
        return String.format("IFNULL(%s, '')", column);
    }

    public static String ifNull(String column, String fieldName) {
        return String.format("IFNULL(%s, '') %s", column, fieldName);
    }


    public static String sqlSelectWrapper(String val1, String val2) {
        return String.format("%s %s", val1, val2);
    }


    public static String fullSqlColumn(String val1, String val2) {
        return String.format("%s.%s", val1, val2);
    }


    public static String formatLogicSql(String alias, String logicColumn, Object value) {
        return String.format("%s.%s = %s ", alias, logicColumn, value);
    }


    public static String formatSqlCondition(String column) {
        return String.format("%s = ?", column);
    }

    public static String formatMapperSqlCondition(String column, Object val) {
        return String.format("%s = %s", column, val);
    }


    public static String formatSqlAndCondition(String column) {
        return String.format("AND %s = ?", column);
    }
    public static String formatSqlAndCondition(String alias, String column) {
        return String.format("AND %s.%s = ?", alias, column);
    }

    public static String applyCondition(String v1, String v2, String v3) {
        return String.format(" %s %s %s ?", v1, v2, v3);
    }

    public static String applyCondition(String v1, String v2, String v3, String v4) {
        return String.format(" %s %s %s %s", v1, v2, v3, v4);
    }

    public static String applyInCondition(String v1, String v2, String v3, String v4) {
        return String.format(" %s %s %s (%s)", v1, v2, v3, v4);
    }

    public static String applyExistsCondition(String v1, String v2, String v3) {
        return String.format(" %s %s (%s)", v1, v2, v3);
    }

    public static String applyIsNullCondition(String v1, String v2, String v3) {
        return String.format(" %s %s %s", v1, v2, v3);
    }

    public static String trimSqlCondition(String condition) {
        String finalCondition = condition.trim();
        if(StrUtils.startWithIgnoreCase(finalCondition, Constants.AND)) {
            finalCondition = finalCondition.substring(3);
        }else if(StrUtils.startWithIgnoreCase(finalCondition, Constants.OR)) {
            finalCondition = finalCondition.substring(2);
        }
        return finalCondition.trim();
    }

    public static String trimFirstAndBySqlCondition(String condition) {
        String finalCondition = condition;
        if(condition.trim().startsWith(Constants.AND)) {
            finalCondition = condition.replaceFirst(Constants.AND, Constants.EMPTY);
        }
        return finalCondition.trim();
    }

    public static String replaceOrWithAndOnSqlCondition(String condition) {
        String finalCondition = condition;
        if(condition.trim().startsWith(Constants.OR)) {
            finalCondition = condition.replaceFirst(Constants.OR, Constants.AND);
        }
        return finalCondition.trim();
    }

}
