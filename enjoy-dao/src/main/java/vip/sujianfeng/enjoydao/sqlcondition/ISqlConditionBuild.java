package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * author SuJianFeng
 * createTime 2022/9/2
 * description
 **/
public interface ISqlConditionBuild<T extends SqlConditionBuilder<?>> {
    void build(T builder);
}
