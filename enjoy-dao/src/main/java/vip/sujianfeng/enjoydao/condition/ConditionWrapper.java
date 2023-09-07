package vip.sujianfeng.enjoydao.condition;


import vip.sujianfeng.enjoydao.condition.consts.Constants;
import vip.sujianfeng.enjoydao.condition.support.ColumnParseHandler;
import vip.sujianfeng.enjoydao.condition.support.TableSupport;
import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.condition.utils.JudgeUtil;
import vip.sujianfeng.enjoydao.condition.utils.StrUtils;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.io.Serializable;
import java.util.*;

/**
 * @author Xiao-Bai
 * @date 2022/3/5 23:07
 * @desc:查询条件储存
 */
@SuppressWarnings("unchecked")
public abstract class ConditionWrapper<T> implements Serializable {


    /**
     * 查询的列名
     */
    private String[] selectColumns;

    /**
     * 表数据支持
     */
    private TableSupport tableSupport;

    /**
     * 实体Class对象
     */
    private Class<T> entityClass;

    /**
     * 当前条件构造是否只进行单表查询
     */
    private Boolean primaryTable = false;

    /**
     * 最终的sql条件语句
     */
    private StringBuilder finalConditional;

    /**
     * 上一次的拼接条件
     */
    private String lastCondition;

    /**
     * sql中的所有参数值
     */
    private List<Object> paramValues;

    /**
     * 排序
     */
    private StringJoiner orderBy;
    /**
     * 分组
     */
    private StringJoiner groupBy;
    /**
     * 筛选
     */
    private StringBuilder having;
    private List<Object> havingParams;

    /**
     * 分页
     */
    private Integer pageIndex;
    private Integer pageSize;
    private Boolean hasPageParams = false;

    /**
     * 函数式接口序列化解析对象
     */
    private ColumnParseHandler<T> columnParseHandler;

    /**
     * 自定义sql条件，只会在条件构造器的条件拼接完成之后才会拼接该条件
     */
    private StringBuilder customizeSql;


