package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author SuJianFeng
 * createTime 2022/10/25
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbMultiRelationField {

    String[] tables();

    String[] srcFields();

    String idField();

}
