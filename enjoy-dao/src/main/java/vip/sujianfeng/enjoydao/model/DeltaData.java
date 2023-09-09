package vip.sujianfeng.enjoydao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 **/
public class DeltaData<T extends AbstractOpModel> {

    private List<T> newRows = new ArrayList<>();
    private List<T> updateRows = new ArrayList<>();
    private List<T> deleteRows = new ArrayList<>();

    public List<T> getNewRows() {
        return newRows;
    }

    public void setNewRows(List<T> newRows) {
        this.newRows = newRows;
    }

    public List<T> getUpdateRows() {
        return updateRows;
    }

    public void setUpdateRows(List<T> updateRows) {
        this.updateRows = updateRows;
    }

    public List<T> getDeleteRows() {
        return deleteRows;
    }

    public void setDeleteRows(List<T> deleteRows) {
        this.deleteRows = deleteRows;
    }
}
