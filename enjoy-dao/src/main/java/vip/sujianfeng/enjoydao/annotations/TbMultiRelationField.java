package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多关联字段
 * @Author SuJianFeng
 * @Date 2022/10/25
 * @Description
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbMultiRelationField {
    /**
     * 多表关联（和每个来源字段一一对应）
     * @return
     */
    String[] tables();

    /**
     * 来源字段（和每个表一一对应）
     * @return
     */
    String[] srcFields();
    /**
     * 外键字段
     * @return
     */
    String idField();

}
