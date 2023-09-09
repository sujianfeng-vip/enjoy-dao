package vip.sujianfeng.enjoydao.generate.modelBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.fxui.formMain.FormEnjoyModelController;
import vip.sujianfeng.enjoydao.generate.models.MySqlTable;
import vip.sujianfeng.utils.comm.DateTimeUtils;
import vip.sujianfeng.utils.comm.FileHelper;
import vip.sujianfeng.utils.comm.StringBuilderEx;

/**
 * author SuJianFeng
 * createTime 2022/9/2
 * description
 **/
public class BuildModelVoUtils {
    public static void buildModelVO(String fileName, String basePackage, String voPackage, MySqlTable table, String className) throws Exception {
        if (FileHelper.isExistFile(fileName)) {
            FormEnjoyModelController.ME.addLog("代码已经存在，忽略: {}", fileName);
            return;
        }
        StringBuilderEx code = new StringBuilderEx();
        code.append("package ").appendRow(voPackage).appendRow("");
        code.appendRow("import io.swagger.annotations.ApiModel")
                .appendRow("import io.swagger.annotations.ApiModelProperty")
                .appendRow("import vip.sujianfeng.enjoydao.annotations.*")
                .appendFR("import %s.*", basePackage)
                .appendRow("")
                .appendRow("/**")
                .appendRow(" * author GenerateModelBuilder")
                .appendRow(" * createTime " + DateTimeUtils.getDateTimeShow())
                .appendRow(" * description 读取数据库生成的实体模型代码，仅不存在时生成，可修改")
                .appendRow(" **/")
                .appendFR("@ApiModel(\"%sVO\")", table.getTableComment())
                .appendFR("@TbTableUuid(table = \"%s\")", table.getTableName())
                .appendFR("open class %sVO: %sPO() {", className, className);

        code.appendFR("}");
        FileHelper.saveTxtFile(fileName, code.toString(), "UTF-8");
        FormEnjoyModelController.ME.addLog("已生成代码: {}", fileName);
    }

    private static Logger logger = LoggerFactory.getLogger(BuildModelVoUtils.class);
}
