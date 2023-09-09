package vip.sujianfeng.enjoydao.tree;

import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.tree.intf.ScanTreeNode;
import vip.sujianfeng.enjoydao.tree.intf.TreeNodeFilter;
import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;
import vip.sujianfeng.enjoydao.tree.utils.TreeUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author SuJianFeng
 * createTime 2022/9/22
 * description
 **/
public class TreeBuilder {

    public <T extends TreeData> List<TreeNode<T>> findTreeList(List<TreeNode<T>> tree, TreeNodeFilter<T> filter) {
        if (tree == null) {
            return null;
        }
        List<TreeNode<T>> list = new ArrayList<>();
        List<TreeNode<T>> treeResult = new ArrayList<>();
        TreeUtils.tree2List(tree, tree, list, filter, true);
        TreeUtils.list2tree(list, treeResult);
        return treeResult;
    }

    public <T extends TreeData> List<TreeNode<T>> clone(List<TreeNode<T>> treeNodes, boolean containChild) {
        List<TreeNode<T>> result = new ArrayList<>();
        for (TreeNode<T> treeNode : treeNodes) {
            result.add(treeNode.clone(containChild));
        }
        return result;
    }

    public <T extends TreeData> void scanTreeNodes(List<TreeNode<T>> treeNodes, ScanTreeNode<T> inf) {
        if (treeNodes == null) {
            return;
        }
        for (TreeNode<T> treeNode : treeNodes) {
            inf.scan(treeNode);
            if (treeNode.getChildren() != null) {
                scanTreeNodes(treeNode.getChildren(), inf);
            }
        }
    }

    public List<TreeNode<? extends TreeData>> buildTreeBySql(TbDao tbDao, Class<? extends TreeData> t, String sql) throws Exception {
        List<? extends TreeData> rows = tbDao.selectListBySql(t, sql);
        List<TreeNode<? extends TreeData>> result = new ArrayList<>();
        for (TreeData item : rows) {
            String id = item.getId();
            String name = item.getName();
            String parentId = item.getParentId();
            if (StringUtilsEx.isEmpty(parentId) || rows.stream().noneMatch(it-> parentId.equals(it.getId()))) {
                result.add(new TreeNode(id, name, parentId, item, getChildren(id, rows)));
            }
        }
        return result;
    }

    public <T extends TreeData> List<TreeNode<T>> buildTreeBySqlEx(TbDao tbDao, Class<T> t, String sql) throws Exception {
        List<T> rows = tbDao.selectListBySql(t, sql);
        List<TreeNode<T>> result = new ArrayList<>();
        for (T item : rows) {
            String id = item.getId();
            String name = item.getName();
            String parentId = item.getParentId();
            if (StringUtilsEx.isEmpty(parentId) || rows.stream().noneMatch(it-> parentId.equals(it.getId()))) {
                result.add(new TreeNode<T>(id, name, parentId, item, getChildrenEx(id, rows)));
            }
        }
        return result;
    }

    private List<TreeNode<? extends TreeData>> getChildren(String pid, List<? extends TreeData> rows){
        List<TreeNode<? extends TreeData>> result = new ArrayList<>();
        List<? extends TreeData> children = rows.stream().filter(it -> pid.equals(it.getParentId())).collect(Collectors.toList());
        for (TreeData item : children) {
            String id = item.getId();
            String name = item.getName();
            result.add(new TreeNode(id, name, pid, item, getChildren(id, rows)));
        }
        return result.size() > 0 ? result : null;
    }

    private <T extends TreeData> List<TreeNode<T>> getChildrenEx(String pid, List<T> rows){
        List<TreeNode<T>> result = new ArrayList<>();
        List<T> children = rows.stream().filter(it -> pid.equals(it.getParentId())).collect(Collectors.toList());
        for (T item : children) {
            String id = item.getId();
            String name = item.getName();
            result.add(new TreeNode<T>(id, name, pid, item, getChildrenEx(id, rows)));
        }
        return result.size() > 0 ? result : null;
    }
}
