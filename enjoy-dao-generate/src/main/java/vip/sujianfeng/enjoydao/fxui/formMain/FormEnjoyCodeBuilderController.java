package vip.sujianfeng.enjoydao.fxui.formMain;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import vip.sujianfeng.enjoydao.fxui.MainApp;
import vip.sujianfeng.enjoydao.generate.GenerateCodeBuilder;
import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.models.MySqlTable;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.fxui.annotations.FxForm;
import vip.sujianfeng.fxui.forms.base.FxBaseController;
import vip.sujianfeng.fxui.utils.DialogUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码案例
 * author SuJianFeng
 * createTime 2022/9/26
 * description
 **/
@FxForm(value = "/fxml/formMain/formEnjoyCodeBuilder.fxml", title = "前后端代码生成")
public class FormEnjoyCodeBuilderController extends FxBaseController {

    public TextArea txtDemoCodeMasterController;
    public TextField edtTableName;
    public TextField edtBackPath;
    public TextField edtFrontPath;
    public CheckBox chkCoverIfExist;

    CommEvent<String> log = txt -> {
        Platform.runLater(()->{
            txtDemoCodeMasterController.appendText("\n");
            txtDemoCodeMasterController.appendText(txt);
        });
    };

    @Override
    public void loadPageData(Object... params) {
        super.loadPageData(params);
        edtFrontPath.setText(MainApp.CONFIG.getCodeBuilder().getFrontPath());
        edtBackPath.setText(MainApp.CONFIG.getCodeBuilder().getBackPath());
    }

    public void clickBuild() throws Exception {
        clearLog();
        ArrayList<String> tableList = StringUtilsEx.splitString(edtTableName.getText(), ",");
        for (String table : tableList) {
            log.call(String.format("开始处理表[%s]....", table));
            try {
                List<MySqlTable> tables = BaseCodeBuilder.queryTables(MainApp.TB_DAO, String.format(" and t.table_name = '%s' ", table));
                if (tables.size() == 0 ){
                    DialogUtils.info("不存在表: " + table);
                    continue;
                }
                String title = tables.get(0).getTableComment();
                ConfigParam config = MainApp.CONFIG.cloneSelf();
                config.getCodeBuilder().setFrontPath(edtFrontPath.getText());
                config.getCodeBuilder().setBackPath(edtBackPath.getText());
                new GenerateCodeBuilder(MainApp.TB_DAO, log, chkCoverIfExist.isSelected()).build(config, table, title);
            }finally {
                log.call(String.format("表[%s]处理结束!", table));
            }
        }
    }

    private void clearLog() {
        Platform.runLater(()->{
            txtDemoCodeMasterController.clear();
        });
    }

}
