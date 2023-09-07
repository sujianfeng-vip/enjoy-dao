package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.DbUtil;
import vip.sujianfeng.enjoydao.condition.utils.JudgeUtil;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbTableSql;
import vip.sujianfeng.utils.comm.StringBuilderEx;

import java.util.*;

/**
 * @author Xiao-Bai
 * @date 2022/12/6 0006 16:22
 */
public class ThisQuery {

    /**
     * 查询的sql
     */
    private final StringBuilder selectSql = new StringBuilder();

    /**
     * sql参数
     */
    private final List<Object> params = new ArrayList<>();

    public String getSelectSql() {
        return selectSql.toString();
    }

    public List<Object> getParams() {
        return params;
    }

    public ThisQuery(ConditionWrapper<?> wrapper, String joinSql) {

        // 查询的字段
        StringJoiner selectColumnStr = new StringJoiner(", ");
        TableSupport tableSupport = wrapper.getTableSupport();
        TbTableSql tbTableSql = tableSupport.getTbTableSql();
        Map<String, String> fieldMap = tableSupport.fieldMap();
        boolean onlyPrimary = wrapper.getPrimaryTable();
        if (wrapper.getSelectColumns() != null) {
            for (String column : wrapper.getSelectColumns()) {
                String aColumn = column;
                if (Objects.nonNull(column) && !column.contains(Constants.POINT)
                        && !column.contains(Constants.BRACKETS_LEFT) && !column.contains(Constants.BRACKETS_RIGHT)) {
                    aColumn = tableSupport.alias() + Constants.POINT + column;
                }
                String field = tableSupport.columnMap().get(aColumn);
                selectColumnStr.add(field == null ? aColumn : String.format("%s `%s`", aColumn, field));
            }

        }else {
            for (TbDefineField defineField : tbTableSql.getFieldList()) {
                String column = fieldMap.get(defineField.getField());
                selectColumnStr.add(String.format("%s `%s`", Optional.ofNullable(column).orElse(""), defineField.getField()));
            }
        }

        // 拼接
        if (!onlyPrimary) {
            for (TbDefineRelationField relationField : tbTableSql.getRlsFieldList()) {
                String column = fieldMap.get(relationField.getField());
                selectColumnStr.add(String.format("%s `%s`", Optional.ofNullable(column).orElse(""), relationField.getField()));
            }
        }

        // 拼接表连接
        this.selectSql.append(
                String.format("SELECT %s \nFROM %s %s %s",
                        selectColumnStr, tableSupport.table(), tableSupport.alias(),
                        (onlyPrimary ? "" : "\n" + joinSql)
                )
        );


        // 拼接条件
        String conditional = wrapper.getFinalConditional();
        if (JudgeUtil.isNotEmpty(conditional)) {
            this.selectSql.append(String.format(" WHERE (%s) ", DbUtil.trimSqlCondition(conditional)));
        }
        params.addAll(wrapper.getParamValues());

        // 收集剩余条件
        this.collectParams(wrapper);
    }

    /**
     * 收集所有参数
     */
    private void collectParams(ConditionWrapper<?> wrapper) {
        // group by
        if (JudgeUtil.isNotEmpty(wrapper.getGroupBy())) {
            this.selectSql.append(Constants.GROUP_BY).append(wrapper.getGroupBy());
        }
        // having
        if (JudgeUtil.isNotEmpty(wrapper.getHaving())) {
            this.selectSql.append(Constants.HAVING).append(wrapper.getHaving());
        }
        // order by
        if (JudgeUtil.isNotEmpty(wrapper.getOrderBy())) {
            this.selectSql.append(Constants.ORDER_BY).append(wrapper.getOrderBy());
        }
        // having params
        if (!JudgeUtil.isNotEmpty(wrapper.getHavingParams())) {
            params.addAll(wrapper.getHavingParams());
        }
    }
}
