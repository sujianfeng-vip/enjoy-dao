package vip.sujianfeng.enjoydao.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author SuJianFeng
 * @Date 2019/9/20 08:07
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbFieldTimestamp {
    /**
     * 对应数据库表字段
     * 如果为空，直接取类字段属性名
     * @return
     */
    String tableField() default "";

    /**
     * 字段别名
     * @return
     */
    String tableFieldAlias() default "";

    /**
     * 字段显示名
     * @return
     */
    String label() default "";

    /**
     * 是否允许为空
     * @return
     */
    boolean allowEmpty() default true;

    /**
     * 字段长度
     * @return
     */
    int length() default 50;

    /**
     * 是否系统预定字段
     * 是：使用方无需处理，有系统统一处理它的值
     * 否：使用方自行处理
     * @return
     */
    boolean isSystemField() default false;

    /**
     * 是否显示
     * @return
     */
    boolean visible() default true;
}
