package vip.sujianfeng.enjoydao.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 注解解析工具类
 * @Author SuJianFeng
 * @Date 2022/10/25
 * @Description
 **/
public class AnnotationUtils {

    /**
     * 取得类字段注解
     * @param t
     * @param a
     * @param <T>
     * @param <A>
     * @return
     */
    public static <T, A extends Annotation> void getFieldAnnotation(Map<Field, A> map, Class<T> t, Class<A> a) {
        if (t == null) return;
        Field[] fields = t.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            A annotation = field.getAnnotation(a);
            if (annotation != null) {
                map.put(field, annotation);
            }
        }
        getFieldAnnotation(map, t.getSuperclass(), a);
    }

}
