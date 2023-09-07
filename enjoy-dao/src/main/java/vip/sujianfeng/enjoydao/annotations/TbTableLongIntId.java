package vip.sujianfeng.enjoydao.annotations;

import vip.sujianfeng.enjoydao.MySqlAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:46
 * 表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbTableLongIntId {
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
