package vip.sujianfeng.enjoydao.condition.utils;

import vip.sujianfeng.enjoydao.condition.CustomCheckException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author Xiao-Bai
 * createTime 2022/11/16 0016 17:00
 */
public class ReflectUtil {


    public static <T> List<Field> loadFields(Class<T> t) {
        Class<?> clz = t;
        List<Field> fieldList = new ArrayList<>();
        while (clz != null) {
            Arrays.stream(clz.getDeclaredFields()).forEach(field -> {
                int modifiers = field.getModifiers();
                if (Modifier.isPrivate(modifiers)
                        && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
                    fieldList.add(field);
                }
            });
            clz = clz.getSuperclass();
        }
        return fieldList;
    }

    public static <T> List<PropertyDescriptor> getProperties(Class<T> cls) throws IntrospectionException {
        Asserts.npe(cls);
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        return Arrays.stream(propertyDescriptors).filter(x -> !x.getName().equals("class")).collect(Collectors.toList());
    }


    public static Map<String, Object> beanToMap(Object bean) throws IntrospectionException {
        Class<?> thisClass = bean.getClass();
        Map<String, Object> resMap = new HashMap<>();
        List<PropertyDescriptor> propertyDescriptors = getProperties(thisClass);
        for (PropertyDescriptor property : propertyDescriptors) {
            String propertyName = property.getName();
            Method readMethod = property.getReadMethod();
            try {
                Object proValue = readMethod.invoke(bean);
                resMap.put(propertyName, proValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return resMap;
    }

    public static <T> T getInstance(Class<T> t)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = t.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    public static <T> Method getMethod(Class<T> t, String methodName) throws IntrospectionException {

        Asserts.npe(methodName);
        List<PropertyDescriptor> propertyDescriptors = getProperties(t);
        List<Method> methodList = new ArrayList<>();
        propertyDescriptors.forEach(op -> {
            methodList.add(op.getReadMethod());
            methodList.add(op.getWriteMethod());
        });
        return methodList.stream()
                .filter(x -> x.getName().equals(methodName)).findFirst()
                .orElseThrow(() -> new CustomCheckException(String.format("%s method not found in %s", methodName, t)));
    }


    public static Type[] getGenericTypes(Method method) {
        if (method == null) {
            return null;
        }
        ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
        return returnType.getActualTypeArguments();
    }

    private Map<String, Integer> getInfo() {
        return new HashMap<>();
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = ReflectUtil.class.getDeclaredMethod("getInfo");
        Type[] genericType = getGenericTypes(method);
        System.out.println("genericType = " + Arrays.toString(genericType));
    }










}
