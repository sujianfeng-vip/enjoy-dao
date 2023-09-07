package vip.sujianfeng.enjoydao.sqlcondition;

import vip.sujianfeng.utils.comm.StringBuilderEx;

import java.util.Map;

/**
 * 名称占位符Sql表达式接口订阅
 * @Author SuJianFeng
 * @Date 2022/10/26
 * @Description
 **/
public interface ISqlNameSeatBuilder {
    StringBuilderEx getExpression();
    Map<String, Object> getParamMap();
}
