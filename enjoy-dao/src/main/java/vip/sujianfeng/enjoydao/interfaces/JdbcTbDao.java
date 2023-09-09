package vip.sujianfeng.enjoydao.interfaces;

import vip.sujianfeng.enjoydao.condition.ConditionWrapper;
import vip.sujianfeng.enjoydao.condition.CustomCheckException;
import vip.sujianfeng.enjoydao.condition.ThisQuery;
import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.db.DbColumn;
import vip.sujianfeng.enjoydao.db.DbUtils;
import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.model.DeltaData;
import vip.sujianfeng.enjoydao.sqlbuilder.*;
import vip.sujianfeng.enjoydao.sqlcondition.ISqlConditionBuilder;
import vip.sujianfeng.enjoydao.sqlcondition.ISqlNameSeatBuilder;
import vip.sujianfeng.enjoydao.sqlcondition.SqlConditionField;
import vip.sujianfeng.enjoydao.utils.DatasetUtils;
import vip.sujianfeng.enjoydao.utils.TbMultiRelationUtils;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2019/12/24 13:55
 **/
public abstract class JdbcTbDao extends JdbcActionTbDao implements TbDao {

    public abstract String insertSql(TbTableSql table, List<Object> params);
    public abstract String updateSql(TbUpdateSql updateSql, List<Object> params);
    public abstract String updateDeltaSql(AbstractOpModel model, List<Object> params, int[] deltas, String... fields);
    public abstract String deleteSql(Class<? extends AbstractOpModel> t, String condition);
    public abstract String recoveryDeleteSql(TbTableSql tbTableSql);
    public abstract String deletePhysicalSql(AbstractOpModel model, String condition);
    public abstract String selectListSql(TbSelectSql tbSelectSql, String condition);
    public abstract String getJoinTableSql(TbSelectSql tbSelectSql);
    protected abstract String getTotalSizeSql(TbSelectSql tbSelectSql, String condition);
    protected abstract String isExistTableSql(String tableName);
    protected abstract String createTableSql(TbTableBaseSql tbTableBaseSql);
    protected abstract String dropTableSql(String tableName);
    protected abstract String mergeTableName(String tableName);


    public JdbcTbDao(SqlAdapter sqlAdapter, DataSource dataSource, String dbName) {
        super(sqlAdapter, dataSource);
        this.dbName = dbName;
    }

