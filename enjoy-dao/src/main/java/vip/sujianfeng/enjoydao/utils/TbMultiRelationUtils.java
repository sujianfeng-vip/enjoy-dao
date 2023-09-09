package vip.sujianfeng.enjoydao.utils;

import vip.sujianfeng.enjoydao.annotations.TbMultiRelationField;
import vip.sujianfeng.enjoydao.interfaces.JdbcTbDao;
import vip.sujianfeng.enjoydao.model.OneValue;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.ReflectUtils;
import vip.sujianfeng.utils.comm.StringBuilderEx;
import vip.sujianfeng.utils.comm.StringUtilsEx;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * author SuJianFeng
 * createTime 2022/10/25
 * description
 **/
public class TbMultiRelationUtils {

    public static <T> void batchSetRelationFieldValue(JdbcTbDao jdbcTbDao, Class<T> t, List<T> rows) throws Exception {
        if (rows.size() == 0) {
            return;
        }
        Map<Field, TbMultiRelationField> map = new HashMap<>();
        AnnotationUtils.getFieldAnnotation(map, t, TbMultiRelationField.class);
        for (Map.Entry<Field, TbMultiRelationField> entry : map.entrySet()) {
            batchSetRelationFieldValue(jdbcTbDao, t, rows, entry.getKey(), entry.getValue());
        }
    }

    public static <T> void batchSetRelationFieldValue(JdbcTbDao jdbcTbDao, Class<T> t, List<T> rows, Field rlsField, TbMultiRelationField relationField) throws Exception {
        if (rows.size() == 0) {
            return;
        }
        StringBuilderEx ids = new StringBuilderEx("''");
        Field idField = ReflectUtils.getDeclaredField(t, relationField.idField());
        if (idField == null) {
            return;
        }
        idField.setAccessible(true);
        for (T row : rows) {
            String value = ConvertUtils.cStr(idField.get(row));
            if (StringUtilsEx.isNotEmpty(value) && !ids.toString().contains(",'" + value + "'")) {
                ids.append(",'").append(value).append("'");
            }
        }
        for (int i = 0; i < relationField.tables().length; i++) {
            if (relationField.srcFields().length <= i) {
                break;
            }
            String table = relationField.tables()[i];
            String srcField = relationField.srcFields()[i];
            List<OneValue> values = jdbcTbDao.selectListBySql(OneValue.class, String.format("select id, %s value from %s where id in (%s)", srcField, table, ids));
            for (T row : rows) {
                String value = ConvertUtils.cStr(idField.get(row));
                if (StringUtilsEx.isEmpty(rlsField.get(row))) {
                    if (StringUtilsEx.isNotEmpty(value)) {
                        Optional<OneValue> find = values.stream().filter(it -> value.equals(it.getId())).findAny();
                        if (find.isPresent()) {
                            rlsField.set(row, find.get().getValue());
                        }
                    }
                }
            }
        }
    }

}
