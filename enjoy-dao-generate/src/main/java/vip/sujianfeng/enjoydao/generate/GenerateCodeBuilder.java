package vip.sujianfeng.enjoydao.generate;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.generate.codebuilder.*;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.intf.CommEvent;

import java.io.IOException;

/**
 * author SuJianFeng
 * createTime 2023/6/21
 * description
 **/
public class GenerateCodeBuilder extends BaseCodeBuilder {
    public boolean coverIfExist;
    public boolean frontCode;
    public boolean backCode;


    public GenerateCodeBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, boolean coverIfExist, boolean frontCode, boolean backCode) {
        super(jdbcDao, logEvent);
        this.coverIfExist = coverIfExist;
        this.backCode = backCode;
        this.frontCode = frontCode;
    }

    public void build(ConfigParam config, String tableName, String title) throws IOException {
        if (this.backCode) {
            new BackApiBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
            new BackPageParamBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        }
        if (this.frontCode) {
            new BackViewControllerBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
            new FrontRootEditHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
            new FrontRootListHtmlBuilder(getJdbcDao(), this.getLogEvent(), config, tableName, title).buildCodeFile(coverIfExist);
        }
    }

}
