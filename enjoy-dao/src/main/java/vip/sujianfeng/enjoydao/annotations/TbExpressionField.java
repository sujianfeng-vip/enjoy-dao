package vip.sujianfeng.enjoydao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author SuJianFeng
 * @Date 2022/5/9
 * @Description 表达式计算字段
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbExpressionField {
    /**
     * 例如:
     * (1) a.quantity * a.price
     * (2) (select name from xxx limit 1)
     * @return
     */
    String value();
}
