package vip.sujianfeng.enjoydao.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.enjoydao.tree.intf.ScanTreeNode;
import vip.sujianfeng.enjoydao.tree.intf.TreeDataCache;
import vip.sujianfeng.enjoydao.tree.intf.TreeNodeFilter;
import vip.sujianfeng.enjoydao.tree.models.QueryChildTreeParam;
import vip.sujianfeng.enjoydao.tree.models.TreeData;
import vip.sujianfeng.enjoydao.tree.models.TreeDefine;
import vip.sujianfeng.enjoydao.tree.models.TreeNode;
import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.utils.cache.ThreadCycleTask;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * author SuJianFeng
 * createTime 2022/9/22
 **/
public class TreeCycleTask extends ThreadCycleTask {

    private TbDao tbDao;
    private List<TreeDefine> treeDefineList;
    private TreeDataCache treeDataCache;
    private TreeBuilder treeBuilder = new TreeBuilder();

    public <T extends TreeData> List<TreeNode<T>> findTreeList(Class<T> t, String key, TreeNodeFilter<T> filter) {
        TreeDefine treeDefine = getTreeDefine(key);
        if (treeDefine == null) {
            return null;
        }
        return this.treeBuilder.findTreeList(this.treeDataCache.get(t, treeDefine), filter);
    }

    public <T extends TreeData> List<TreeNode<T>> buildTreeBySqlEx(TbDao tbDao, Class<T> t, String sql) throws Exception {
        return this.treeBuilder.buildTreeBySqlEx(tbDao, t, sql);
    }

    public <T extends TreeData> void scanTreeNodes(List<TreeNode<T>> treeNodes, ScanTreeNode<T> inf) {
        this.treeBuilder.scanTreeNodes(treeNodes, inf);
    }

    public <T extends TreeData> List<TreeNode<T>> clone(List<TreeNode<T>> list, boolean containChild) {
        return this.treeBuilder.clone(list, containChild);
    }

    public TreeDefine getTreeDefine(String key) {
        Optional<TreeDefine> find = treeDefineList.stream().filter(it -> it.getKey().equals(key)).findAny();
        return find.orElse(null);
    }

    public TreeCycleTask(TbDao tbDao, TreeDataCache treeDataCache, TreeDefine... treeDefineList) {
        super("Tree Cache Data");
        this.treeDataCache = treeDataCache;
        this.tbDao = tbDao;
        this.treeDefineList = new ArrayList<>(Arrays.asList(treeDefineList));
    }

    @Override
    public void cycleExecute() throws Exception {
        if (this.treeDefineList == null) {
            return;
        }
        for (TreeDefine treeDefine : this.treeDefineList) {
            updateCache(treeDefine);
        }
    }

    public void updateCache(TreeDefine tTreeDefine) throws Exception {
        List<TreeNode<?>> treeData = null;
        if (tTreeDefine.getBuildTree() != null) {
            treeData = tTreeDefine.getBuildTree().build(tbDao, tTreeDefine);
        }else if (StringUtilsEx.isNotEmpty(tTreeDefine.getQuerySql())) {
            treeData = this.treeBuilder.buildTreeBySql(tbDao, tTreeDefine.getT(), tTreeDefine.getQuerySql());
        }
        if (treeData != null) {
            this.treeDataCache.put(tTreeDefine, treeData);
            logger.info("[{}/{}]Sync completed!", tTreeDefine.getKey(), tTreeDefine.getTitle());
        }
    }

    public List<TreeDefine> getTreeDefineList() {
        return treeDefineList;
    }

    public void setTreeDefineList(List<TreeDefine> treeDefineList) {
        this.treeDefineList = treeDefineList;
    }

    public TreeBuilder getTreeBuilder() {
        return treeBuilder;
    }

    public void setTreeBuilder(TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
