package vip.sujianfeng.enjoydao.tree.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.sujianfeng.enjoydao.tree.intf.TreeNodeFilter;
import vip.sujianfeng.enjoydao.tree.utils.TreeUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/27
 **/
@ApiModel("Query subtree conditions")
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
        //When there are multiple parent nodes, the parent node itself must also be included in order for direct child nodes to have ownership
        if (tmpList.size() > 1) {
            for (String tmpId : tmpList) {
                if (StringUtilsEx.sameText(tmpId, currNode.getValue())) {
                    return true;
                }
            }
        }
        if (oneLevel) {
            // Take only one layer
            for (String tmpId : tmpList) {
                if (StringUtilsEx.sameText(currNode.getParentId(), tmpId)) {
                    return true;
                }
            }
            return false;
        }
        if (tmpList.size() == 0) {
            // Take All
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
            if (!StringUtilsEx.sameText(currNode.getValue(), tmpId)
                    && TreeUtils.isInTree(rootNode, currNode.getValue())) {
                return true;
            }
        }
        return false;
    }


    @ApiModelProperty("Parent node ID (if parentIds and parentId are both empty, it represents the entire tree)")
    private String parentId;
    @ApiModelProperty("Parent node list (if parentIds and parentId both exist, the data will be merged)")
    private List<String> parentIds = new ArrayList<>();
    @ApiModelProperty("Whether to take only one level")
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
