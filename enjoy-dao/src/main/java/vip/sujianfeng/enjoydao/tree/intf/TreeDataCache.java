package vip.sujianfeng.enjoydao.tree.intf;

import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeDefine;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;

import java.util.List;

/**
 * 树数据缓存处理接口
 * @Author SuJianFeng
 * @Date 2022/10/9
 * @Description
 **/
public interface TreeDataCache {
    <T extends TreeData> List<TreeNode<T>> get(Class<T> dateType, TreeDefine treeDefine);
    void put(TreeDefine treeDefine, List<TreeNode<?>> treeData);
}
