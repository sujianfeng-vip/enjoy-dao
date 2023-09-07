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
 * @Author SuJianFeng
 * @Date 2019/1/30 12:58
 **/
public interface TbDao {
    boolean isExistDb(String dbName) throws Exception;
    int createDb(String dbName) throws Exception;
    boolean isExistTable(String tableName) throws Exception;
    /**
     * 如果表不存在，那么自动创建表
     * @return
     */
    <T> int createTable(Class<T> t) throws Exception;
    <T> int createTable(TbTableBaseSql tbTableBaseSql) throws Exception;
    <T> int dropTable(Class<T> t) throws Exception;
    int dropTable(String tableName) throws Exception;

    /**
     * 查询数据库当前字段定义
     * @param tableName
     * @return
     */
    List<TbDefineField> queryDefineFields(String tableName) throws Exception;

    /**
     * 在当前数据库表新增字段
     * @return
     * @throws Exception
     */
    int addField(String tableName, TbDefineField tbDefineField) throws Exception;

    /**
     * 保存（不存在行新增，否则更新）
     * @return
     * @throws Exception
     */
    int save(AbstractOpModel model) throws Exception ;

    /**
     * 新增行
     * @return
     * @throws Exception
     */
    int insert(AbstractOpModel model) throws Exception ;

    /**
     * 更新（指定字段）
     * @param fields
     * @return
     * @throws Exception
     */
    int update(AbstractOpModel model, String... fields) throws Exception ;
    int updateFields(AbstractOpModel model, SqlConditionField<?>... fields) throws Exception ;
    int update(AbstractOpModel model, List<String> fields) throws Exception ;

    /**
     * 执行sql语句
     * @param sql
     * @return
     * @throws Exception
     */
    int executeSql(String sql, Object... args) throws Exception;
    int executeSql(String sql, ISqlNameSeatBuilder builder) throws Exception;

    /**
     * 新增
     * @param table
     * @return
     * @throws Exception
     */
    int insert(TbTableSql table) throws Exception ;
    int update(TbUpdateSql sql) throws Exception ;

    /**
     * 差异（数据库原值的基础上累加delta值）更新整形字段
     * @param fields
     * @param delta
     * @return
     */
    int updateDelta(AbstractOpModel model, int delta, String... fields) throws Exception;
    int updateDelta(AbstractOpModel model, int[] deltas, String... fields) throws Exception;

    /**
     * 逻辑删除
     * @return
     * @throws Exception
     */
    int delete(AbstractOpModel model) throws Exception ;
    int delete(AbstractOpModel model, String condition, Object... params) throws Exception ;

    /**
     * 恢复逻辑删除的记录
     * @param t
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> int recoveryDelete(Class<T> t, Object key) throws Exception ;
    <T> int recoveryDelete(Class<T> t, String condition, Object... params) throws Exception ;

    /**
     * 物理删除
     * @return
     * @throws Exception
     */
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


    /**
     * 比较两个数据集的差异，并保存入库
     * @param oldRows
     * @param newRows
     * @param <T>
     * @return
     */
    <T extends AbstractOpModel> int updateDataRows(List<T> oldRows, List<T> newRows) throws Exception;

    default String changeSql(String sql, List<Object> params) {
        return sql;
    }

}
