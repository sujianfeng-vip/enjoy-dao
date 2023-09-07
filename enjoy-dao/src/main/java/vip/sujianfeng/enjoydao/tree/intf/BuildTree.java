package vip.sujianfeng.enjoydao.tree.intf;

import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.tree.models.TreeDefine;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;

import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2022/9/22
 * @Description
 **/
public interface BuildTree {
    List<TreeNode<?>> build(TbDao tbDao, TreeDefine treeDefine);
}
