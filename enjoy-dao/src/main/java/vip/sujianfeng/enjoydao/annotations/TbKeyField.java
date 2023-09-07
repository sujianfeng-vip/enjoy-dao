package vip.sujianfeng.enjoydao.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:46
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbKeyField {
    /**
     * 对应数据库表字段
     * @return
     */
    String tableField() default "id";
    /**
     * 字段显示名
     * @return
     */
    String label() default "主键";

}
