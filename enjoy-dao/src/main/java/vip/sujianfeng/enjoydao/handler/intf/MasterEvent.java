package vip.sujianfeng.enjoydao.handler.intf;

import vip.sujianfeng.enjoydao.handler.model.ManyIdParam;
import vip.sujianfeng.enjoydao.handler.model.PageParam;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.TbPageRows;
import vip.sujianfeng.utils.define.CallResult;

import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/21
 * @Description 主数据处理事件接口
 **/
public interface MasterEvent<T extends AbstractOpModel, P extends PageParam> {

    /**
     * 新增前
     * @param op
     * @param item
     */
    default void beforeAdd(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    /**
     * 新增后
     * @param op
     * @param item
     */
    default void afterAdd(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    /**
     * 修改前
     * @param op
     * @param item
     */
    default void beforeUpdate(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    /**
     * 修改后
     * @param op
     * @param item
     */
    default void afterUpdate(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    /**
     * 单个查询后
     * @param op
     * @param item
     */
    default void afterQueryOne(CallResult<?> op, JdbcTbDao jdbcDao, T item) {

    }

    /**
     * 分页查询前
     * @param op
     * @param param
     */
    default void beforeQueryPage(CallResult<?> op, JdbcTbDao jdbcDao, P param) {

    }

    /**
     * 分页查询后
     * @param op
     * @param param
     * @param pageRows
     */
    default void afterQueryPage(CallResult<?> op, JdbcTbDao jdbcDao, P param, TbPageRows<T> pageRows) {

    }

    /**
     * 删除前
     * @param op
     * @param manyIdParam
     */
    default void beforeDelete(CallResult<?> op, JdbcTbDao jdbcDao, ManyIdParam manyIdParam, List<T> rows) {

    }

    /**
     * 删除后
     * @param op
     * @param manyIdParam
     */
    default void afterDelete(CallResult<?> op, JdbcTbDao jdbcDao, ManyIdParam manyIdParam, List<T> rows) {

    }
}
