package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author SuJianFeng
 * createTime 2019/1/30 11:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbTableUuid {

    String table() default "";

    String tableAlias() default "a";

    String orderBy() default "a.create_time desc";
}
