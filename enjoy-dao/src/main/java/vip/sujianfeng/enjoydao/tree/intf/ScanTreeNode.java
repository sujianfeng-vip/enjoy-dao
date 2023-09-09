package vip.sujianfeng.enjoydao.tree.intf;

import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;

/**
 * author SuJianFeng
 * createTime 2022/10/9
 **/
public interface ScanTreeNode<T extends TreeData> {
    void scan(TreeNode<T> treeNode);
}
