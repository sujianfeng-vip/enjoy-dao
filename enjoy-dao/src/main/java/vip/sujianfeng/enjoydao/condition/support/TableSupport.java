package vip.sujianfeng.enjoydao.condition.support;

import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * author Xiao-Bai
 * createTime 2022/11/1 13:07
 */
public interface TableSupport {


    String table();

    String alias();

    Map<String, String> fieldMap();

    Map<String, String> columnMap();


    List<Field> fields();

    TbTableSql getTbTableSql();
}
