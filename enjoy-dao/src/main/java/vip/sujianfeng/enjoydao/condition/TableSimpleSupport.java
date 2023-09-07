package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.MySqlAdapter;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.ReflectUtil;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;
import vip.sujianfeng.utils.comm.StringBuilderEx;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao-Bai
 * @date 2022/12/6 0006 17:21
 */
public class TableSimpleSupport<T> implements TableSupport {

    private final DefaultTbTableSql tbTableSql;

    @Override
    public String table() {
        return tbTableSql.getTableName();
    }

    @Override
    public String alias() {
        return tbTableSql.getTableAlias();
    }

    protected String mergeTableName(String tableName) {
        if (tableName.contains(".")) {
            return tableName;
        }
        return String.format("`%s`.`%s`", tbTableSql, tableName);
    }

    @Override
    public Map<String, String> fieldMap() {
        return tbTableSql.getFieldMap();
    }

    @Override
    public Map<String, String> columnMap() {
        return tbTableSql.getColumnMap();
    }

    @Override
    public List<Field> fields() {
        return ReflectUtil.loadFields(tbTableSql.getT());
    }

    @Override
    public TbTableSql getTbTableSql() {
        return this.tbTableSql;
    }

    public TableSimpleSupport(Class<T> cls) {
        SqlAdapter sqlAdapter = new MySqlAdapter();
        this.tbTableSql = new DefaultTbTableSql(sqlAdapter, cls);
    }
}
