package vip.sujianfeng.enjoydao.tree.models;

import vip.sujianfeng.enjoydao.tree.intf.BuildTree;


/**
 * @Author SuJianFeng
 * @Date 2022/9/22
 * @Description
 **/
public class TreeDefine {
    private String key;
    private String title;
    private String querySql;
    private BuildTree buildTree;
    private Class<? extends TreeData> t;

    public TreeDefine(Class<? extends TreeData> t, String key, String title, String querySql, BuildTree buildTree) {
        this.t = t;
        this.key = key;
        this.title = title;
        this.querySql = querySql;
        if (buildTree != null) {
            this.buildTree = buildTree;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public BuildTree getBuildTree() {
        return buildTree;
    }

    public void setBuildTree(BuildTree buildTree) {
        this.buildTree = buildTree;
    }

    public Class<? extends TreeData> getT() {
        return t;
    }

    public void setT(Class<? extends TreeData> t) {
        this.t = t;
    }
}
