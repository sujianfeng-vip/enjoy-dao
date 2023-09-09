package vip.sujianfeng.enjoydao.utils;

import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛型实际类缓存工具类
 * author SuJianFeng
 * createTime 2022/12/2
 * description
 **/
public class TypeMapUtils {
    public static Map<String, Type> TYPE_MAP = new HashMap<>();

    private static String getKey(Object obj, String tag) {
        String key = StringUtilsEx.leftStr(obj.getClass().getName(), "$");
        return String.format("%s-%s", key, tag);
    }

    public static void add(Object obj, String tag, Type type) {
        TYPE_MAP.put(getKey(obj, tag), type);
    }

    public static Type get(Object obj, String tag) {
        return TYPE_MAP.get(getKey(obj, tag));
    }
}
