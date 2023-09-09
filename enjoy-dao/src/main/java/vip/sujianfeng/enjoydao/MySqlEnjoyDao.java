package vip.sujianfeng.enjoydao;

import javax.sql.DataSource;

/**
 * author SuJianFeng
 * createTime 2022/10/17
 * description
 **/
public class MySqlEnjoyDao extends AbstractMySqlEnjoyDao {

    public MySqlEnjoyDao(DataSource dataSource, String dbName) {
        super(dataSource, dbName);
    }

    @Override
    public Object currTime() {
        return System.currentTimeMillis() / 1000;
    }

}
