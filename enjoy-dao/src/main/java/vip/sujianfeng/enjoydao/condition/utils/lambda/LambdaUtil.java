package vip.sujianfeng.enjoydao.condition.utils.lambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.condition.CustomCheckException;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author Xiao-Bai
 * createTime 2022/8/26 23:58
 */

public final class LambdaUtil {

    private static final Logger log = LoggerFactory.getLogger(LambdaUtil.class);

    private final static Map<String, WeakReference<SerializedLambda>> FUNCTION_CACHE = new ConcurrentHashMap<>();

    public static <T> SerializedLambda resolve(SFunction<T, ?> function) {
        Class<?> aClass = function.getClass();
        String canonicalName = aClass.getCanonicalName();
        return Optional.ofNullable(FUNCTION_CACHE.get(canonicalName))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda serializedLambda = startParse(function);
                    FUNCTION_CACHE.put(canonicalName, new WeakReference<>(serializedLambda));
                    return serializedLambda;
                });
    }

    private static <T> SerializedLambda startParse(SFunction<T, ?> function) {
        SerializedLambda serializedLambda = null;
        Method writeMethod;
        try {
            // 从function中取出序列化方法
            writeMethod = function.getClass().getDeclaredMethod("writeReplace");
            writeMethod.setAccessible(true);
            serializedLambda = (SerializedLambda) writeMethod.invoke(function);
            writeMethod.setAccessible(false);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if (Objects.isNull(serializedLambda)) {
            throw new CustomCheckException("Unable to parse: " + function);
        }
        return serializedLambda;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getImplClass(SFunction<T, ?> function) {
        SerializedLambda serializedLambda = resolve(function);
        try {
            return (Class<T>) Class.forName(serializedLambda.getImplClass().replace('/', '.'));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static <T> String getImplMethodName(SFunction<T, ?> function) {
        SerializedLambda serializedLambda = resolve(function);
        return serializedLambda.getImplMethodName();
    }


    public static <T> Class<?> getImplFuncType(SFunction<T, ?> function) {
        SerializedLambda serializedLambda = resolve(function);
        String implMethodSignature = serializedLambda.getImplMethodSignature();
        if (implMethodSignature.length() <= 5 && !implMethodSignature.contains("/")) {
            String implMethodName = serializedLambda.getImplMethodName();
            log.error(implMethodName + " ==> The property corresponding to this method or method uses a base type, which may cause it to be unresolved. Please replace the return value of the property or method with the wrapper class corresponding to the base type");
        }
        implMethodSignature = implMethodSignature.substring(3, implMethodSignature.indexOf(";"));
        try {
            return Class.forName(implMethodSignature.replace('/', '.'));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }





}
