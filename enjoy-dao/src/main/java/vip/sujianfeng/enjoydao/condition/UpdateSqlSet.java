package vip.sujianfeng.enjoydao.condition;

/**
 * author Xiao-Bai
 * createTime 2022/8/2 22:36
 */
public interface UpdateSqlSet<Param, Children> {


    Children set(boolean condition, Param column, Object val);
    default Children set(Param column, Object val) {
        return set(true, column, val);
    }

    Children setSql(boolean condition, String setSql, Object... params);
    default Children setSql(String setSql, Object... params) {
        return setSql(true, setSql, params);
    }










}
