package vip.sujianfeng.enjoydao.db;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @Author SuJianFeng
 * @Date 2019/5/14 10:55
 **/
public class DbUtils {

    private static Logger logger = LoggerFactory.getLogger(DbUtils.class);

    public static Connection buildConn(DbConfig dbConfig) throws Exception {
        return buildConn(dbConfig.getDriverName(), dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
    }

    public static Connection buildConn(String driverName, String url, String user, String password) throws Exception {
        Class.forName(driverName);
        return DriverManager.getConnection(url, user, password);
    }
    public static List<Map<String, Object>> queryRows(Connection conn, IDataRow dataRow, String sql, Object... params) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<>();
        PreparedStatement pst = conn.prepareStatement(sql);
        if (params != null){
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        ResultSet rSet;
        try{
            rSet = pst.executeQuery();
        } catch (Exception e){
            logger.error("\n=============Sql Error==========\nerror->{}\nsql->{}\nparams->{}\n===============================\n",
                    e.toString(), sql, JSON.toJSONString(params));
            throw e;
        }
        ResultSetMetaData metaData = rSet.getMetaData();
        // 遍历结果
        while (rSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++){
                String field = metaData.getColumnLabel(i);
                Object value = rSet.getObject(field);
                if (dataRow != null){
                    value = dataRow.changeValue(field, value);
                }
                row.put(field, value);
            }
            rows.add(row);
        }
        return rows;
    }

    public static List<DbColumn> queryDbColumnDefinesBySql(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        if (params != null){
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        ResultSet rSet;
        try{
            rSet = pst.executeQuery();
        } catch (Exception e){
            logger.error("\n=============Sql Error==========\nerror->{}\nsql->{}\nparams->{}\n===============================\n",
                    e, sql, JSON.toJSONString(params));
            throw e;
        }
        ResultSetMetaData metaData = rSet.getMetaData();
        List<DbColumn> result = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++){
            DbColumn dbColumn = new DbColumn();
            dbColumn.setColumnName(metaData.getColumnName(i));
            dbColumn.setColumnLabel(metaData.getColumnLabel(i));
            dbColumn.setColumnType(metaData.getColumnType(i));
            dbColumn.setColumnTypeName(metaData.getColumnTypeName(i));
            dbColumn.setColumnClassName(metaData.getColumnClassName(i));
            dbColumn.setAutoIncrement(metaData.isAutoIncrement(i));
            dbColumn.setCaseSensitive(metaData.isCaseSensitive(i));
            dbColumn.setDefinitelyWritable(metaData.isDefinitelyWritable(i));
            dbColumn.setColumnDisplaySize(metaData.getColumnDisplaySize(i));
            dbColumn.setCurrency(metaData.isCurrency(i));
            dbColumn.setIsNullable(metaData.isNullable(i));
            dbColumn.setPrecision(metaData.getPrecision(i));
            dbColumn.setReadOnly(metaData.isReadOnly(i));
            dbColumn.setScale(metaData.getScale(i));
            dbColumn.setCatalogName(metaData.getCatalogName(i));
            dbColumn.setDefinitelyWritable(metaData.isDefinitelyWritable(i));
            dbColumn.setSchemaName(metaData.getSchemaName(i));
            dbColumn.setSearchable(metaData.isSearchable(i));
            dbColumn.setTableName(metaData.getTableName(i));
            dbColumn.setWritable(metaData.isWritable(i));
            result.add(dbColumn);
        }
        return result;
    }

    public static <T> List<T> queryRows(Connection conn, Class<T> t, IDataRow dataRow, String sql, Object... params) throws Exception {
        List<Map<String, Object>> rows = queryRows(conn, dataRow, sql, params);
        String json = JSON.toJSONString(rows);
        return JSON.parseArray(json, t);
    }
    public static int executeSql(Connection conn, String sql, Object... params) throws Exception {
        List<Object> list = new ArrayList<>();
        list.addAll(Arrays.asList(params));
        try{
            return executeSql(conn, sql, list);
        }catch (Exception e){
            logger.error("\n=============Sql Error==========\nerror->{}\nsql->{}\nparams->{}\n===============================\n",
                    e.toString(), sql, JSON.toJSONString(params));
            throw e;
        }
    }

    public static int executeSql(Connection conn, String sql, List<Object> params) throws Exception {
        PreparedStatement pst = conn.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            pst.setObject(i + 1, params.get(i));
        }
        try{
            pst.execute();
            return pst.getUpdateCount();
        }catch (Exception e){
            logger.error("\n=============Sql Error==========\nerror->{}\nsql->{}\nparams->{}\n===============================\n",
                    e.toString(), sql, JSON.toJSONString(params));
            throw e;
        }
    }

    public static long insertSql(Connection conn, String sql, Object... params) throws Exception {
        return insertSql(conn, false, sql, params);
    }

    public static long insertSql(Connection conn, boolean generatedNewIntKey, String sql, Object... params) throws Exception {
        try{
            if (generatedNewIntKey){
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
                int result = pst.executeUpdate();
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) return generatedKeys.getLong(1);
                return result;
            }else{
                PreparedStatement pst = conn.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
                return pst.executeUpdate();
            }
        }catch (Exception e){
            logger.error("\n=============Sql Error==========\nerror->{}\nsql->{}\nparams->{}\n===============================\n",
                    e.toString(), sql, JSON.toJSONString(params));
            throw e;
        }
    }

    public static Object selectValueSql(Connection conn, String sql, Object... params) throws Exception {
        List<Map> rows = queryRows(conn, Map.class, sql, params);
        if (rows == null || rows.size() == 0 || rows.get(0).values().size() == 0){
            return null;
        }
        return rows.get(0).values().toArray()[0];
    }

    public static <T> List<T> queryRows(Connection conn, Class<T> t, String sql, Object... params) throws Exception {
        return queryRows(conn, t,null, sql, params);
    }

    public interface IDataRow{
        Object changeValue(String field, Object value);
    }

    /**
     * 开始事务
     * @param conn
     */
    public static void beginTransaction(Connection conn){
        if(conn != null){
            try {
                if(conn.getAutoCommit()){
                    conn.setAutoCommit(false);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
        }
    }

    /**
     * 提交事务
     * @param conn
     */
    public static void commitTransaction(Connection conn){
        if(conn != null){
            try {
                if(!conn.getAutoCommit()){
                    conn.commit();
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
        }
    }

    /**
     * 回滚事务
     * @param conn
     */
    public static void rollBackTransaction(Connection conn){
        if(conn != null){
            try {
                if(!conn.getAutoCommit()){
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
        }
    }

    public static <T> T queryObject(Connection conn, Class<T> type, String sql, Object... params) throws Exception {
        List<T> rows = queryObjects(conn, type, sql, params);
        if (rows.size() > 0 && rows.get(0) != null) return rows.get(0);
        return null;
    }

    public static <T> List<T> queryObjects(Connection conn, Class<T> t, String sql, Object... params) throws Exception {
        return queryRows(conn, t, sql, params);
    }

}
