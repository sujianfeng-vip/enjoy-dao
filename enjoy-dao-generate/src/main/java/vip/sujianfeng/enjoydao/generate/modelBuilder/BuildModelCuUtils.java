package vip.sujianfeng.enjoydao.generate.modelBuilder;

import vip.sujianfeng.enjoydao.generate.models.MySqlTableColumn;
import vip.sujianfeng.utils.comm.StringBuilderEx;

import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class BuildModelCuUtils {

    public static void buildCu(StringBuilderEx code, String className, List<MySqlTableColumn> columns) {
        code.appendRow("")
                .appendRow("/**")
                .appendRow(" * @Author GenerateModelBuilder")
                .appendRow(" * @Description Sql条件生成器")
                .appendRow(" **/")
                .appendFR("class %sCU: SqlConditionBuilder<%sCU>() {", className, className);
        for (MySqlTableColumn column : columns) {
            code.appendFR("    var %s = SqlConditionField<%sCU>(this, \"a.%s\")", column.getColumnName().toUpperCase(), className, column.getColumnName());
        }
        code.appendRow("}");
    }
}
