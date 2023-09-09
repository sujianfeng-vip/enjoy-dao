package vip.sujianfeng.enjoydao.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.condition.support.ColumnParseHandler;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.condition.utils.ReflectUtil;
import vip.sujianfeng.enjoydao.condition.utils.lambda.LambdaUtil;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author Xiao-Bai
 * createTime 2022/3/3 14:45
 **/
public class DefaultColumnParseHandler<T> implements ColumnParseHandler<T> {

    private static final Logger log = LoggerFactory.getLogger(DefaultColumnParseHandler.class);

    private final Class<T> thisClass;
    private final List<Field> fieldList;
    private final Map<String, String> fieldMapper;
    private final TableSupport tableSupport;

    private final static Map<String, Map<String, ColumnFieldCache>> COLUMN_CACHE = new ConcurrentHashMap<>();

    public DefaultColumnParseHandler(Class<T> thisClass, TableSupport tableSupport) {
        this.thisClass = thisClass;
        this.fieldMapper = tableSupport.fieldMap();
        this.fieldList = tableSupport.fields();
        this.tableSupport = tableSupport;

    }

    public Map<String, ColumnFieldCache> createColumnCache() {
        Map<String, ColumnFieldCache> cacheMap = new HashMap<>();
        List<PropertyDescriptor> properties = null;
        try {
            properties = ReflectUtil.getProperties(thisClass);
        } catch (IntrospectionException e) {
            log.error(e.toString(), e);
        }
        Asserts.notNull(properties, thisClass.getName() + "The property read is empty");
        for (PropertyDescriptor property : properties) {
            String getter = property.getReadMethod().getName();
            String field = property.getName();
            String column = tableSupport.fieldMap().get(field);
            ColumnFieldCache fieldCache = new ColumnFieldCache(field, getter, column);
            cacheMap.put(property.getReadMethod().getName(), fieldCache);
        }
        COLUMN_CACHE.put(thisClass.getName(), cacheMap);
        return cacheMap;
    }


    @Override
    public Class<T> getThisClass() {
        return this.thisClass;
    }

    @Override
    public List<Field> loadFields() {
        return this.fieldList;
    }

    @Override
    public String parseToField(SFunction<T, ?> func) {
        Asserts.notNull(func);
        SerializedLambda serializedLambda = LambdaUtil.resolve(func);
        String implMethodName = serializedLambda.getImplMethodName();

        Map<String, ColumnFieldCache> fieldCacheMap = Optional.ofNullable(COLUMN_CACHE.get(thisClass.getName()))
                .orElseGet(this::createColumnCache);

        ColumnFieldCache fieldCache = fieldCacheMap.get(implMethodName);
        if (fieldCache == null) {
            throw new CustomCheckException("Cannot find a matching property with method name: '%s'", implMethodName);
        }
        return fieldCache.getField();
    }

    @Override
    public String parseToColumn(SFunction<T, ?> func) {
        String field = parseToField(func);
        String column = fieldMapper.get(field);
        if(Objects.isNull(column)) {
            throw new CustomCheckException("Property '" + field + "',  the table field mapped by this attribute cannot be found");
        }
        return column;
    }

    @Override
    public String parseToNormalColumn(SFunction<T, ?> func) {
        return null;
    }
}
