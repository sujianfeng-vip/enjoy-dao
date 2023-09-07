package vip.sujianfeng.enjoydao.fxui.formMain;

import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import vip.sujianfeng.fxui.annotations.FxForm;
import vip.sujianfeng.fxui.forms.base.FxBaseController;


/**
 * @author SuJianFeng
 * @date 2019/9/4 7:25
 **/
@FxForm(value = "/fxml/formMain/formMain.fxml", title = "主页面")
public class FormMainController extends FxBaseController {

    public AnchorPane rootPane;
    public BorderPane bodyPane;

    public TabPane tabPaneBody;

    @Override
    protected void onHidden(WindowEvent windowEvent) throws Exception {
        super.onHidden(windowEvent);
        System.exit(0);
    }
}
