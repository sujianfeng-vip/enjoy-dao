package vip.sujianfeng.enjoydao.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.condition.utils.ReflectUtil;

import java.beans.IntrospectionException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Xiao-Bai
 * @Date 2022/7/21 0021 14:44
 * @Desc 全等条件解析处理
 */
public class AllEqualConditionHandler<T> {

    private static final Logger logger = LoggerFactory.getLogger(AllEqualConditionHandler.class);

    private final Object entity;
    private final Map<String, String> fieldMapper;
    private final DefaultConditionWrapper<T> conditionWrapper;

    public AllEqualConditionHandler(T entity, DefaultConditionWrapper<T> conditionWrapper) {
        this.entity = entity;
        this.conditionWrapper = conditionWrapper;
        this.fieldMapper = conditionWrapper.getTableSupport().fieldMap();
    }

    /**
     * 条件拼接
     */
    public void allExecEqCondition() {
        try {
            Map<String, Object> parseMap = ReflectUtil.beanToMap(entity);
            parseMap.forEach((key, value) -> {
                if (fieldMapper.containsKey(key) && Objects.nonNull(value)) {
                    conditionWrapper.eq(fieldMapper.get(key), value);
                }
            });
        } catch (IntrospectionException e) {
            logger.error("There is a problem with the current entity, please check");
            throw new CustomCheckException(e.getMessage());
        }
    }




}