    public TableSupport getTableSupport() {
        return tableSupport;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public ConditionWrapper<T> setEntityClass(Class<T> entityClass) {
        Asserts.notNull(entityClass, "映射实体Class对象缺失");
        this.entityClass = entityClass;
        return this;
    }

    public List<Object> getParamValues() {
        return paramValues;
    }

    protected void addParams(Object param) {
        if (param instanceof Collection) {
            this.paramValues.addAll((Collection<?>) param);
            return;
        }
        this.paramValues.add(param);
    }

    protected void addParams(Object... params) {
        paramValues.addAll(Arrays.asList(params));
    }

    protected void addParams(List<Object> params) {
        this.paramValues.addAll(params);
    }

    public String getFinalConditional() {
        if (this.customizeSql != null) {
            return finalConditional.toString() + this.customizeSql;
        }
        return finalConditional.toString();
    }

    protected StringBuilder getFinalCondition() {
        return finalConditional;
    }

    protected void addCondition(String condition) {
        this.finalConditional.append(condition);
    }

    protected String getLastCondition() {
        return lastCondition;
    }

    protected void setLastCondition(String lastCondition) {
        this.lastCondition = lastCondition;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    protected void setSelectColumns(String[] selectColumns) {
        this.selectColumns = selectColumns;
    }

    public StringJoiner getOrderBy() {
        return orderBy;
    }

    public StringJoiner getGroupBy() {
        return groupBy;
    }

    public StringBuilder getHaving() {
        return having;
    }

    public List<Object> getHavingParams() {
        return havingParams;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getCustomizeSql() {
        if (this.customizeSql == null) {
            return Constants.EMPTY;
        }
        return customizeSql.toString();
    }

    protected void addCustomizeSql(String customizeSql) {
        if (this.customizeSql == null) {
            this.customizeSql = new StringBuilder();
        }
        this.customizeSql.append(Constants.WHITESPACE).append(customizeSql);
    }


    /**
     * 参数注入后的sql条件
     * <p>
     * 原sql(a.name = ?, params = 20)
     * </p>
     * 返回sql(a.name = 20)
     */
    public String injectParamsConditional() {
        StringBuilder handleSqlBuilder = new StringBuilder(this.finalConditional);

        if (JudgeUtil.isNotEmpty(this.customizeSql)) {
            handleSqlBuilder.append(this.customizeSql);
        }
        if (JudgeUtil.isNotEmpty(this.groupBy)) {
            handleSqlBuilder.append(Constants.GROUP_BY).append(this.groupBy);
        }
        if (JudgeUtil.isNotEmpty(this.having)) {
            handleSqlBuilder.append(Constants.HAVING).append(this.having);
            this.paramValues.addAll(this.havingParams);
        }
        if (JudgeUtil.isNotEmpty(this.orderBy)) {
            handleSqlBuilder.append(Constants.ORDER_BY).append(this.orderBy);
        }
        return handleExecuteSql(handleSqlBuilder.toString(), this.paramValues.toArray());
    }

    /**
     * 可执行的sql条件
     */
    public static String handleExecuteSql(String sql, Object[] params) {
        int symbolCount = StrUtils.countStr(sql, Constants.QUEST);
        int index = 0;
        while (index < symbolCount) {
            Object param = params[index];
            if(Objects.isNull(param)) {
                param = "null";
            }else if (param instanceof CharSequence) {
                param = String.format("'%s'", param);
            }
            sql = sql.replaceFirst("\\?", param.toString());
            index ++;
        }
        return sql;
    }

    public boolean hasPageParams() {
        return hasPageParams;
    }

    protected void wrapperInitialize(Class<T> entityClass, TableSupport tableSupport) {
        this.entityClass = entityClass;
        if (tableSupport == null) {
            this.tableSupport = new TableSimpleSupport<>(this.entityClass);
        }else {
            this.tableSupport = tableSupport;
        }
        this.columnParseHandler = new DefaultColumnParseHandler<>(entityClass, this.tableSupport);
        this.dataStructureInit();
    }

    protected void wrapperInitialize(Class<T> entityClass) {
        wrapperInitialize(entityClass, null);
    }

    /**
     * 结构初始化
     */
    protected void dataStructureInit() {
        this.finalConditional = new StringBuilder();
        this.lastCondition = Constants.EMPTY;
        this.paramValues = new ArrayList<>();
        this.orderBy = new StringJoiner(Constants.SEPARATOR_COMMA_2);
        this.groupBy = new StringJoiner(Constants.SEPARATOR_COMMA_2);
        this.having = new StringBuilder();
        this.havingParams = new ArrayList<>();
    }

    protected void setPageParams(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.hasPageParams = true;
    }

    /**
     * 解析函数后，得到java属性字段对应的表字段名称
     */
    @SafeVarargs
    protected final String[] parseColumn(SFunction<T, ?>... func) {
        return columnParseHandler.parseToColumns(Arrays.asList(func)).toArray(new String[0]);
    }

    /**
     * 解析函数后，得到java属性字段对应的表字段名称
     */
    protected String parseColumn(SFunction<T, ?> func) {
        return columnParseHandler.parseToColumn(func);
    }

    public abstract T getThisEntity();

    public Boolean getPrimaryTable() {
        return primaryTable;
    }

    protected void setPrimaryTable() {
        this.primaryTable = true;
    }

    protected void setPrimaryTable(boolean primaryTable) {
        this.primaryTable = primaryTable;
    }

    protected ColumnParseHandler<T> getColumnParseHandler() {
        return columnParseHandler;
    }

    protected void setColumnParseHandler(ColumnParseHandler<T> columnParseHandler) {
        this.columnParseHandler = columnParseHandler;
    }

    public void setTableSupport(TableSupport tableSupport) {
        this.tableSupport = tableSupport;
    }

    /**
     * 合并查询列(数组合并)
     */
    protected void mergeSelect(String[] selectColumns) {
        if(Objects.isNull(selectColumns)) {
            return;
        }
        if(Objects.isNull(getSelectColumns())) {
            setSelectColumns(selectColumns);
            return;
        }
        int thisLen = getSelectColumns().length;
        int addLen = selectColumns.length;
        String[] newSelectColumns = new String[thisLen + addLen];
        for (int i = 0; i < newSelectColumns.length; i++) {
            if(i <= thisLen - 1) {
                newSelectColumns[i] = getSelectColumns()[i];
            }else {
                newSelectColumns[i] = selectColumns[i - thisLen];
            }
        }
        setSelectColumns(newSelectColumns);
    }


}