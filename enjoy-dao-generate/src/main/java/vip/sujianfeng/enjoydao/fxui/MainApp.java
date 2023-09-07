package vip.sujianfeng.enjoydao.fxui;


import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.datasource.DruidConfig;
import vip.sujianfeng.datasource.DruidUtils;
import vip.sujianfeng.enjoydao.MySqlEnjoyDao;
import vip.sujianfeng.enjoydao.fxui.formMain.FormEnjoyCodeBuilderController;
import vip.sujianfeng.enjoydao.fxui.formMain.FormEnjoyModelController;
import vip.sujianfeng.enjoydao.fxui.formMain.FormMainController;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.enjoydao.utils.AppUtils;
import vip.sujianfeng.fxui.comm.formLoading.LoadingUtils;
import vip.sujianfeng.fxui.ctrls.TabPaneOp;
import vip.sujianfeng.fxui.utils.FxFormUtils;
import vip.sujianfeng.utils.comm.YmlUtils;

import java.io.IOException;

/**
 * @author SuJianFeng
 * @date 2019/8/26 8:04
 **/
public class MainApp extends Application {

    public static ConfigParam CONFIG;
    public static MySqlEnjoyDao TB_DAO;

    private AnchorPane rootLayout;
    private Stage primaryStage;
    private TabPane tabPaneBody;

    private static String[] ARGS;

    public static MainApp mainApp;
    public static TabPaneOp tabPaneOp;

    public static void main(String[] args) {
        ARGS = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        mainApp = this;
        primaryStage = stage;


        //捕捉全局异常
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            FxFormUtils.showLogForm(null, throwable);
        });


        stage.setOnCloseRequest(event -> {
            //DialogUtils.showError("确定退出？");
            //程序退出时结束子线程
            System.exit(0);
        });

        //子线程执行加载任务
        LoadingUtils.load(() -> {
            AppUtils.getCurrProjectRoot();

            String ymlFile = ARGS.length > 0 ? ARGS[0] : "./enjoy-dao-ui.yml";
            logger.info("加载配置文件：{}", ymlFile);
            CONFIG = YmlUtils.loadYml(ConfigParam.class, ymlFile);

            if (CONFIG == null) {
                logger.error("不存在配置文件: {}", ymlFile);
                return;
            }

            logger.info(JSON.toJSONString(CONFIG));

            DruidConfig dbconfig = new DruidConfig();
            dbconfig.setUrl(CONFIG.getDatabase().getUrl());
            dbconfig.setDriverClassName(CONFIG.getDatabase().getDriverClassName());
            dbconfig.setDatabase(CONFIG.getDatabase().getDatabase());
            dbconfig.setUsername(CONFIG.getDatabase().getUsername());
            dbconfig.setPassword(CONFIG.getDatabase().getPassword());
            TB_DAO = new MySqlEnjoyDao(DruidUtils.getDruidDataSource(dbconfig), dbconfig.getDatabase());

        }, () -> {
            //完成后主线程执行加载后的事项

            initLayout();
            showFirstPage();
            //startTaskService.startTask();

        });
    }

    private void initLayout() {
        FormMainController formMainController = FxFormUtils.showNormalForm(FormMainController.class);
        primaryStage = formMainController.getStage();
        rootLayout = formMainController.rootPane;
        tabPaneBody = formMainController.tabPaneBody;
        tabPaneBody.getTabs().clear();
        tabPaneOp = new TabPaneOp(tabPaneBody);
    }


    private static String TAB_FIRST_PANE_ID = "tabFirstPane";
    private static String PANE_ID_ENJOY_CODE_DEMO = "tabCodeDemo";

    private boolean haveFirstPage(){
        if (tabPaneBody.getTabs().size() == 0){
            return false;
        }
        if (TAB_FIRST_PANE_ID.equalsIgnoreCase(tabPaneOp.getTab(0).getId())){
            return true;
        }
        return false;
    }

    public void showFirstPage() throws IOException {
        if (haveFirstPage()){
            tabPaneOp.selectTab(0);
            return;
        }
        FormEnjoyModelController formEnjoyModelController = FxFormUtils.buildController(FormEnjoyModelController.class);
        tabPaneOp.addTab(TAB_FIRST_PANE_ID, "模型代码生成", formEnjoyModelController.getRootNode(), false);
        tabPaneOp.addTab(PANE_ID_ENJOY_CODE_DEMO, "前后端代码生成", FxFormUtils.buildController(FormEnjoyCodeBuilderController.class).getRootNode(), false);
        formEnjoyModelController.generateModels(null, CONFIG);

    }


    public BorderPane getBorderPane(){
        return (BorderPane) rootLayout.lookup("#bodyPane");
    }

    public AnchorPane getRootLayout() {
        return rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
