package vip.sujianfeng.enjoydao.fxui.formMain;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.fxui.MainApp;
import vip.sujianfeng.enjoydao.generate.GenerateModelBuilder;
import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.enjoydao.utils.AppUtils;
import vip.sujianfeng.fxui.annotations.FxForm;
import vip.sujianfeng.fxui.forms.base.FxBaseController;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.intf.CommEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模型生成
 * @author SuJianFeng
 * @date 2019/9/6 14:06
 **/
@FxForm(value = "/fxml/formMain/formEnjoyModel.fxml", title = "Enjoy代码生成工具")
public class FormEnjoyModelController extends FxBaseController {

    public TextArea txtBuildLog;
    public static FormEnjoyModelController ME;

    @Override
    public void initPage(Object... params) {
        super.initPage(params);
        ME = this;
    }

    public void generateModels(ActionEvent actionEvent) {
        generateModels(actionEvent, MainApp.CONFIG);
    }

    public void generateModels(ActionEvent actionEvent, ConfigParam configParam) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue(1), new ThreadPoolExecutor.DiscardPolicy());
        threadPoolExecutor.execute(()->{
            try {

                String projectRoot = AppUtils.getCurrProjectRoot();
                addLog("项目根目录：{}", projectRoot);
                CommEvent<String> log = txt -> {
                    Platform.runLater(()->{
                        addLog(txt);
                    });
                };
                GenerateModelBuilder generateModelBuilder = new GenerateModelBuilder(
                        MainApp.TB_DAO,
                        log,
                        MainApp.CONFIG.getModelBuilder().getModelBase(),
                        String.format("%s/%s", projectRoot, MainApp.CONFIG.getModelBuilder().getPoPath()),
                        String.format("%s/%s", projectRoot, MainApp.CONFIG.getModelBuilder().getVoPath()),
                        MainApp.CONFIG.getModelBuilder().getIgnoreFields()
                );
                generateModelBuilder.build(configParam);
                FormEnjoyModelController.ME.addLog("完成!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addLog(String s, Object... params) {
        Platform.runLater(()->{
            String text = s.replace("{}", "%s");
            text = String.format(text, params);
            logger.info(text);
            this.txtBuildLog.appendText("\n");
            this.txtBuildLog.appendText(text);
        });
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
