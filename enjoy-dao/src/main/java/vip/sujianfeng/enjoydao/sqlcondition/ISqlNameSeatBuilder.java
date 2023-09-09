package vip.sujianfeng.enjoydao.sqlcondition;

import vip.sujianfeng.utils.comm.StringBuilderEx;

import java.util.Map;

/**
 * Name Placeholder Sql Expression Interface Subscription
 * author SuJianFeng
 * createTime 2022/10/26
 **/
public interface ISqlNameSeatBuilder {
    StringBuilderEx getExpression();
    Map<String, Object> getParamMap();
}
