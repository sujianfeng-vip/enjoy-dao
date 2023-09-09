package vip.sujianfeng.enjoydao;

import com.alibaba.fastjson.JSONObject;
import vip.sujianfeng.enjoydao.condition.ConditionWrapper;
import vip.sujianfeng.enjoydao.condition.CustomCheckException;
import vip.sujianfeng.enjoydao.condition.ThisQuery;
import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.db.DbUtils;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.GuidUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2019/8/29 15:17
 **/
public abstract class AbstractMySqlEnjoyDao extends JdbcTbDao {

    public AbstractMySqlEnjoyDao(DataSource dataSource, String dbName) {
        super(new MySqlAdapter(), dataSource, dbName);
    }

    public abstract Object currTime();

    @Override
    public String insertSql(TbTableSql table, List<Object> params) {
        Object currTime = currTime();
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFormater(" insert into %s(`%s`,", mergeTableName(table.getTableName()), table.getKeyField().getTableField());
        Object id = table.getId();
        if (StringUtilsEx.isEmpty(id)) {
            id = GuidUtils.buildGuid();
        }
        table.setId(String.valueOf(id));
        params.add(id);
        for (TbDefineField tbDefineField : table.getAllUpdateFields()) {
            if (tbDefineField.getValue() == null) {
                continue;
            }
            sql.appendFormater("`%s`,", tbDefineField.getTableField());
        }
        sql.appendFR(" create_time, update_time, state)");
        sql.append(" values(?,");
        for (TbDefineField tbDefineField : table.getAllUpdateFields()) {
            if (tbDefineField.getValue() == null) {
                continue;
            }
            sql.append("?,");
            params.add(tbDefineField.getValue());
        }
        sql.appendFormater("?, ?, 0)");
        params.add(currTime);
        params.add(currTime);
        return sql.toString();
    }

    @Override
    public List<TbDefineField> queryDefineFields(String tableName) throws Exception {
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR("select column_key columnKey, column_name fieldName, data_type fieldType, character_maximum_length length, column_comment label");
        sql.appendFR("from information_schema.`COLUMNS`");
        sql.appendFR("where table_schema = ? and table_name = ?");
        List<JSONObject> rows = selectListBySql(JSONObject.class, sql.toString(), this.getDbName(), tableName);
        List<TbDefineField> result = new ArrayList<>();
        for (JSONObject row : rows) {
            TbDefineField tbDefineField = new TbDefineField();
            tbDefineField.setKeyField(row.getString("columnKey").equalsIgnoreCase("PRI"));
            tbDefineField.setTableField(row.getString("fieldName"));
            tbDefineField.setField(row.getString("fieldName"));
            tbDefineField.setFieldType(TbDefineFieldType.valueOfStr(row.getString("fieldType")));
            tbDefineField.setLabel(row.getString("label"));
            result.add(tbDefineField);
        }
        return result;
    }

    @Override
    public int addField(String tableName, TbDefineField tbDefineField) throws Exception {
        String sql = String.format("alter table %s add column `%s` %s",
                mergeTableName(tableName), tbDefineField.getTableField(), tbDefineField.getDefineFieldTypeSql(this.getSqlAdapter()));
        return executeSql(sql);
    }

    @Override
    public int update(TbUpdateSql updateSql) throws Exception {
        return dbConnAction(conn -> {
            List<Object> params = new ArrayList<>();
            String sql = updateSql(updateSql, params);
            return DbUtils.executeSql(conn, sql, params);
        });
    }

