package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public interface ISqlConditionBuild<T extends SqlConditionBuilder<?>> {
    void build(T builder);
}
