package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.utils.comm.ReflectUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SQL statement parameter replacement, replacing {xxx} with?, And return the parameter array
 * author SuJianFeng
 * createTime 2020/9/2 11:29
 **/
public class SqlParamsParser {
    private String sql;
    private Object paramObj;
    private List<Object> paramList;

    public SqlParamsParser(String sql, Object paramObj) throws Exception {
        this.sql = sql;
        this.paramObj = paramObj;
        this.paramList = new ArrayList<>();
        this.build();
    }

    private void build() throws Exception {
        if (this.sql.contains("{") && this.sql.contains("}")){
            String left = StringUtilsEx.leftStr(this.sql, "{");
            String right = StringUtilsEx.rightStrEx(this.sql, "{");
            String key = StringUtilsEx.leftStr(right, "}");
            right = StringUtilsEx.rightStrEx(this.sql, "}");
            Object value = getParamValue(key);
            if (value == null){
                throw new Exception(String.format("Parameter [%s] data does not exist!", key));
            }
            this.paramList.add(value);
            this.sql = String.format("%s?%s", left, right);
            this.build();
        }
    }

    private Object getParamValue(String key) throws Exception {
        if (paramObj instanceof Map){
            return ((Map)paramObj).get(key);
        }
        Field f = ReflectUtils.getDeclaredField(paramObj.getClass(), key);
        if (f == null){
            throw new Exception(String.format("Property does not exist for this class [%s]:%s", paramObj.getClass().getName(), key));
        }
        f.setAccessible(true);
        return f.get(paramObj);
    }


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getParamObj() {
        return paramObj;
    }

    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public void setParamList(List<Object> paramList) {
        this.paramList = paramList;
    }
}
