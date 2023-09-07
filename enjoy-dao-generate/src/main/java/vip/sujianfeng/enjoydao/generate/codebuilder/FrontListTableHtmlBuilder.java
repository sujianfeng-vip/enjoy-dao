package vip.sujianfeng.enjoydao.generate.codebuilder;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.comm.ConfigUtils;
import vip.sujianfeng.utils.comm.HumpNameUtils;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.Map;

/**
 * @Author SuJianFeng
 * @Date 2023/6/21
 * @Description
 **/
public class FrontListTableHtmlBuilder extends BaseCodeBuilder {
    private ConfigParam config;
    private String tableName;
    private String title;

    public FrontListTableHtmlBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, ConfigParam config, String tableName, String title) {
        super(jdbcDao, logEvent);
        this.config = config;
        this.tableName = tableName;
        this.title = title;
    }

    @Override
    protected String getFileName() {
        String modelClassName = tableName2className(tableName);
        String modelCode = HumpNameUtils.humpToMidLine2(modelClassName);
        return getCodeFilePath(config.getCodeBuilder().getFrontPath(), modelCode) + "/list/table.html";
    }


    @Override
    protected String getCode(Map<String, String> map) {
        String result = ConfigUtils.loadResFile("/front-code-template/list/table.html");
        return replaceByMap(result, map);
    }
}
