package vip.sujianfeng.enjoydao.utils;

import vip.sujianfeng.enjoydao.interfaces.TbDao;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;
import vip.sujianfeng.enjoydao.model.DeltaData;
import vip.sujianfeng.utils.comm.ConvertUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 **/
public class DatasetUtils {

    public static <T extends AbstractOpModel> DeltaData<T> getDelta(List<T> oldRows, List<T> newRows) {
        DeltaData<T> result = new DeltaData<>();
        if (newRows == null) {
            return result;
        }
        if (oldRows == null) {
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
                    result.getUpdateRows().add(newRow);
                }
                continue;
            }
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

    public static <T extends AbstractOpModel> void updateDelta(TbDao tbDao, DeltaData<T> deltaData) throws Exception {

        for (T newRow : deltaData.getNewRows()) {
            tbDao.insert(newRow);
        }

        for (T updateRow : deltaData.getUpdateRows()) {
            tbDao.update(updateRow);
        }

        for (T deleteRow : deltaData.getDeleteRows()) {
            tbDao.delete(deleteRow);
        }
    }
}
