package vip.sujianfeng.enjoydao.model;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.core.utils.ReflectUtils;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;

import java.lang.reflect.Field;

/**
 * @author SuJianFeng
 * @date 2019/8/9 9:06
 **/
public abstract class AbstractOpModel {
    private static Logger logger = LoggerFactory.getLogger(AbstractOpModel.class);

    public AbstractOpModel clone(){
        String json = JSON.toJSONString(this);
        return JSON.parseObject(json, this.getClass());
    }

    public void checkFieldValue(TbDefineField field){

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

    public void afterDelete() throws Exception {

    }

    public abstract String getId();

    public abstract void setId(String id);

}
