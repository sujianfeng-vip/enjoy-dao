package vip.sujianfeng.enjoydao;

import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineExpressionField;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;
import vip.sujianfeng.enjoydao.interfaces.SqlAdapter;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineField;
import vip.sujianfeng.enjoydao.sqlbuilder.TbDefineRelationField;

public class MySqlAdapter implements SqlAdapter {
    
    @Override
    public String getKeyDefine() {
        return "varchar(50)";
    }

    @Override
    public String getDefineFieldTypeSql(TbDefineField defineField) {
        switch (defineField.getFieldType()) {
            case ftString:
                if (defineField.getLength() <= 0){
                    return "varchar(255)";
                }
                return String.format("varchar(%s)", defineField.getLength());
            case ftInt:
            case ftBoolean:
                return "int";
            case ftLongInt:
                return "bigint";
            case ftTimestamp:
                return "timestamp";
            case ftDate:
                return "date";
            case ftMoney:
                return "double(10, 4) DEFAULT '0.00'";
            case ftText:
                return "text";
            case ftLongtext:
                return "longtext";
            case ftJson:
                return "json";
            case ftJsonb:
                return "jsonb";
        }
        return "varchar(255)";
    }

    @Override
    public String getSqlValue(TbDefineField defineField) {
        Object value = defineField.getValue();
        switch (defineField.getFieldType()) {
            case ftString:
            case ftJson:
            case ftText:
            case ftLongtext:
            case ftJsonb:
            case ftTimestamp:
                return String.format("'%s'", value);
            case ftInt:
            case ftLongInt:
            case ftBoolean:
                return String.format("%s", ConvertUtils.cInt(value));
        }
        return String.format("'%s'", value);
    }

    @Override
    public String getSelectFieldSql(TbDefineField field) {
        return String.format(" a.`%s` `%s`", field.getTableField(), field.getField());
    }

    @Override
    public String getSelectFieldSql(TbDefineRelationField rlsField) {
        boolean srcEmpty = StringUtilsEx.isEmpty(rlsField.getSrcFieldAlias());
        if (rlsField.getSrcField().contains(".")){
            return String.format(", %s `%s`", rlsField.getSrcField(), srcEmpty ? rlsField.getField() : rlsField.getSrcFieldAlias());
        }
        return String.format(", %s.`%s` `%s`", rlsField.getJoinTableAlias(), rlsField.getSrcField(), srcEmpty ? rlsField.getField() : rlsField.getSrcFieldAlias());
    }

    @Override
    public String getSelectFieldSql(TbDefineExpressionField exprField) {
        return String.format(", (%s) `%s` ", exprField.getExpression(), exprField.getFieldName());
    }

    @Override
    public String getLeftJoinSql(TbDefineRelationField rlsField) {
        return String.format("left join %s %s on %s", rlsField.getJoinTable(), rlsField.getJoinTableAlias(), rlsField.getJoinCondition());
    }
}
