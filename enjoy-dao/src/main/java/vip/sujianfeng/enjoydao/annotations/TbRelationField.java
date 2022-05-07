package vip.sujianfeng.enjoydao.annotations;

import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author SuJianFeng
 * @Date 2019/2/2 17:10
 * 关联字段：需要join其他表关联出来的字段
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TbRelationField {
    /**
     * 关联表名
     * @return
     */
    String joinTable();
    /**
     * 关联表-别名
     * @return
     */
    String joinTableAlias();

    /**
     * 关联条件
     * @return
     */
    String joinCondition();

    /**
     * 来源字段名
     * @return
     */
    String srcField();

    /**
     * 字段别名
     * @return
     */
    String srcFieldAlias() default "";

    /**
     * 字段类型
     * @return
     */
    TbDefineFieldType fieldType() default TbDefineFieldType.ftString;

    /**
     * 是否显示
     * @return
     */
    boolean visible() default true;
}
