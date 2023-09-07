package vip.sujianfeng.enjoydao;

import com.alibaba.fastjson.JSON;
import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/6/21
 * @Description
 **/
public class DemoTableTest {

    public static void main(String[] args) {
        try {
            DemoTable demoTable = new DemoTable();
            demoTable.setField1("111");
            MySqlEnjoyDao mySqlEnjoyDao = new MySqlEnjoyDao(null, "");
            TbTableSql tbTableSql = new TbTableSql(mySqlEnjoyDao.getSqlAdapter(), demoTable);
            List<Object> params = new ArrayList<>();
            String sql = mySqlEnjoyDao.insertSql(tbTableSql, params);
            System.out.println("sql => " + sql);
            System.out.println("params => " + JSON.toJSONString(params));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
