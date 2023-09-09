package vip.sujianfeng.enjoydao.tree.intf;

import io.swagger.annotations.ApiModel;
import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;

import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/10/11
 * description
 **/
@ApiModel("树节点过滤器")
public interface TreeNodeFilter<T extends TreeData> {
    boolean match(List<TreeNode<T>> allNodes, TreeNode<T> currNode);
}
