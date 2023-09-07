package vip.sujianfeng.enjoydao.tree.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.tree.intf.TreeNodeFilter;
import vip.sujianfeng.enjoydao.tree.utils.TreeUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/27
 * @Description
 **/
@ApiModel("查询子树条件")
public class QueryChildTreeParam<T extends TreeData> implements TreeNodeFilter<T> {

    @Override
    public boolean match(List<TreeNode<T>> tree, TreeNode<T> currNode) {
        ArrayList<String> tmpList = new ArrayList<>();
        if (StringUtilsEx.isNotEmpty(parentId)) {
            tmpList.add(parentId);
        }
        if (parentIds.size() > 0) {
            tmpList.addAll(parentIds);
        }
        //多个父节点时，父节点本身也要纳入，直接子节点才有归属
        if (tmpList.size() > 1) {
            for (String tmpId : tmpList) {
                if (StringUtilsEx.sameText(tmpId, currNode.getValue())) {
                    return true;
                }
            }
        }
        if (oneLevel) {
            //只取一层
            for (String tmpId : tmpList) {
                if (StringUtilsEx.sameText(currNode.getParentId(), tmpId)) {
                    return true;
                }
            }
            return false;
        }
        if (tmpList.size() == 0) {
            //取全部
            return true;
        }
        for (String tmpId : tmpList) {
            TreeNode<T> rootNode = TreeUtils.getTreeNodeByValue(tree, tmpId);
            if (rootNode == null ) {
                List<TreeNode<T>> list = new ArrayList<>();
                TreeUtils.getChildren(tree, list, tmpId, false);
                for (TreeNode<T> node : list) {
                    if (TreeUtils.isInTree(node, currNode.getValue())) {
                        return true;
                    }
                }
                return false;
            }
            if (!StringUtilsEx.sameText(currNode.getValue(), tmpId) //不包含指根节点
                    && TreeUtils.isInTree(rootNode, currNode.getValue())) {
                return true;
            }
        }
        return false;
    }


    @ApiModelProperty("父节点id（如果parentIds和parentId同时为空表示整棵树）")
    private String parentId;
    @ApiModelProperty("父节点列表（如果parentIds和parentId同时存在，那么数据会合并）")
    private List<String> parentIds = new ArrayList<>();
    @ApiModelProperty("是否只取一级")
    private boolean oneLevel;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(boolean oneLevel) {
        this.oneLevel = oneLevel;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }
}
