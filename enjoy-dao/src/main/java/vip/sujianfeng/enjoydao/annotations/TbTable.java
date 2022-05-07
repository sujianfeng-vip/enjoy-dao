package vip.sujianfeng.enjoydao.annotations;

import vip.sujianfeng.enjoydao.MySqlAdapter;
import vip.sujianfeng.enjoydao.enums.TbKeyType;

import java.lang.annotation.*;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:46
 * 表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbTable {
    /**
     * 表名
     * @return
     */
    String table() default "";
    /**
     * 别名
     * @return
     */
    String tableAlias() default "a";
    /**
     * 主键类型
     * @return
     */
    TbKeyType keyType() default TbKeyType.AUTO_INC_INT;

    /**
     * 排序字段
     * @return
     */
    String orderBy() default "a.id desc";
}
