package vip.sujianfeng.enjoydao.annotations;

import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author SuJianFeng
 * createTime 2019/2/2 17:10
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbRelationField {

    String joinTable();

    String joinTableAlias();


    String joinCondition();

    String srcField();


    String srcFieldAlias() default "";

    TbDefineFieldType fieldType() default TbDefineFieldType.ftString;

    boolean visible() default true;
}
