package vip.sujianfeng.enjoydao.interfaces;


import vip.sujianfeng.enjoydao.condition.ConditionWrapper;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.*;
import vip.sujianfeng.enjoydao.sqlcondition.ISqlConditionBuilder;
import vip.sujianfeng.enjoydao.sqlcondition.ISqlNameSeatBuilder;
import vip.sujianfeng.enjoydao.sqlcondition.SqlConditionField;

import java.util.List;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2019/1/30 12:58
 **/
public interface TbDao {
    boolean isExistDb(String dbName) throws Exception;
    int createDb(String dbName) throws Exception;
    boolean isExistTable(String tableName) throws Exception;

    <T> int createTable(Class<T> t) throws Exception;
    <T> int createTable(TbTableBaseSql tbTableBaseSql) throws Exception;
    <T> int dropTable(Class<T> t) throws Exception;
    int dropTable(String tableName) throws Exception;

    List<TbDefineField> queryDefineFields(String tableName) throws Exception;

    int addField(String tableName, TbDefineField tbDefineField) throws Exception;


    int save(AbstractOpModel model) throws Exception ;


    int insert(AbstractOpModel model) throws Exception ;


    int update(AbstractOpModel model, String... fields) throws Exception ;
    int updateFields(AbstractOpModel model, SqlConditionField<?>... fields) throws Exception ;
    int update(AbstractOpModel model, List<String> fields) throws Exception ;


    int executeSql(String sql, Object... args) throws Exception;
    int executeSql(String sql, ISqlNameSeatBuilder builder) throws Exception;

    int insert(TbTableSql table) throws Exception ;
    int update(TbUpdateSql sql) throws Exception ;


    int updateDelta(AbstractOpModel model, int delta, String... fields) throws Exception;
    int updateDelta(AbstractOpModel model, int[] deltas, String... fields) throws Exception;

    int delete(AbstractOpModel model) throws Exception ;
    int delete(AbstractOpModel model, String condition, Object... params) throws Exception ;


    <T> int recoveryDelete(Class<T> t, Object key) throws Exception ;
    <T> int recoveryDelete(Class<T> t, String condition, Object... params) throws Exception ;


    int deletePhysical(AbstractOpModel model) throws Exception ;
    int deletePhysical(AbstractOpModel model, String condition, Object... params) throws Exception ;

    boolean isExistField(AbstractOpModel model, String field);
    Object getFieldValue(AbstractOpModel model, String field) throws Exception;
    void setFieldValue(AbstractOpModel model, String field, Object value) throws Exception;

    <T> T selectOne(Class<T> t, long key) throws Exception;
    <T> T selectOne(Class<T> t, int key) throws Exception;
    <T> T selectOneByUuId(Class<T> t, String uuidKey) throws Exception;
    <T> T selectOneByCondition(Class<T> t, String condition, Object... params) throws Exception;
    <T> T selectOneByCondition(Class<T> t, ISqlConditionBuilder builder) throws Exception;
    <T> List<T> selectList(Class<T> t, String condition, int pageIndex, int pageSize) throws Exception;

    <T> List<T> selectList(Class<T> t, int pageIndex, int pageSize, String orderBy, String condition, Object... params) throws Exception;
    <T> List<T> selectList(Class<T> t, ISqlConditionBuilder builder) throws Exception;
    <T> List<T> selectList(Class<T> t, String condition, int pageIndex, int pageSize, String orderBy) throws Exception;
    <T> TbPageRows<T> selectPageRows(Class<T> t, String condition, int pageIndex, int pageSize) throws Exception;

    <T> TbPageRows<T> selectPageRows(Class<T> t, int pageIndex, int pageSize, String orderBy, String condition, Object... params) throws Exception;
    <T> TbPageRows<T> selectPageRows(Class<T> t, ISqlConditionBuilder builder) throws Exception;
    <T> List<T> selectListBySql(Class<T> t, String sql, Object... args) throws Exception;
    <T> List<T> selectListBySql(Class<T> t, String sql, ISqlNameSeatBuilder builder) throws Exception;
    List<Map<String, Object>> selectListBySql(String sql, Object... args) throws Exception;
    <T> TbPageRows<T> selectPageRows(Class<T> t, String condition, int pageIndex, int pageSize, String orderBy) throws Exception;

    <T> T selectOneBySql(Class<T> t, String sql, Object... args) throws Exception;
    <T> T selectOneBySql(Class<T> t, String sql, ISqlNameSeatBuilder builder) throws Exception;
    Object selectOneBySql(String sql, Object... args) throws Exception;
    Object selectOneBySql(String sql, ISqlNameSeatBuilder builder) throws Exception;

    <T> List<T> selectList(ConditionWrapper<T> wrapper) throws Exception;
    <T> T selectOne(ConditionWrapper<T> wrapper) throws Exception;
    <T> TbPageRows<T> selectPageRows(ConditionWrapper<T> wrapper) throws Exception;
    <T> int selectCount(ConditionWrapper<T> wrapper) throws Exception;


    <T extends AbstractOpModel> int updateDataRows(List<T> oldRows, List<T> newRows) throws Exception;

    default String changeSql(String sql, List<Object> params) {
        return sql;
    }

}
