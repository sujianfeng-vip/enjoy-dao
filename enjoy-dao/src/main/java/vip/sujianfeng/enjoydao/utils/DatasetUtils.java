package vip.sujianfeng.enjoydao.utils;

import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.model.DeltaData;
import vip.sujianfeng.utils.comm.ConvertUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * @Author SuJianFeng
 * @Date 2022/9/21
 * @Description 数据集工具类
 **/
public class DatasetUtils {

    /**
     * 比较两个数据集，找出Delta数据
     * @param oldRows
     * @param newRows
     * @param <T>
     * @return
     */
    public static <T extends AbstractOpModel> DeltaData<T> getDelta(List<T> oldRows, List<T> newRows) {
        DeltaData<T> result = new DeltaData<>();
        if (newRows == null) {
            //不存在新增数据
            return result;
        }
        if (oldRows == null) {
            //不存在旧数据，那么全部新增
            result.getNewRows().addAll(newRows);
            return result;
        }
        for (T newRow : newRows) {
            Optional<T> find = oldRows.stream().filter(oldRow -> oldRow.getId() != null && oldRow.getId().equalsIgnoreCase(newRow.getId())).findAny();
            if (find.isPresent()) {
                boolean changed = false;
                AbstractOpModel oldRow = find.get();
                for (Field declaredField : newRow.allDeclaredFields()) {
                    Object newValue = newRow.getFieldValue(declaredField.getName());
                    Object oldValue = oldRow.getFieldValue(declaredField.getName());
                    if (newValue != null && oldValue == null) {
                        changed = true;
                        break;
                    }
                    if (newValue != null && !ConvertUtils.cStr(newValue).equals(ConvertUtils.cStr(oldValue))) {
                        changed = true;
                        break;
                    }
                }
                if (changed) {
                    //修改
                    result.getUpdateRows().add(newRow);
                }
                continue;
            }
            //新增
            result.getNewRows().add(newRow);
        }
        for (T oldRow : oldRows) {
            Optional<T> find = newRows.stream().filter(newRow -> newRow.getId() != null && newRow.getId().equalsIgnoreCase(oldRow.getId())).findAny();
            if (!find.isPresent()) {
                //删除
                result.getDeleteRows().add(oldRow);
            }
        }
        return result;
    }

    /**
     * 将delta数据入库
     * @param tbDao
     * @param deltaData
     * @param <T>
     * @throws Exception
     */
    public static <T extends AbstractOpModel> void updateDelta(TbDao tbDao, DeltaData<T> deltaData) throws Exception {
        //新增
        for (T newRow : deltaData.getNewRows()) {
            tbDao.insert(newRow);
        }
        //修改
        for (T updateRow : deltaData.getUpdateRows()) {
            tbDao.update(updateRow);
        }
        //删除
        for (T deleteRow : deltaData.getDeleteRows()) {
            tbDao.delete(deleteRow);
        }
    }
}
