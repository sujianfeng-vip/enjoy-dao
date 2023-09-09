package vip.sujianfeng.enjoydao.condition;

/**
 * author Xiao-Bai
 * createTime 2022/8/6 23:43
 */
public abstract class AbstractUpdateSet<T> {

    private UpdateSetWrapper<T> updateSetWrapper;

    private ConditionWrapper<T> conditionWrapper;
    private final Class<T> entityClass;


    public AbstractUpdateSet(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public UpdateSetWrapper<T> getUpdateSetWrapper() {
        return updateSetWrapper;
    }

    protected void setUpdateSetWrapper(UpdateSetWrapper<T> updateSetWrapper) {
        this.updateSetWrapper = updateSetWrapper;
    }

    public ConditionWrapper<T> getConditionWrapper() {
        return conditionWrapper;
    }

    protected void setConditionWrapper(ConditionWrapper<T> conditionWrapper) {
        this.conditionWrapper = conditionWrapper;
    }

    public Class<T> thisEntityClass() {
        return entityClass;
    }
}
