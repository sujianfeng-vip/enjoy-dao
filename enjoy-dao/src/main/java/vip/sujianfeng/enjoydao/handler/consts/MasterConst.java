package vip.sujianfeng.enjoydao.handler.consts;

/**
 * @Author SuJianFeng
 * @Date 2022/9/21
 * @Description
 **/
public class MasterConst {

    public static final String REST_MAP_SAVE_URI = "/save";
    public static final String REST_MAP_SAVE_NAME = "保存（id不存在是新增、反之更新）";

    public static final String REST_MAP_ADD_URI = "/add";
    public static final String REST_MAP_ADD_NAME = "新增";

    public static final String REST_MAP_UPDATE_URI = "/update";
    public static final String REST_MAP_UPDATE_NAME = "更新（选择性）";

    public static final String REST_MAP_DELETE_URI = "/delete";
    public static final String REST_MAP_DELETE_NAME = "批量逻辑删除";

    public static final String REST_MAP_QUERY_ONE_URI = "/queryOne";
    public static final String REST_MAP_QUERY_ONE_NAME = "查询单个";

    public static final String REST_MAP_QUERY_PAGE_URI = "/queryPage";
    public static final String REST_MAP_QUERY_PAGE_NAME = "查询单页";

    public static final String REST_MAP_BATCH_UPDATE_URI = "/batchUpdateField";
    public static final String REST_MAP_BATCH_UPDATE_NAME = "批量更新指定栏位";
}
