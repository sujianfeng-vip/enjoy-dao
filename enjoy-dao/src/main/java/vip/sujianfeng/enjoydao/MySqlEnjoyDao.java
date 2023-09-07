package vip.sujianfeng.enjoydao;

import javax.sql.DataSource;

/**
 * @Author SuJianFeng
 * @Date 2022/10/17
 * @Description
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
