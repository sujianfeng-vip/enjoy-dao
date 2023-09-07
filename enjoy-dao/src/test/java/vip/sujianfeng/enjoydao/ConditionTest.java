package vip.sujianfeng.enjoydao;

import vip.sujianfeng.datasource.DruidConfig;
import vip.sujianfeng.datasource.DruidUtils;
import vip.sujianfeng.enjoydao.condition.Conditions;
import vip.sujianfeng.enjoydao.condition.LambdaConditionWrapper;
import vip.sujianfeng.enjoydao.model.OrderInfo;
import vip.sujianfeng.enjoydao.sqlbuilder.TbPageRows;

import java.util.List;

/**
 * @author Xiao-Bai
 * @date 2022/12/7 0007 9:11
 */
public class ConditionTest {

    public static void main(String[] args) throws Exception {

        DruidConfig druidConfig = new DruidConfig();
        druidConfig.setUrl("jdbc:mysql://hougu-test.mysql.rds.aliyuncs.com:5678/qf_order?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false");
        druidConfig.setUsername("hougu_erp_dev");
        druidConfig.setPassword("hougu@123");
        druidConfig.setDatabase("qf_order");
        MySqlEnjoyDao mySqlEnjoyDao = new MySqlEnjoyDao(DruidUtils.getDruidDataSource(druidConfig), druidConfig.getDatabase());

        LambdaConditionWrapper<OrderInfo> conditionWrapper = Conditions.lambdaQuery(OrderInfo.class).eq(OrderInfo::getOrderNum, "600101");
        conditionWrapper.pageParams(1, 10);
        List<OrderInfo> orderInfos = mySqlEnjoyDao.selectList(conditionWrapper);
        System.out.println("orderInfos.size() = " + orderInfos);

    }
}
