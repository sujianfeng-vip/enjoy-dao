package vip.sujianfeng.enjoydao.generate.codebuilder;

import vip.sujianfeng.enjoydao.generate.base.BaseCodeBuilder;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.ConfigParam;
import vip.sujianfeng.utils.comm.*;
import vip.sujianfeng.utils.intf.CommEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author SuJianFeng
 * @Date 2023/6/21
 * @Description
 **/
public class BackPageParamBuilder extends BaseCodeBuilder {
    private ConfigParam config;
    private String tableName;
    private String title;
    public BackPageParamBuilder(JdbcTbDao jdbcDao, CommEvent<String> logEvent, ConfigParam config, String tableName, String title) {
        super(jdbcDao, logEvent);
        this.config = config;
        this.tableName = tableName;
        this.title = title;
    }

    @Override
    protected String getFileName() {
        String modelClassName = tableName2className(tableName);
        return getCodeFilePath(config.getCodeBuilder().getBackPath(), tableName2variantName(modelClassName)) + String.format("/%sPageParam.kt", modelClassName);
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
        map.put("basePackage",  getCodePackage(config.getCodeBuilder().getBackPath(), variantCode));
        map.put("dateTime", DateTimeUtils.getDateTimeShow());
        map.put("title", title);
        map.put("rootPackage", rootPackage);
        map.put("tableVO", getModelPackage(config.getModelBuilder().getVoPath(), tableName) + "." + modelClassName + "VO");
        map.put("tableCU", getModelPackage(config.getModelBuilder().getPoPath(), tableName) + "." + modelClassName + "CU");
        String result = ConfigUtils.loadResFile("/back-code-template/page-params.txt");
        result = replaceByMap(result, map);
        return result;
    }
}
