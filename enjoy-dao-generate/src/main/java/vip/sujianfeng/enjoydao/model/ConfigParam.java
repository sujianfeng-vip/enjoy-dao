package vip.sujianfeng.enjoydao.model;

import com.alibaba.fastjson.JSON;
import vip.sujianfeng.datasource.DruidConfig;

/**
 * author SuJianFeng
 * createTime 2022/9/23
 * description
 **/
public class ConfigParam {
    private DruidConfig database;
    private ModelBuilderParam modelBuilder;
    private CodeBuilderParam codeBuilder;

    public ConfigParam cloneSelf() {
        return JSON.parseObject(JSON.toJSONString(this), ConfigParam.class);

    }



    public DruidConfig getDatabase() {
        return database;
    }

    public void setDatabase(DruidConfig database) {
        this.database = database;
    }

    public ModelBuilderParam getModelBuilder() {
        return modelBuilder;
    }

    public void setModelBuilder(ModelBuilderParam modelBuilderParam) {
        this.modelBuilder = modelBuilderParam;
    }

    public CodeBuilderParam getCodeBuilder() {
        return codeBuilder;
    }

    public void setCodeBuilder(CodeBuilderParam codeBuilder) {
        this.codeBuilder = codeBuilder;
    }
}
