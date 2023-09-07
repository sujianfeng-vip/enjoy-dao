package vip.sujianfeng.enjoydao.tree.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/22
 * @Description
 **/
@ApiModel(description = "树节点")
public class TreeNode<T extends TreeData> {
    @ApiModelProperty("值")
    private String value;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("是否勾选")
    private boolean checked;
    @ApiModelProperty("是否展开")
    private boolean expand;
    @ApiModelProperty("子集")
    private List<TreeNode<T>> children;
    private T data;

    public TreeNode<T> clone(boolean containChild) {
        List<TreeNode<T>> children = null;
        if (containChild) {
            if (this.children != null && this.children.size() > 0) {
                children = new ArrayList<>();
                for (TreeNode<T> child : this.children) {
                    children.add(child.clone(true));
                }
            }
        }
        return new TreeNode<T>(this.value, this.title, this.parentId, this.data, children);
    }

    public TreeNode() {
    }

    public TreeNode(String value, String title, String parentId, T data, List<TreeNode<T>> children) {
        this.value = value;
        this.title = title;
        this.parentId = parentId;
        this.data = data;
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
