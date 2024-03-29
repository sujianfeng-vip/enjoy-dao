package vip.sujianfeng.enjoydao.generate.codebuilder;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.models.MySqlTableColumn;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.comm.ConfigUtils;
import vip.sujianfeng.utils.comm.HumpNameUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.List;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2023/6/21
 * description
 **/
public class FrontRootListHtmlBuilder extends BaseCodeBuilder {
    private ConfigParam config;
    private String tableName;
    private String title;

    public FrontRootListHtmlBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, ConfigParam config, String tableName, String title) {
        super(jdbcDao, logEvent);
        this.config = config;
        this.tableName = tableName;
        this.title = title;
    }


    @Override
    protected String getFileName() {
        String modelClassName = tableName2className(tableName);
        String modelCode = HumpNameUtils.humpToMidLine2(modelClassName);
        return getCodeFilePath(config.getCodeBuilder().getFrontPath(), modelCode) + "/list.html";
    }


    @Override
    protected String getCode(Map<String, String> map) throws Exception {
        String result = ConfigUtils.loadResFile("/front-code-template/list.html");

        StringBuilderEx tableColumns = new StringBuilderEx();
        List<MySqlTableColumn> tableColumnList = getTableColumns(getJdbcDao(), tableName);
        tableColumnList.forEach(it -> {
            if (isSystemField(it.getColumnName())) {
                return;
            }
            tableColumns.appendFR("            {title: '%s', key: '%s'}, ", it.getColumnComment(), HumpNameUtils.underLineToHump(it.getColumnName()));
        });
        map.put("tableColumns", tableColumns.toString());

        return replaceByMap(result, map);
    }
}
