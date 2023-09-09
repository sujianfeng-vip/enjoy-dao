package vip.sujianfeng.enjoydao.generate;

import vip.sujianfeng.enjoydao.MySqlEnjoyDao;
import vip.sujianfeng.enjoydao.fxui.formMain.FormEnjoyModelController;
import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.modelBuilder.BuildModelPoUtils;
import vip.sujianfeng.enjoydao.generate.modelBuilder.BuildModelVoUtils;
import vip.sujianfeng.enjoydao.generate.models.MySqlTable;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.comm.HumpNameUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.List;

public class GenerateModelBuilder extends BaseCodeBuilder {


    private String poPath;
    private String voPath;
    private String modelBase;
    private String ignoreFields;

    public GenerateModelBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, String modelBase, String poPath, String voPath, String ignoreFields) {
        super(jdbcDao, logEvent);
        this.poPath = poPath;
        this.modelBase = modelBase;
        this.voPath = voPath;
        this.ignoreFields = ignoreFields;
    }

    public void build(ConfigParam configParam) throws Exception {
        StringBuilderEx tableWhere = new StringBuilderEx();
        if (StringUtilsEx.isNotEmpty(configParam.getModelBuilder().getTableNames())) {
            List<String> tableList = StringUtilsEx.splitString(configParam.getModelBuilder().getTableNames(), ",");
            if (tableList.size() > 0) {
                tableWhere.append(" and t.table_name in (''");
                for (String tableName : tableList) {
                    tableWhere.appendFormater(",'%s'", tableName);
                }
                tableWhere.append(" )");
            }
        }
        if (StringUtilsEx.isNotEmpty(configParam.getModelBuilder().getIgnoreTableNames())) {
            List<String> tableList = StringUtilsEx.splitString(configParam.getModelBuilder().getIgnoreTableNames(), ",");
            if (tableList.size() > 0) {
                tableWhere.append(" and t.table_name not in (''");
                for (String tableName : tableList) {
                    tableWhere.appendFormater(",'%s'", tableName);
                }
                tableWhere.append(" )");
            }
        }
        List<MySqlTable> tables = queryTables(getJdbcDao(), tableWhere.toString());

        FormEnjoyModelController.ME.addLog("数据库[{}]表总数:{}", getJdbcDao().getDbName(), tables.size());
        for (MySqlTable it : tables) {
            String className = StringUtilsEx.firstWordUpCase(HumpNameUtils.underLineToHump(it.getTableNameForClass()));
            String basePath = getModelCodeFilePath(this.poPath, it.getTableName()) + "/" + className + "PO.kt";
            String basePackage = getModelPackage(this.poPath, it.getTableName());
            String voPath = getModelCodeFilePath(this.voPath, it.getTableName()) + "/" + className + "VO.kt";
            String voPackage = getModelPackage(this.voPath, it.getTableName());
            //生成基础模型
            BuildModelPoUtils.buildModelPO(getJdbcDao(), modelBase, ignoreFields, basePath, basePackage, it, className);
            //生成视图模型
            BuildModelVoUtils.buildModelVO(voPath, basePackage, voPackage, it, className);
        }
    }

    public String getPoPath() {
        return poPath;
    }

    public void setPoPath(String poPath) {
        this.poPath = poPath;
    }

    public String getModelBase() {
        return modelBase;
    }

    public void setModelBase(String modelBase) {
        this.modelBase = modelBase;
    }

    public String getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(String ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    public String getVoPath() {
        return voPath;
    }

    public void setVoPath(String voPath) {
        this.voPath = voPath;
    }
}
