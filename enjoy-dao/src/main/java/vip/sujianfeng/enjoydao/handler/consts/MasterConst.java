package vip.sujianfeng.enjoydao.handler.consts;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 * description
 **/
public class MasterConst {

    public static final String REST_MAP_SAVE_URI = "/save";
    public static final String REST_MAP_SAVE_NAME = "Save (if the ID does not exist, it is a new addition, otherwise it will be updated)";

    public static final String REST_MAP_ADD_URI = "/add";
    public static final String REST_MAP_ADD_NAME = "newly added";

    public static final String REST_MAP_UPDATE_URI = "/update";
    public static final String REST_MAP_UPDATE_NAME = "Update (optional)";

    public static final String REST_MAP_DELETE_URI = "/delete";
    public static final String REST_MAP_DELETE_NAME = "Batch logical deletion";

    public static final String REST_MAP_QUERY_ONE_URI = "/queryOne";
    public static final String REST_MAP_QUERY_ONE_NAME = "Query individual";

    public static final String REST_MAP_QUERY_PAGE_URI = "/queryPage";
    public static final String REST_MAP_QUERY_PAGE_NAME = "Query single page";

    public static final String REST_MAP_BATCH_UPDATE_URI = "/batchUpdateField";
    public static final String REST_MAP_BATCH_UPDATE_NAME = "Batch update of specified fields";
}