    @Override
    public String updateSql(TbUpdateSql updateSql, List<Object> params) {
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" update %s set", mergeTableName(updateSql.getTableName()));
        for (TbDefineField tbDefineField : updateSql.getUpdateFields()) {
            if (tbDefineField.getValue() == null) {
                continue;
            }
            sql.appendFR("`%s` = ?, ", tbDefineField.getTableField());
            params.add(tbDefineField.getValue());
        }
        Object currTime = currTime();
        sql.appendFR(" update_time = ?");
        params.add(currTime);
        sql.appendFR(" where `%s` = ?", updateSql.getKeyField().getTableField());
        params.add(updateSql.getId());
        return sql.toString();
    }

    @Override
    public String updateDeltaSql(AbstractOpModel model, List<Object> params, int[] deltas, String... fields) {
        TbUpdateSql updateSql = new TbUpdateSql(this.getSqlAdapter(), model.getClass(), model, fields);
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" update %s set", mergeTableName(updateSql.getTableName()));
        for (int i = 0; i < deltas.length; i++) {
            sql.appendFR("`%s` = `%s` + %s, ", fields[i], fields[i], deltas[i]);
        }
        Object currTime = currTime();
        sql.appendFR(" update_time = ?");
        params.add(currTime);
        sql.appendFR(" where `%s` = ?", updateSql.getKeyField().getTableField());
        params.add(model.getId());
        return sql.toString();
    }

    @Override
    public String deleteSql(Class<? extends AbstractOpModel> t, String condition) {
        TbTableSql tbTableSql = new TbTableSql(this.getSqlAdapter(), t);
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" update %s %s set %s.state = 1", mergeTableName(tbTableSql.getTableName()), tbTableSql.getTableAlias(), tbTableSql.getTableAlias());
        sql.appendFR(" where 1=1").append(condition);
        return sql.toString();
    }

    @Override
    public String recoveryDeleteSql(TbTableSql tbTableSql) {
        return String.format("update %s a set state = 0 where a.`%s` = ? ", mergeTableName(tbTableSql.getTableName()), tbTableSql.getKeyField().getTableField());
    }

    @Override
    public String deletePhysicalSql(AbstractOpModel model, String condition) {
        TbTableSql tbTableSql = TbTableSql.getTableSql(this.getSqlAdapter(), model);
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" delete from %s", mergeTableName(tbTableSql.getTableName()));
        sql.appendFR(" where 1=1 %s", condition);
        return sql.toString();
    }

    @Override
    public String getJoinTableSql(TbSelectSql tbSelectSql) {
        StringBuilderEx sql = new StringBuilderEx();
        for (TbDefineRelationField rlsField : tbSelectSql.getRlsFieldList()) {
            String joinLine = String.format("left join %s %s on %s", mergeTableName(rlsField.getJoinTable()), rlsField.getJoinTableAlias(), rlsField.getJoinCondition());
            if (!sql.toString().contains(joinLine)) {
                sql.appendRow(joinLine);
            }
        }
        return sql.toString();
    }

    @Override
    public String selectListSql(TbSelectSql tbSelectSql, String condition) {
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" select %s", tbSelectSql.getSelectSql());
        sql.appendFR(" from %s %s", mergeTableName(tbSelectSql.getTableName()), tbSelectSql.getTableAlias());
        sql.appendFR(" %s", getJoinTableSql(tbSelectSql));
        sql.appendFR(" where 1=1 %s", condition);
        if (StringUtilsEx.isNotEmpty(tbSelectSql.getOrderBy())) {
            sql.appendFR(" order by %s", tbSelectSql.getOrderBy());
        }
        sql.appendFR(" limit %s, %s", tbSelectSql.getBeginRow(), tbSelectSql.getPageSize());
        return sql.toString();
    }

    @Override
    protected String getTotalSizeSql(TbSelectSql tbSelectSql, String condition) {
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR(" select count(1) from %s %s", mergeTableName(tbSelectSql.getTableName()), tbSelectSql.getTableAlias());
        sql.appendFR(" %s", getJoinTableSql(tbSelectSql));
        sql.appendFR(" where 1=1 %s", condition);
        return sql.toString();
    }

    @Override
    protected String isExistTableSql(String tableName) {
        String dbName = getDbName();
        if (tableName.contains(".")) {
            dbName = StringUtilsEx.leftStr(tableName, ".");
            dbName = dbName.replace("`", "");
            tableName = StringUtilsEx.leftStr(tableName, ".");
            tableName = tableName.replace("`", "");
        }
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR("SELECT DISTINCT t.table_name, n.SCHEMA_NAME");
        sql.appendFR("FROM information_schema.TABLES t");
        sql.appendFR("left join information_schema.SCHEMATA n on t.TABLE_SCHEMA = n.SCHEMA_NAME");
        sql.appendFR("WHERE t.table_name = '%s' AND n.SCHEMA_NAME = '%s';", tableName, dbName);
        return sql.toString();
    }

    @Override
    protected String createTableSql(TbTableBaseSql tbTableBaseSql) {
        StringBuilderEx sql = new StringBuilderEx();
        TbDefineTable defineTable = tbTableBaseSql.getDefineTable();
        sql.appendFR("create table %s(", mergeTableName(tbTableBaseSql.getTableName()));
        sql.appendFR("  `id` %s, ", defineTable.getKeyDefine(this.getSqlAdapter()));
        for (TbDefineField tbDefineField : tbTableBaseSql.getFieldList()) {
            if ("id".equalsIgnoreCase(tbDefineField.getField())) {
                continue;
            }
            sql.appendFR("  `%s` %s COMMENT '%s',", tbDefineField.getTableField(), tbDefineField.getDefineFieldTypeSql(this.getSqlAdapter()), tbDefineField.getLabel());
        }
        sql.appendFR("  PRIMARY KEY (`%s`)", tbTableBaseSql.getKeyField().getTableField());
        sql.appendFR(")");
        return sql.toString();
    }

    @Override
    protected String dropTableSql(String tableName) {
        StringBuilderEx sql = new StringBuilderEx();
        sql.appendFR("drop table %s;", mergeTableName(tableName));
        return sql.toString();
    }

    @Override
    protected String mergeTableName(String tableName) {
        if (tableName.contains(".")) {
            return tableName;
        }
        return String.format("`%s`.`%s`", this.getDbName(), tableName);
    }
}
