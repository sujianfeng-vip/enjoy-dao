package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.*;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:46
 * 表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbTable {
    /**
     * 表名
     * @return
     */
    String table() default "";
    /**
     * 别名
     * @return
     */
    String tableAlias() default "a";

    /**
     * 排序字段
     * @return
     */
    String orderBy() default "a.id desc";
}
