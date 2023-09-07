package vip.sujianfeng.enjoydao.generate.enmus;

/**
 * @Author SuJianFeng
 * @Date 2023/6/23
 * @Description
 **/
public enum ConstText {

    AutoCreateCanModify("AutoCreateCanModify", "读取数据库生成的代码，仅不存在时生成，可修改"),
    ViewTitle("ViewTitle", "界面视图"),
    SystemError("SystemError", "系统错误"),
    SaveSuccess("SaveSuccess", "保存成功!"),
    ResetLabel("ResetLabel", "重置"),
    SaveLabel("SaveLabel", "保存"),
    AddLabel("AddLabel", "新增"),
    ModifyLabel("ModifyLabel", "修改"),
    HintLabel("HintLabel", "提示"),
    SearchLabel("SearchLabel", "查询"),
    ConfirmDeleteRowHint("ConfirmDeleteRowHint", "您确定删除此行数据?"),
    DeleteSuccessHint("DeleteSuccessHint", "删除成功!"),
    OpLabel("OpLabel", "操作"),
    InputKeywordHint("InputKeywordHint", "请输入关键字..."),
    DeleteLabel("DeleteLabel", "删除");

    private String code;
    private String title;

    ConstText(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
