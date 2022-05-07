package vip.sujianfeng.enjoydao.interfaces;

import vip.sujianfeng.enjoydao.enums.TbDefineFieldType;
import vip.sujianfeng.enjoydao.enums.TbKeyType;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;

public interface SqlAdapter {
    String getKeyDefine(TbKeyType keyType);
    String getDefineFieldTypeSql(TbDefineField fieldType);
    String getSqlValue(TbDefineField defineField);
    String getSelectFieldSql(TbDefineField rlsField);
    String getSelectFieldSql(TbDefineRelationField rlsField);
    String getLeftJoinSql(TbDefineRelationField rlsField);
}
