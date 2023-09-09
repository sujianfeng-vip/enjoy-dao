package vip.sujianfeng.enjoydao.handler.intf;

import vip.sujianfeng.enjoydao.handler.model.ManyIdParam;
import vip.sujianfeng.enjoydao.handler.model.PageParam;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.TbPageRows;
import vip.sujianfeng.utils.define.CallResult;

import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 **/
public interface MasterEvent<T extends AbstractOpModel, P extends PageParam> {


    default void beforeAdd(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    default void afterAdd(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    default void beforeUpdate(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    default void afterUpdate(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    default void afterQueryOne(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    default void beforeQueryPage(CallResult<?> op, JdbcTbDao jdbcDao, P param) {

    }

    default void afterQueryPage(CallResult<?> op, JdbcTbDao jdbcDao, P param, TbPageRows<T> pageRows) {

    }

    default void beforeDelete(CallResult<?> op, JdbcTbDao jdbcDao, ManyIdParam manyIdParam, List<T> rows) {

    }


    default void afterDelete(CallResult<?> op, JdbcTbDao jdbcDao, ManyIdParam manyIdParam, List<T> rows) {

    }
}
