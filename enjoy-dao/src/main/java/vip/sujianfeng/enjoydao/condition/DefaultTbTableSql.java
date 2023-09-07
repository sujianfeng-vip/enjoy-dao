package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineTable;
import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiao-Bai
 * @date 2022/12/6 0006 17:51
 */
public class DefaultTbTableSql extends TbTableSql {

    private final Map<String, String> fieldMap = new HashMap<>();
    private final Map<String, String> columnMap = new HashMap<>();

    public DefaultTbTableSql(SqlAdapter sqlAdapter, String tableName, TbDefineTable defineTable, TbDefineField keyField) {
        super(sqlAdapter, tableName, defineTable, keyField);
    }

    public DefaultTbTableSql(SqlAdapter sqlAdapter, AbstractOpModel model) {
        super(sqlAdapter, model);
    }

    public DefaultTbTableSql(SqlAdapter sqlAdapter, Class<?> t) {
        super(sqlAdapter, t);
        initMap();
    }

    public DefaultTbTableSql(SqlAdapter sqlAdapter, Class<?> t, AbstractOpModel model) {
        super(sqlAdapter, t, model);
        initMap();
    }

    private void initMap() {
        for (TbDefineField defineField : getFieldList()) {
            String column = String.format("%s.%s", getTableAlias(), defineField.getTableField());
            this.columnMap.put(column, defineField.getField());
            this.fieldMap.put(defineField.getField(),column);
        }
        for (TbDefineRelationField relationField : getRlsFieldList()) {
            String column = relationField.getTableField();
            if (!column.contains(".")) {
                column = String.format("%s.%s", relationField.getJoinTableAlias(), relationField.getTableField());
            }
            this.columnMap.put(column, relationField.getField());
            this.fieldMap.put(relationField.getField(),column);
        }
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }
}
