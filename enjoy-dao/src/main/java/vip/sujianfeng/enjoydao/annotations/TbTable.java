package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.*;

/**
 * author SuJianFeng
 * createTime 2019/1/30 11:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbTable {

    String table() default "";

    String tableAlias() default "a";

    String orderBy() default "a.id desc";
}
