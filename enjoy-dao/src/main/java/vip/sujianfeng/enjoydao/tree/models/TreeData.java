package vip.sujianfeng.enjoydao.tree.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * author SuJianFeng
 * createTime 2022/10/2 0002
 * description
 **/
public class TreeData {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
