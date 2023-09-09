
package vip.sujianfeng.enjoydao.condition.utils;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

/**
 * author Xiao-Bai
 * createTime 2021/1/12 0012 22:07
 */

@SuppressWarnings("unchecked")
public class CustomUtil extends StrUtils {

    public static boolean isBasicType(Object el) {
        return el instanceof CharSequence
                || el.getClass().isPrimitive()
                || el instanceof Integer
                || el instanceof Long
                || el instanceof Double
                || el instanceof Character
                || el instanceof Short
                || el instanceof Float
                || el instanceof Boolean
                || el instanceof Byte
                || el instanceof BigDecimal
                || el instanceof Date;
    }

    public static boolean isBasicClass(Class<?> cls) {
        return CharSequence.class.isAssignableFrom(cls)
                || cls.isPrimitive()
                || Integer.class.equals(cls)
                || Long.class.equals(cls)
                || Double.class.equals(cls)
                || Character.class.equals(cls)
                || Short.class.equals(cls)
                || Float.class.equals(cls)
                || Boolean.class.equals(cls)
                || Byte.class.equals(cls)
                || LocalDate.class.equals(cls)
                || BigInteger.class.equals(cls)
                || BigDecimal.class.equals(cls)
                || Date.class.equals(cls);
    }


    public static boolean isJavaOriginObject(Class<?> cls) {
        return cls.getPackage().getName().startsWith(Constants.JAVA_DOT);
    }

    public static String handleExecuteSql(String sql, Object[] params) {
        int symbolCount = countStr(sql, Constants.QUEST);
        int index = 0;
        while (index < symbolCount) {
            Object param = params[index];
            if(Objects.isNull(param)) {
                param = "null";
            }else if (param instanceof CharSequence) {
                param = String.format("'%s'", param);
            }
            sql = sql.replaceFirst("\\?", param.toString());
            index ++;
        }
        return sql;
    }


    public static boolean addParams(List<Object> thisParams, Object addVal) {
        Asserts.notNull(addVal);
        if (isBasicType(addVal)) {
            return thisParams.add(addVal);
        }
        if (addVal.getClass().isArray()) {
            return thisParams.addAll(Arrays.asList((Object[]) addVal));
        }
        if (addVal instanceof List) {
            return thisParams.addAll((List<Object>) addVal);
        }
        if (addVal instanceof Set) {
            return thisParams.addAll((Set<Object>) addVal);
        }
        throw new UnsupportedOperationException(String.format("Adding parameters of '%s' type is not supported", addVal.getClass()));
    }




}
