package vip.sujianfeng.enjoydao.handler;

import vip.sujianfeng.enjoydao.MySqlAdapter;
import vip.sujianfeng.enjoydao.handler.intf.MasterDoAction;
import vip.sujianfeng.enjoydao.handler.intf.MasterEvent;
import vip.sujianfeng.enjoydao.handler.model.BatchUpdateFieldParam;
import vip.sujianfeng.enjoydao.handler.model.ManyIdParam;
import vip.sujianfeng.enjoydao.handler.model.OneIdParam;
import vip.sujianfeng.enjoydao.handler.model.PageParam;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.TbPageRows;
import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.utils.define.CallResult;

import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 **/
public class MasterDataHandler<T extends AbstractOpModel, P extends PageParam> {

    public CallResult<T> save(JdbcTbDao jdbcDao, T item) {
        if (StringUtilsEx.isNotEmpty(item.getId())) {
            return update(jdbcDao, item);
        }
        return add(jdbcDao, item);
    }

    public CallResult<T> add(JdbcTbDao jdbcDao, T item) {
        return proc(jdbcDao, it -> {
            masterEvent.beforeAdd(it, jdbcDao, item);
            if (!it.isSuccess()) return;
            jdbcDao.insert(item);
            masterEvent.afterAdd(it, jdbcDao, item);
            it.setData(item);
        });
    }

    public CallResult<T> update(JdbcTbDao jdbcDao, T item) {
        return proc(jdbcDao, it -> {
            masterEvent.beforeUpdate(it, jdbcDao, item);
            if (!it.isSuccess()) return;
            jdbcDao.update(item);
            masterEvent.afterUpdate(it, jdbcDao, item);
            T data = jdbcDao.selectOneByUuId(modelClass, item.getId());
            it.setData(data);
        });
    }

    public CallResult<Integer> delete(JdbcTbDao jdbcDao, ManyIdParam param) {
        return proc(jdbcDao, it -> {
            StringBuilderEx strIds = new StringBuilderEx("''");
            param.getIds().forEach(id->{
                strIds.append(",").append("'").append(id).append("'");
            });
            List<T> rows = jdbcDao.selectList(modelClass, String.format(" and a.id in (%s)", strIds), 1, strIds.length());
            masterEvent.beforeDelete(it, jdbcDao, param, rows);
            if (!it.isSuccess()) return;
            for (T row : rows) {
                jdbcDao.delete(row);
            }
            masterEvent.afterDelete(it, jdbcDao, param, rows);
        });
    }

    public CallResult<T> queryOne(JdbcTbDao jdbcDao, OneIdParam oneIdParam) {
        return proc(jdbcDao, it -> {
            T item = jdbcDao.selectOneByUuId(modelClass, oneIdParam.getId());
            if (item == null) {
                it.error("This ID does not exist:%s", oneIdParam.getId());
                return;
            }
            it.setData(item);
            masterEvent.afterQueryOne(it, jdbcDao, item);
        });
    }

    public CallResult<TbPageRows<T>> queryPage(JdbcTbDao jdbcDao, P param) {
        return proc(jdbcDao, it -> {
            masterEvent.beforeQueryPage(it, jdbcDao, param);
            if (!it.isSuccess()) return;
            TbPageRows<T> data = jdbcDao.selectPageRows(modelClass, param.sqlConditionBuilder(jdbcDao));
            it.setData(data);
            masterEvent.afterQueryPage(it, jdbcDao, param, data);
        });
    }

    public CallResult<Integer> batchUpdateField(JdbcTbDao jdbcTbDao, BatchUpdateFieldParam updateParam) {
        return proc(jdbcTbDao, it-> {
            List<Object> params = new ArrayList<>();
            params.add(updateParam.getFieldValue());
            TbTableSql tbTableSql = new TbTableSql(new MySqlAdapter(), this.modelClass);
            StringBuilderEx idsStr = new StringBuilderEx("''");
            for (String id : updateParam.getIds()) {
                idsStr.append(",?");
                params.add(id);
            }
            Integer data = jdbcTbDao.executeSql(String.format("update %s set update_time = %s, %s = ? where id in (%s)", tbTableSql.getTableName(),  System.currentTimeMillis() / 1000, updateParam.getFieldName(), idsStr), params.toArray());
            it.setData(data);
        });
    }

    private Class<T> modelClass;
    private MasterEvent<T, P> masterEvent;

    public MasterDataHandler(Class<T> modelClass, MasterEvent<T, P> masterEvent) {
        this.modelClass = modelClass;
        this.masterEvent = masterEvent;
    }

    private <K> CallResult<K> proc(JdbcTbDao jdbcDao, MasterDoAction<K> action) {
        return CallResult.opCall(it-> jdbcDao.doTrans(()-> {
            action.proc(it);
            return it.isSuccess();
        }));
    }

    public MasterEvent<T, P> getMasterEvent() {
        return masterEvent;
    }

    public void setMasterEvent(MasterEvent<T, P> masterEvent) {
        this.masterEvent = masterEvent;
    }

    public Class<T> getModelClass() {
        return modelClass;
    }
}
