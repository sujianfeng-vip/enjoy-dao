package vip.sujianfeng.enjoydao.generate;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.codebuilder.*;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.intf.CommEvent;

import java.io.IOException;

/**
 * @Author SuJianFeng
 * @Date 2023/6/21
 * @Description
 **/
public class GenerateCodeBuilder extends BaseCodeBuilder {
    public boolean coverIfExist;

    public GenerateCodeBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, boolean coverIfExist) {
        super(jdbcDao, logEvent);
        this.coverIfExist = coverIfExist;
    }

    public void build(ConfigParam config, String tableName, String title) throws IOException {
        new BackApiBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new BackPageParamBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new BackViewControllerBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListDataHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListMethodHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListReadHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListColumnsHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListToolbarHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontListTableHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontRootEditHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        new FrontRootListHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
    }

}
