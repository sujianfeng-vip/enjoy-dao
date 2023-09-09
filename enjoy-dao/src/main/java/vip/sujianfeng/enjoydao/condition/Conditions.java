package vip.sujianfeng.enjoydao.condition;

/**
 * author Xiao-Bai
 * createTime 2022/3/15 14:41
 **/
@SuppressWarnings("unchecked")
public class Conditions {

    public static <T> LambdaConditionWrapper<T> lambdaQuery(Class<T> entityClass) {
        return new LambdaConditionWrapper<>(entityClass);
    }

    public static <T> DefaultConditionWrapper<T> query(Class<T> entityClass) {
        return new DefaultConditionWrapper<>(entityClass);
    }

    public static <T> EmptyConditionWrapper<T> emptyQuery(Class<T> entityClass) {
        return new EmptyConditionWrapper<>(entityClass);
    }

    public static <T> DefaultConditionWrapper<T> allEqQuery(T entity) {
        DefaultConditionWrapper<T> conditionWrapper = new DefaultConditionWrapper<>((Class<T>) entity.getClass());
        AllEqualConditionHandler<T> equalConditionHandler = new AllEqualConditionHandler<>(entity, conditionWrapper);
        equalConditionHandler.allExecEqCondition();
        return conditionWrapper;
    }

    public static <T> DefaultUpdateSet<T> update(Class<T> entityClass) {
        return new DefaultUpdateSet<>(entityClass);
    }

    public static <T> LambdaUpdateSet<T> lambdaUpdate(Class<T> entityClass) {
        return new LambdaUpdateSet<>(entityClass);
    }
    
}
