package vip.sujianfeng.enjoydao.generate.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.generate.GenerateModelBuilder;
import vip.sujianfeng.enjoydao.generate.enmus.ConstText;
import vip.sujianfeng.enjoydao.generate.models.MySqlTable;
import vip.sujianfeng.enjoydao.generate.models.MySqlTableColumn;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.utils.AppUtils;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.FileHelper;
import vip.sujianfeng.utils.comm.HumpNameUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.utils.intf.CommEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2023/6/21
 * description
 **/
public class BaseCodeBuilder {

    private JdbcTbDao jdbcDao;
    private CommEvent<String> logEvent;
    private Map<String, String> map = new HashMap<>();

    public CommEvent<String> getLogEvent() {
        return logEvent;
    }

    public BaseCodeBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent) {
        this.jdbcDao = jdbcDao;
        this.logEvent = logEvent;
        for (ConstText it : ConstText.values()) {
            this.map.put(it.getCode(), it.getTitle());
        }
    }

    public static boolean isSystemField(String fieldName) {
        return ConvertUtils.oneOfString(HumpNameUtils.underLineToHump(fieldName), "id", "createTime", "updateTime", "state", "createUserId", "updateUserId", "bizStatus");
    }


    public static List<MySqlTable> queryTables(JdbcTbDao myJdbcDao, String tableWhere) throws Exception {
        return myJdbcDao.selectListBySql(MySqlTable.class,
                "SELECT t.table_schema tableSchema, t.table_name tableName, t.TABLE_COMMENT tableComment \n" +
                        "FROM information_schema.TABLES t \n" +
                        "where t.table_schema = ? \n" +
                        tableWhere +
                        "order by t.table_name", myJdbcDao.getDbName());
    }

    public static List<MySqlTableColumn> getTableColumns(JdbcTbDao myJdbcDao, String tableWhere) throws Exception {
        return myJdbcDao.selectListBySql(MySqlTableColumn.class,
                "SELECT t.table_schema tableSchema, t.table_name tableName, t.TABLE_COMMENT tableComment , c.COLUMN_NAME columnName, \n" +
                        "       CAST(c.COLUMN_TYPE as char) columnType, cast(c.COLUMN_COMMENT as char) columnComment\n" +
                        "FROM information_schema.TABLES t\n" +
                        "inner join information_schema.SCHEMATA n on t.TABLE_SCHEMA = n.SCHEMA_NAME\n" +
                        "inner join information_schema.COLUMNS c on t.TABLE_SCHEMA = c.TABLE_SCHEMA and t.TABLE_NAME = c.TABLE_NAME\n" +
                        "where t.table_schema = ? and t.table_name = ? \n" +
                        "order by c.COLUMN_NAME ", myJdbcDao.getDbName(), tableWhere);
    }


    protected String getCode(Map<String, String> map) throws Exception {
        return "";
    }

    protected boolean coverIfExist() {
        return false;
    }

    protected String getFileName() {
        return "";
    }

    public void buildCodeFile(boolean coverIfExist) throws IOException {
        String projectRoot = AppUtils.getCurrProjectRoot();
        String fileName = projectRoot + "/" + getFileName();
        try {
            if (!coverIfExist && FileHelper.isExistFile(fileName)) {
                logEvent.call(String.format("[%s] => 已经存在忽略!", fileName));
                return;
            }
            logEvent.call(String.format("[%s] = > 开始生成代码....", fileName));
            FileHelper.saveTxtFile(fileName, getCode(map), "UTF-8");
            logEvent.call(String.format("[%s] = > 生成完成!....", fileName));
        }catch (Exception e) {
            logger.error(e.toString(), e);
            logEvent.call(String.format("[%s] = > 生成出错: %s", fileName, e));
        }

    }

    private final Logger logger = LoggerFactory.getLogger(GenerateModelBuilder.class);

    protected String tableName2className(String tableName) {
        String className = ConvertUtils.cStr(tableName).replace(".", "_");
        return StringUtilsEx.firstWordUpCase(HumpNameUtils.underLineToHump(className));
    }

    protected String tableName2variantName(String tableName) {
        String className = ConvertUtils.cStr(tableName).replace(".", "_");
        return StringUtilsEx.firstWordLowerCase(HumpNameUtils.underLineToHump(className));
    }

    protected String replaceByMap(String src, Map<String, String> params) {
        if (src.contains("{{") && src.contains("}}")){
            String left = StringUtilsEx.leftStr(src, "{{");
            String right = StringUtilsEx.rightStrEx(src, "{{");
            String key = StringUtilsEx.leftStr(right, "}}");
            String value = params.get(key);
            right = StringUtilsEx.rightStrEx(src, "}}");

            src = String.format("%s%s%s", left, value, right);
            src = this.replaceByMap(src, params);
        }
        return src;
    }

    protected String getModelCodeFilePath(String path, String tableName) {
        String result = path;
        String sub = StringUtilsEx.leftStr(tableName, "_");
        if (tableName.contains("_") && StringUtilsEx.isNotEmpty(sub)) {
            result += "/" + sub;
        }
        return result;
    }

    protected String getModelPackage(String path, String tableName) {
        String result = StringUtilsEx.rightStr(path, "kotlin/");
        result = result.replace("/", ".");
        result = result.replace("/", ".");
        String sub = StringUtilsEx.leftStr(tableName, "_");
        if (tableName.contains("_") && StringUtilsEx.isNotEmpty(sub)) {
            result += "." + sub;
        }
        return result;
    }

    protected String getCodePackage(String path, String modelClassName) {
        String result = StringUtilsEx.rightStr(path, "kotlin/");
        result = result.replace("/", ".");
        result = result.replace("/", ".");
        if (StringUtilsEx.isNotEmpty(modelClassName)) {
            result += "." + modelClassName;
        }
        return result;
    }

    protected String getCodeFilePath(String path, String modelClassName) {
        return path + "/" + modelClassName;
    }

    public JdbcTbDao getJdbcDao() {
        return jdbcDao;
    }
}