    private String dbName;
    
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int createTables(Class<?>... arr) throws Exception {
        int result = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            result += createTable(arr[i]);
        }
        return result;
    }

    @Override
    public boolean isExistDb(String dbName) throws Exception {
        return false;
    }

    @Override
    public int createDb(String dbName) throws Exception {
        return 0;
    }

    @Override
    public boolean isExistTable(String tableName) throws Exception {
        Object table = dbConnAction(conn->{
            return DbUtils.selectValueSql(conn, isExistTableSql(tableName));
        });
        return StringUtilsEx.isNotEmpty(table);
    }

    @Override
    public <T> int createTable(Class<T> t) throws Exception {
        TbTableBaseSql tbTableSql = new TbTableSql(this.getSqlAdapter(), t);
        if (tbTableSql.getDefineTable() == null){
            throw new Exception(String.format("this class [%s] table annotation is not set!", t.getName()));
        }
        if (isExistTable(tbTableSql.getTableName())){
            return 0;
        }
        return createTable(tbTableSql);
    }


    @Override
    public int createTable(TbTableBaseSql tbTableBaseSql) throws Exception {
        return dbConnAction(conn -> {
            DbUtils.executeSql(conn,  createTableSql(tbTableBaseSql));
            return 1;
        });
    }

    @Override
    public <T> int dropTable(Class<T> t) throws Exception {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), t);
        return dropTable(tbTableSql.getTableName());
    }

    @Override
    public int dropTable(String tableName) throws Exception {
        return dbConnAction(conn -> {
            return DbUtils.executeSql(conn,  dropTableSql(tableName));
        });

    }

    @Override
    public int save(AbstractOpModel model) throws Exception {
        int i = update(model);
        if (i == 0){
            i = insert(model);
        }
        return i;
    }

    @Override
    public int insert(AbstractOpModel model) throws Exception {
        model.beforeInsert();
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), model);
        int i = insert(tbTableSql);
        if (i > 0){
            model.afterInsert();
            model.setId(tbTableSql.getId());
        }
        return i;
    }

    protected void beforeUpdateCheck(TbUpdateSql tbUpdateSql) throws Exception {
        for (TbDefineField tbDefineField: tbUpdateSql.getUpdateFields()){
            if (tbUpdateSql.getModel() != null){
                tbUpdateSql.getModel().checkFieldValue(tbDefineField);
            }
            if (!tbDefineField.isAllowEmpty() && tbDefineField.getFieldType() == TbDefineFieldType.ftString
                    && StringUtilsEx.isEmpty(tbDefineField.getValue())){
                throw new Exception(String.format("%s不能为空!",
                        StringUtilsEx.isEmpty(tbDefineField.getLabel()) ? tbDefineField.getField() : tbDefineField.getLabel()));
            }
        }
    }

    @Override
    public int update(AbstractOpModel model, String... fields) throws Exception {
       return update(model, Arrays.asList(fields));
    }

    @Override
    public int updateFields(AbstractOpModel model, SqlConditionField<?>... fields) throws Exception {
        List<String> fieldNames = new ArrayList<>();
        for (SqlConditionField<?> field : fields) {
            fieldNames.add(field.getFieldName());
        }
        return update(model, fieldNames);
    }

    @Override
    public int update(AbstractOpModel model, List<String> fields) throws Exception {
        model.beforeUpdate();
        TbUpdateSql sql = new TbUpdateSql(this.getSqlAdapter(), model.getClass(), model, fields);
        beforeUpdateCheck(sql);
        if (StringUtilsEx.isEmpty(model.getId()) || StringUtilsEx.sameText("0", ConvertUtils.cStr(model.getId()))){
            return 0; //如果没有key，那么取消update
        }
        int i = update(sql);
        if (i > 0){
            model.afterUpdate();
        }
        return i;
    }

    @Override
    public int executeSql(String sql, Object... args) throws Exception {
        return dbConnAction(conn -> DbUtils.executeSql(conn, sql, args));
    }

    @Override
    public int executeSql(String sql, ISqlNameSeatBuilder builder) throws Exception {
        sql += "\n" + builder.getExpression().toString();
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, builder.getParamMap());
        return executeSql(sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    public int executeSqlForName(String sql, Object paramObj) throws Exception {
        return dbConnAction(conn -> {
            SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
            return DbUtils.executeSql(conn, sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
        });
    }

    @Override
    public int insert(TbTableSql table) throws Exception {
        return dbConnAction(conn -> {
            List<Object> params = new ArrayList<>();
            String sql = insertSql(table, params);
            sql = changeSql(sql, params);
            return (int) DbUtils.insertSql(conn, false, sql, params.toArray());
        });
    }

    @Override
    public int update(TbUpdateSql updateSql) throws Exception {
        return dbConnAction(conn -> {
            List<Object> params = new ArrayList<>();
            String sql = updateSql(updateSql, params);
            sql = this.changeSql(sql, params);
            return DbUtils.executeSql(conn, sql, params);
        });
    }

    @Override
    public int updateDelta(AbstractOpModel model, int delta, String... fields) throws Exception {
        int[] deltas = new int[fields.length];
        Arrays.fill(deltas, delta);
        return updateDelta(model, deltas, fields);
    }

    @Override
    public int updateDelta(AbstractOpModel model, int[] deltas, String... fields) throws Exception {
        return dbConnAction(conn -> {
            List<Object> params = new ArrayList<>();
            String sql = updateDeltaSql(model, params, deltas, fields);
            sql = this.changeSql(sql, params);
            return DbUtils.executeSql(conn, sql, params);
        });
    }

    @Override
    public int delete(AbstractOpModel model) throws Exception {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), model);
        return delete(model, String.format(" and %s.id = ?", tbTableSql.getTableAlias()), model.getId());
    }


    @Override
    public int delete(AbstractOpModel model, String condition, Object... params) throws Exception {
        model.beofreDelete();
        try {
            return delete(model.getClass(), condition, params);
        }finally {
            model.afterDelete();
        }
    }

    public int delete(Class<? extends AbstractOpModel> t, String condition, Object... params) throws Exception {
        List<Object> objects = Arrays.asList(params);
        String sql = this.changeSql(deleteSql(t, condition), objects);
        return dbConnAction(conn -> {
            return DbUtils.executeSql(conn, sql, objects.toArray());
        });
    }

    @Override
    public <T> int recoveryDelete(Class<T> aClass, Object key) throws Exception {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), aClass);
        return dbConnAction(conn -> {
            return DbUtils.executeSql(conn, recoveryDeleteSql(tbTableSql), key);
        });

    }

    @Override
    public <T> int recoveryDelete(Class<T> aClass, String condition, Object... objects) throws Exception {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), aClass);
        return dbConnAction(conn -> {
            return DbUtils.executeSql(conn, recoveryDeleteSql(tbTableSql) + condition, objects);
        });
    }

    @Override
    public int deletePhysical(AbstractOpModel model) throws Exception {
        return deletePhysical(model, " and id = ?", model.getId());
    }

    @Override
    public int deletePhysical(AbstractOpModel model, String condition, Object... params) throws Exception {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), model);
        return dbConnAction(conn -> {
            return DbUtils.executeSql(conn, deletePhysicalSql(model, condition), params);
        });
    }

    @Override
    public boolean isExistField(AbstractOpModel model, String field) {
        return model.isExistField(field);
    }

    @Override
    public Object getFieldValue(AbstractOpModel model, String field) throws Exception {
        return model.getFieldValue(field);
    }

    @Override
    public void setFieldValue(AbstractOpModel model, String field, Object value) throws Exception {
        model.setFieldValue(field, value);
    }


    @Override
    public Object selectOneBySql(String s, Object... objects) throws Exception {
        return dbConnAction(conn -> DbUtils.selectValueSql(conn, s, objects));
    }

    public Object selectOneBySqlForName(String sql, Object paramObj) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
        return selectOneBySql(sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public <T> List<T> selectListBySql(Class<T> t, String sql, Object... objects) throws Exception{
        List<T> rows = dbConnAction(conn -> DbUtils.queryRows(conn, t, sql, objects));
        TbMultiRelationUtils.batchSetRelationFieldValue(this, t, rows);
        return rows;
    }

    @Override
    public <T> List<T> selectListBySql(Class<T> t, String sql, ISqlNameSeatBuilder builder) throws Exception {
        sql += "\n" + builder.getExpression().toString();
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, builder.getParamMap());
        return selectListBySql(t, sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public List<Map<String, Object>> selectListBySql(String sql, Object... args) throws Exception {
        return dbConnAction(conn -> DbUtils.queryRows(conn, (field, value) -> value, sql, args));
    }

    public <T> List<T> selectListBySqlForName(Class<T> t, String sql, Object paramObj) throws Exception{
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
        return selectListBySql(t, sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    public List<Map<String, Object>> selectListBySqlForName(String sql, Object paramObj) throws Exception{
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
        return selectListBySql( sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    public List<DbColumn> queryDbColumnDefinesBySqlForName(String sql, Object paramObj) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
        return queryDbColumnDefinesBySql(sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    public List<DbColumn> queryDbColumnDefinesBySql(String sql, Object... params) throws Exception {
        return dbConnAction(conn -> DbUtils.queryDbColumnDefinesBySql(conn, sql, params));
    }

    @Override
    public <T> T selectOne(Class<T> t, long key) throws Exception {
        return selectOneByCondition(t, String.format(" and a.id = %s", key));
    }

    @Override
    public <T> T selectOne(Class<T> aClass, int i) throws Exception {
        return selectOne(aClass, Long.valueOf(i));
    }

    @Override
    public <T> T selectOneByUuId(Class<T> t, String uuidKey) throws Exception {
        return selectOneByCondition(t, String.format(" and a.id = '%s'", uuidKey));
    }

    @Override
    public <T> T selectOneBySql(Class<T> t, String sql, Object... args) throws Exception {
        return dbConnAction(conn -> DbUtils.queryObject(conn, t, sql, args));
    }

    @Override
    public <T> T selectOneBySql(Class<T> t, String sql, ISqlNameSeatBuilder builder) throws Exception {
        return selectOneBySqlForName(t, sql + "\n" + builder.getExpression(), builder.getParamMap());
    }

    @Override
    public Object selectOneBySql(String sql, ISqlNameSeatBuilder builder) throws Exception {
        return selectOneBySqlForName( sql + "\n" + builder.getExpression(), builder.getParamMap());
    }

    public <T> T selectOneBySqlForName(Class<T> t, String sql, Object paramObj) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(sql, paramObj);
        return selectOneBySql(t, sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public <T> T selectOneByCondition(Class<T> t, String condition, Object... params) throws Exception {
        List<T> rows = selectList(t, 1, 1, "", condition, params);
        if (rows.size() > 0){
            return rows.get(0);
        }
        return null;
    }

    @Override
    public <T> T selectOneByCondition(Class<T> t, ISqlConditionBuilder builder) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(builder.getExpression().toString(), builder.getParamMap());
        return selectOneByCondition(t, sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public <T> List<T> selectList(Class<T> t, String condition, int pageIndex, int pageSize) throws Exception {
        return selectList(t, condition, pageIndex, pageSize, null);
    }

    @Override
    public <T> TbPageRows<T> selectPageRows(Class<T> t, String condition, int pageIndex, int pageSize) throws Exception {
        return selectPageRows(t, condition, pageIndex, pageSize, "");
    }


    @Override
    public <T> List<T> selectList(Class<T> t, int pageIndex, int pageSize, String orderBy, String condition, Object... params) throws Exception {
        TbSelectSql tbSelectSql = new TbSelectSql(this.getSqlAdapter(), t, pageIndex, pageSize, orderBy);
        tbSelectSql.setPageIndex(pageIndex);
        tbSelectSql.setPageSize(pageSize);
        tbSelectSql.setOrderBy(orderBy);
        return selectList(t, tbSelectSql, condition, params);
    }

    public <T> List<T> selectList(Class<T> t, TbSelectSql tbSelectSql, String condition, Object... params) throws Exception {
        List<Object> objects = Arrays.asList(params);
        String sql = changeSql(selectListSql(tbSelectSql, condition), objects);
        List<T> rows = dbConnAction(conn -> DbUtils.queryObjects(conn, t , sql, objects.toArray()));
        TbMultiRelationUtils.batchSetRelationFieldValue(this, t, rows);
        return rows;
    }

    @Override
    public <T> List<T> selectList(Class<T> t, String condition, int pageIndex, int pageSize, String orderBy) throws Exception {
        return selectList(t, pageIndex, pageSize, orderBy, condition);
    }

    @Override
    public <T> List<T> selectList(Class<T> t, ISqlConditionBuilder builder) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(builder.getExpression().toString(), builder.getParamMap());
        return selectList(t, builder.getPageNo(), builder.getPageSize(), builder.getOrderBy(), sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public <T> TbPageRows<T> selectPageRows(Class<T> t, String condition, int pageIndex, int pageSize, String orderBy) throws Exception {
        return selectPageRows(t, pageIndex, pageSize, orderBy, condition);
    }

    @Override
    public <T> TbPageRows<T> selectPageRows(Class<T> t, int pageIndex, int pageSize, String orderBy, String condition, Object... params) throws Exception {
        TbSelectSql tbSelectSql = new TbSelectSql(this.getSqlAdapter(), t, pageIndex, pageSize, orderBy);
        return selectPageRows(t, tbSelectSql, condition, params);
    }

    public <T> TbPageRows<T> selectPageRows(Class<T> t, TbSelectSql tbSelectSql, String condition, Object... params) throws Exception {
        String sqlTmp = getTotalSizeSql(tbSelectSql, condition);
        List<Object> objects = Arrays.asList(params);
        String sql = this.changeSql(sqlTmp, objects);
        int totalSize = dbConnAction(conn -> ConvertUtils.cInt(DbUtils.selectValueSql(conn, sql, objects.toArray())));
        TbPageRows<T> result = new TbPageRows<>(condition, tbSelectSql.getPageIndex(), tbSelectSql.getPageSize());
        result.setTotalSize(totalSize);
        result.setRows(selectList(t, tbSelectSql, condition, params));
        return result;
    }

    @Override
    public <T> TbPageRows<T> selectPageRows(Class<T> t, ISqlConditionBuilder builder) throws Exception {
        SqlParamsParser sqlParamsParser = new SqlParamsParser(builder.getExpression().toString(), builder.getParamMap());
        return selectPageRows(t, builder.getPageNo(), builder.getPageSize(), builder.getOrderBy(), sqlParamsParser.getSql(), sqlParamsParser.getParamList().toArray());
    }

    @Override
    public <T extends AbstractOpModel> int updateDataRows(List<T> oldRows, List<T> newRows) throws Exception {
        DeltaData<T> delta = DatasetUtils.getDelta(oldRows, newRows);
        DatasetUtils.updateDelta(this, delta);
        return delta.getNewRows().size() + delta.getUpdateRows().size() + delta.getDeleteRows().size();
    }

    @Override
    public <T> List<T> selectList(ConditionWrapper<T> wrapper) throws Exception {
        Asserts.notNull(wrapper);
        String joinSql = getJoinTableSql(wrapper);
        ThisQuery thisQuery = new ThisQuery(wrapper, joinSql);
        String selectSql = changeSql(thisQuery.getSelectSql(), thisQuery.getParams());
        return selectListBySql(wrapper.getEntityClass(), selectSql, thisQuery.getParams().toArray());
    }

    private <T> String getJoinTableSql(ConditionWrapper<T> wrapper) {
        StringBuilderEx joinSql = new StringBuilderEx();
        TbTableSql tbTableSql = wrapper.getTableSupport().getTbTableSql();
        for (TbDefineRelationField rlsField : tbTableSql.getRlsFieldList()) {
            String joinLine = String.format("LEFT JOIN %s %s on %s", mergeTableName(rlsField.getJoinTable()), rlsField.getJoinTableAlias(), rlsField.getJoinCondition());
            if (!joinSql.toString().contains(joinLine)) {
                joinSql.appendRow(joinLine);
            }
        }
        return joinSql.toString();
    }

    @Override
    public <T> T selectOne(ConditionWrapper<T> wrapper) throws Exception {
        List<T> list = selectList(wrapper);
        if (list.size() == 0) {
            return null;
        } else if (list.size() > 1) {
            throw new CustomCheckException("Query a result but obtain" + list.size());
        }
        return list.get(0);
    }

    @Override
    public <T> TbPageRows<T> selectPageRows(ConditionWrapper<T> wrapper) throws Exception {
        Asserts.notNull(wrapper);
        if(!wrapper.hasPageParams()) {
            throw new CustomCheckException("Missing paging parameter:pageIndex:%s, pageSize:%s", wrapper.getPageIndex(), wrapper.getPageSize());
        }
        String joinTableSql = getJoinTableSql(wrapper);
        ThisQuery thisQuery = new ThisQuery(wrapper, joinTableSql);
        String selectSql = changeSql(thisQuery.getSelectSql(), thisQuery.getParams());
        TbPageRows<T> pageRows = new TbPageRows<>(wrapper.getFinalConditional(), wrapper.getPageIndex(), wrapper.getPageSize());
        int count = selectCount(wrapper);
        pageRows.setTotalSize(count);
        selectSql = String.format("%s \nLIMIT %s, %s", selectSql, (wrapper.getPageIndex() - 1) * wrapper.getPageIndex(), wrapper.getPageSize());
        List<T> list = selectListBySql(wrapper.getEntityClass(), selectSql, thisQuery.getParams().toArray());
        pageRows.setRows(list);
        return pageRows;
    }

    @Override
    public <T> int selectCount(ConditionWrapper<T> wrapper) throws Exception {
        Asserts.notNull(wrapper);
        String joinTableSql = getJoinTableSql(wrapper);
        ThisQuery thisQuery = new ThisQuery(wrapper, joinTableSql);
        String selectSql = changeSql(thisQuery.getSelectSql(), thisQuery.getParams());
        String selectCountSql = String.format("SELECT COUNT(1) FROM (%s) TB_COUNT ", selectSql);
        return dbConnAction(conn -> ConvertUtils.cInt(DbUtils.selectValueSql(conn, selectCountSql, thisQuery.getParams().toArray())));
    }


}
