package vip.sujianfeng.enjoydao.tree.intf;

import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;

/**
 * 便利树节点
 * @Author SuJianFeng
 * @Date 2022/10/9
 * @Description
 **/
public interface ScanTreeNode<T extends TreeData> {
    void scan(TreeNode<T> treeNode);
}
