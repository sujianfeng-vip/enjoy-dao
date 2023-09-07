package vip.sujianfeng.enjoydao.interfaces;

import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineExpressionField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;

public interface SqlAdapter {
    String getKeyDefine();
    String getDefineFieldTypeSql(TbDefineField fieldType);
    String getSqlValue(TbDefineField defineField);
    String getSelectFieldSql(TbDefineField rlsField);
    String getSelectFieldSql(TbDefineRelationField rlsField);
    String getSelectFieldSql(TbDefineExpressionField exprField);
    String getLeftJoinSql(TbDefineRelationField rlsField);
}
