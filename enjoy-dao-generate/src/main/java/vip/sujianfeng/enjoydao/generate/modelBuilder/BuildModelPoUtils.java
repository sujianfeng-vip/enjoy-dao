package vip.sujianfeng.enjoydao.generate.modelBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.MySqlEnjoyDao;
import vip.sujianfeng.enjoydao.fxui.formMain.FormEnjoyModelController;
import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.models.MySqlTable;
import vip.sujianfeng.enjoydao.generate.models.MySqlTableColumn;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.utils.comm.*;

import javax.sql.rowset.JdbcRowSet;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class BuildModelPoUtils {

    public static void buildModelPO(JdbcTbDao myJdbcDao, String modelBase, String ignoreFields, String fileName, String packageName, MySqlTable table, String className) throws Exception {
        StringBuilderEx code = new StringBuilderEx();
        code.append("package ").appendRow(packageName).appendRow("");
        code.appendRow("import io.swagger.annotations.ApiModel")
                .appendRow("import io.swagger.annotations.ApiModelProperty")
                .appendRow("import vip.sujianfeng.enjoydao.annotations.*")
                .appendRow("import vip.sujianfeng.enjoydao.sqlcondition.*")
                .appendRow("import vip.sujianfeng.enjoydao.enums.*")
                .appendRow("")
                .appendRow("/**")
                .appendRow(" * @Author GenerateModelBuilder")
                //.appendRow(" * @Date " + DateTimeUtils.getDateTimeShow())
                .appendRow(" * @Description 读取数据库生成的实体模型代码，不要手动修改，重新生成后会覆盖")
                .appendRow(" **/")
                .appendFR("@ApiModel(\"%sPO\")", table.getTableComment())
                .appendFR("@TbTableUuid(table = \"%s\")", table.getTableName())
                .appendFR("open class %sPO: %s() {", className, modelBase);

        List<MySqlTableColumn> columns = BaseCodeBuilder.getTableColumns(myJdbcDao, table.getTableName());
        for (MySqlTableColumn column : columns) {
            if (StringUtilsEx.isNotEmpty(ignoreFields) && ignoreFields.contains(HumpNameUtils.underLineToHump(column.getColumnName()) + ",")) {
                continue;
            }
            code.appendFR("    @ApiModelProperty(\"%s\")", column.getColumnComment());
            String columnType = StringUtilsEx.leftStr(column.getColumnType(), "(");
            String length = StringUtilsEx.leftStr(StringUtilsEx.rightStr(column.getColumnType(), "("), ")");
            if (ConvertUtils.cInt(length) == 0) {
                length = "50";
            }
            if (ConvertUtils.oneOfString(columnType.toLowerCase(), "tinyint", "smallint", "mediumint", "int", "integer", "bit")) {
                code.appendFR("    @TbFieldInt(tableField = \"%s\", label = \"%s\")", column.getColumnName(), column.getColumnComment());
                code.appendFR("    var %s: Int? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            } else if (ConvertUtils.oneOfString(columnType.toLowerCase(), "bigint")) {
                code.appendFR("    @TbFieldLong(tableField = \"%s\", label = \"%s\")", column.getColumnName(), column.getColumnComment());
                code.appendFR("    var %s: Long? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            } else if (ConvertUtils.oneOfString(columnType.toLowerCase(), "text")) {
                code.appendFR("    @TbField(fieldType = TbDefineFieldType.ftText, tableField = \"%s\", label = \"%s\")", column.getColumnName(), column.getColumnComment());
                code.appendFR("    var %s: String? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            } else if (ConvertUtils.oneOfString(columnType.toLowerCase(), "text", "tinytext")) {
                code.appendFR("    @TbField(fieldType = TbDefineFieldType.ftText, tableField = \"%s\", label = \"%s\")", column.getColumnName(), column.getColumnComment());
                code.appendFR("    var %s: String? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            } else if (ConvertUtils.oneOfString(columnType.toLowerCase(), "mediumtext", "longtext")) {
                code.appendFR("    @TbField(fieldType = TbDefineFieldType.ftText, tableField = \"%s\", label = \"%s\")", column.getColumnName(), column.getColumnComment());
                code.appendFR("    var %s: String? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            } else {
                code.appendFR("    @TbFieldString(tableField = \"%s\", label = \"%s\", length = %s)", column.getColumnName(), column.getColumnComment(), length);
                code.appendFR("    var %s: String? = null", HumpNameUtils.underLineToHump(column.getColumnName()));
            }
        }
        code.appendRow("");
        code.appendRow("    companion object {");
        code.appendFR("        const val TABLE_NAME = \"%s\"", table.getTableName());
        for (MySqlTableColumn column : columns) {
            if (column.getColumnName().equalsIgnoreCase("TABLE_NAME")) {
                continue;
            }
            code.appendFR("        const val %s = \"%s\"", column.getColumnName().toUpperCase(), column.getColumnName());
        }
        code.appendRow("    }");
        code.appendFR("}");
        BuildModelCuUtils.buildCu(code, className, columns);
        FileHelper.saveTxtFile(fileName, code.toString(), "UTF-8");
        FormEnjoyModelController.ME.addLog("已生成代码: {}", fileName);
    }

    private static Logger logger = LoggerFactory.getLogger(BuildModelVoUtils.class);


}
