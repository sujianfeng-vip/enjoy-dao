package vip.sujianfeng.enjoydao.tree.utils;

import vip.sujianfeng.enjoydao.tree.intf.TreeNodeFilter;
import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/10/11
 **/
public class TreeUtils {

    public static <T extends TreeData> TreeNode<T> getTreeNodeByValue(List<TreeNode<T>> tree, String value) {
        if (tree == null) {
            return null;
        }
        for (TreeNode<T> treeNode : tree) {
            if (StringUtilsEx.sameText(treeNode.getValue(), value)) {
                return treeNode;
            }
            TreeNode<T> tmp = getTreeNodeByValue(treeNode.getChildren(), value);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    public static <T extends TreeData> void getChildren(List<TreeNode<T>> tree, List<TreeNode<T>> toList, String parentId, boolean clone) {
        if (tree == null) {
            return;
        }
        for (TreeNode<T> currNode : tree) {
            if (StringUtilsEx.sameText(currNode.getParentId(), parentId)) {
                toList.add(clone ? currNode.clone(false) : currNode);
            }
            getChildren(currNode.getChildren(), toList, parentId, clone);
        }
    }


    public static <T extends TreeData> void tree2List(List<TreeNode<T>> fromTree, List<TreeNode<T>> fromList, List<TreeNode<T>> toList, TreeNodeFilter<T> filter, boolean clone) {
        for (TreeNode<T> currNode : fromList) {
            if (filter.match(fromTree, currNode)) {
                toList.add(clone ? currNode.clone(false) : currNode);
            }
            if (currNode.getChildren() != null) {
                tree2List(fromTree, currNode.getChildren(), toList, filter, clone);
            }
        }
    }

    public static <T extends TreeData> void list2tree(List<TreeNode<T>> fromList, List<TreeNode<T>> toTree) {
        for (TreeNode<T> currNode : fromList) {
            if (TreeUtils.getTreeNodeByValue(toTree, currNode.getValue()) != null) {
                continue;
            }
            TreeNode<T> parentNode = TreeUtils.getTreeNodeByValue(fromList, currNode.getParentId());
            if (parentNode != null) {
                if (parentNode.getChildren() == null ) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(currNode);
                continue;
            }
            toTree.add(currNode);
        }
    }

    public static <T extends TreeData> boolean isInTree(TreeNode<T> treeNode, String value) {
        if (StringUtilsEx.sameText(treeNode.getValue(), value)) {
            return true;
        }
        if (treeNode.getChildren() != null) {
            for (TreeNode<T> child : treeNode.getChildren()) {
                if (isInTree(child, value)) {
                    return true;
                }
            }
        }
        return false;
    }
}
