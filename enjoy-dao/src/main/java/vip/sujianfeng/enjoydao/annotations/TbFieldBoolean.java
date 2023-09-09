package vip.sujianfeng.enjoydao.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author SuJianFeng
 * createTime 2019/9/20 08:07
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbFieldBoolean {

    String tableField() default "";

    String tableFieldAlias() default "";

    String label() default "";

    boolean allowEmpty() default true;

    int length() default 50;

    boolean isSystemField() default false;

    boolean visible() default true;
}
