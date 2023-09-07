package vip.sujianfeng.enjoydao.model;

/**
 * @Author SuJianFeng
 * @Date 2022/9/23
 * @Description
 **/
public class ModelBuilderParam {
    private String modelBase;
    private String poPath;
    private String voPath;
    private String ignoreFields;
    /**
     * 指定表名（多张逗号隔开），只处理这些表
     */
    private String tableNames;
    /**
     * 排除指定表名列表
     */
    private String ignoreTableNames;

    public String getModelBase() {
        return modelBase;
    }

    public void setModelBase(String modelBase) {
        this.modelBase = modelBase;
    }

    public String getPoPath() {
        return poPath;
    }

    public void setPoPath(String poPath) {
        this.poPath = poPath;
    }

    public String getVoPath() {
        return voPath;
    }

    public void setVoPath(String voPath) {
        this.voPath = voPath;
    }

    public String getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(String ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public String getIgnoreTableNames() {
        return ignoreTableNames;
    }

    public void setIgnoreTableNames(String ignoreTableNames) {
        this.ignoreTableNames = ignoreTableNames;
    }
}
