package vip.sujianfeng.enjoydao.sqlbuilder;

import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;
import vip.sujianfeng.enjoydao.model.AbstractOpModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author SuJianFeng
 * @Date 2019/1/30 11:45
 **/
public class TbUpdateSql extends TbTableSql {

    public <T> TbUpdateSql(SqlAdapter sqlAdapter, Class<T> t, AbstractOpModel obj, String... updateFields) {
        super(sqlAdapter, t, obj);
        this.updateFields = Arrays.asList(updateFields);
    }

    public <T> TbUpdateSql(SqlAdapter sqlAdapter, Class<T> t, AbstractOpModel obj, List<String> updateFields) {
        super(sqlAdapter, t, obj);
        this.updateFields = updateFields;
    }

    private List<String> updateFields;


    /**
     * 取得指定参与update的这部分自动
     * @return
     */
    public List<TbDefineField> getUpdateFields(){
        boolean all = updateFields == null || updateFields.size() == 0;
        if (all){
            return getAllUpdateFields();
        }
        List<TbDefineField> list = new ArrayList<>();
        for (TbDefineField field: getAllUpdateFields()){
            if (all || ConvertUtils.oneOfString(field.getField(), updateFields)){
                list.add(field);
            }
        }
        return list;
    }

}
