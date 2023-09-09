package vip.sujianfeng.enjoydao.generate.codebuilder;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.comm.*;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * author SuJianFeng
 * createTime 2023/6/21
 * description
 **/
public class BackViewControllerBuilder extends BaseCodeBuilder {
    private ConfigParam config;
    private String tableName;
    private String title;

    public BackViewControllerBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, ConfigParam config, String tableName, String title) {
        super(jdbcDao, logEvent);
        this.config = config;
        this.tableName = tableName;
        this.title = title;
    }

    @Override
    protected String getFileName() {
        String modelClassName = tableName2className(tableName);
        return getCodeFilePath(config.getCodeBuilder().getBackPath(), tableName2variantName(modelClassName)) + String.format("/%sViewController.kt", modelClassName);
    }

    @Override
    protected String getCode(Map<String, String> map) {
        String rootPackage = getModelPackage(config.getModelBuilder().getPoPath(), tableName);
        rootPackage = StringUtilsEx.leftStr(rootPackage, ".po");
        String modelClassName = tableName2className(tableName);
        String modelCode = HumpNameUtils.humpToMidLine2(modelClassName);
        String variantCode = tableName2variantName(modelClassName);
        map.put("modelClassName", modelClassName);
        map.put("modelCode", "module/" + modelCode);
        map.put("moduleApi", "module/" + modelCode + "-api");
        map.put("viewUriRoot", StringUtilsEx.rightStr(config.getCodeBuilder().getFrontPath(), "templates/"));
        map.put("basePackage",  getCodePackage(config.getCodeBuilder().getBackPath(), variantCode));
        map.put("dateTime", DateTimeUtils.getDateTimeShow());
        map.put("title", title);
        map.put("rootPackage", rootPackage);
        map.put("tableVO", getModelPackage(config.getModelBuilder().getVoPath(), tableName) + "." + modelClassName + "VO");
        String result = ConfigUtils.loadResFile("/back-code-template/view-controller.txt");
        result = replaceByMap(result, map);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
