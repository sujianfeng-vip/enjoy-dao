package vip.sujianfeng.enjoydao.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.db.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author SuJianFeng
 * @date 2019/12/24 14:35
 **/
public abstract class JdbcActionTbDao {
    private static Logger logger = LoggerFactory.getLogger(JdbcActionTbDao.class);
    private SqlAdapter sqlAdapter;
    /**
     * 线程隔离连接
     */
    private ThreadLocal<Connection> THREAD_LOCAL_CONN = new ThreadLocal<>();
    private DataSource dataSource;

    public JdbcActionTbDao(SqlAdapter sqlAdapter, DataSource dataSource){
        this.sqlAdapter = sqlAdapter;
        this.dataSource = dataSource;
    }

    /**
     * 获取的连接要线程隔离，避免多线程环境下互相影响
     * @return
     */
    private Connection getConn() throws SQLException {
        Connection result = THREAD_LOCAL_CONN.get();
        if (result == null || result.isClosed()){
            result = this.dataSource.getConnection();
            THREAD_LOCAL_CONN.set(result);
        }
        return result;
    }

    public <T> T dbConnAction(DbConnectionAction<T> action) throws Exception {
        Connection connection = getConn();
        try{
            if (connection == null || connection.isClosed()){
                connection = this.dataSource.getConnection();
                THREAD_LOCAL_CONN.set(connection);
            }
            return action.consumeAction(connection);
        } finally {
            if (connection != null && connection.getAutoCommit()){
                //如果是自动提交，那么关闭连接
                connection.close();
            }
        }
    }

    public void doTrans(DbAction dbAction) throws Exception {
        this.beginTrans();
        try{
            if (dbAction.doTrans()) {
                this.commitTrans();
                return;
            }
            this.rollBackTrans();
        }catch (Exception e){
            this.rollBackTrans();
            throw e;
        }
    }

    /**
     * 开始事务
     */
    public void beginTrans() {
        try {
            DbUtils.beginTransaction(getConn());
        } catch (SQLException e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 提交事务
     */
    public void commitTrans() {
        try {
            Connection connection = getConn();
            DbUtils.commitTransaction(connection);
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 回滚事务
     */
    public void rollBackTrans() {
        try {
            Connection connection = getConn();
            DbUtils.rollBackTransaction(connection);
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString(), e);
        }
    }

    public ThreadLocal<Connection> getThreadLocalConn() {
        return THREAD_LOCAL_CONN;
    }

    public void setTHREAD_LOCAL_CONN(ThreadLocal<Connection> THREAD_LOCAL_CONN) {
        this.THREAD_LOCAL_CONN = THREAD_LOCAL_CONN;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlAdapter getSqlAdapter() {
        return sqlAdapter;
    }
}
