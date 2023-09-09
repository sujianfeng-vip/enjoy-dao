package vip.sujianfeng.enjoydao.annotations;

import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;

import java.lang.annotation.*;

/**
 * author SuJianFeng
 * createTime 2019/1/30 11:46
 **/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TbField {

    String tableField() default "";

    String tableFieldAlias() default "";

    String label() default "";


    boolean allowEmpty() default true;

    boolean allowDuplicates() default true;


    TbDefineFieldType fieldType() default TbDefineFieldType.ftString;


    int length() default 50;


    boolean isSystemField() default false;

    boolean visible() default true;
}
