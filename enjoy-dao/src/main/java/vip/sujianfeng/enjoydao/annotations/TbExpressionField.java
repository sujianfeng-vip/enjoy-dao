package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author SuJianFeng
 * createTime 2022/5/9
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbExpressionField {
    /**
     * (1) a.quantity * a.price
     * (2) (select name from xxx limit 1)
     * @return sql
     */
    String value();
}
