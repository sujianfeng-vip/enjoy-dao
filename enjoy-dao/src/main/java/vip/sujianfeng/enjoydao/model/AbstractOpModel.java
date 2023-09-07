package vip.sujianfeng.enjoydao.model;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.annotations.TbTableUuid;
import vip.sujianfeng.utils.comm.ReflectUtils;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuJianFeng
 * @date 2019/8/9 9:06
 **/
public abstract class AbstractOpModel implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(AbstractOpModel.class);

    private List<Field> fields = null;

    public AbstractOpModel clone(){
        String json = JSON.toJSONString(this);
        return JSON.parseObject(json, this.getClass());
    }

    public String tableName() {
        TbTableUuid tbTableUuid = this.getClass().getAnnotation(TbTableUuid.class);
        return tbTableUuid.table();
    }

    public void checkFieldValue(TbDefineField field){

    }

    public List<Field> allDeclaredFields() {
        if (fields != null) {
            return fields;
        }
        fields = new ArrayList<>();
        Class<?> t = this.getClass();
        while (t != null) {
            fields.addAll(Arrays.asList(t.getDeclaredFields()));
            t = t.getSuperclass();
        }
        return fields;
    }

    public Field getDeclaredField(String fieldName){
        return ReflectUtils.getDeclaredField(this.getClass(), fieldName);
    }

    public boolean isExistField(String field) {
        Field f = this.getDeclaredField(field);
        return f != null;
    }

    public Object getFieldValue(String field) {
        try {
            return ReflectUtils.getFieldValue(this, field);
        } catch (IllegalAccessException e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

    public void setFieldValue(String field, Object value) {
        try {
            ReflectUtils.setFieldValue(this, field, value);
        } catch (IllegalAccessException e) {
            logger.error(e.toString(), e);
        }
    }


    public void beforeUpdate() throws Exception {

    }

    public void afterUpdate() throws Exception {

    }

    public void beforeInsert() throws Exception {

    }

    public void afterInsert() throws Exception {

    }

    public void beofreDelete() throws Exception {

    }


    public void afterDelete() throws Exception {

    }

    public abstract String getId();

    public abstract void setId(String id);

}
