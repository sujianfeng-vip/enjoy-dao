package vip.sujianfeng.enjoydao.enums;

/**
 * author SuJianFeng
 * createTime 2019/1/30 11:14
 **/
public enum TbDefineFieldType {
    ftString, ftInt, ftLongInt, ftDate, ftTimestamp, ftBoolean, ftMoney, ftText, ftLongtext, ftJson, ftJsonb;

    public static TbDefineFieldType valueOfStr(String name){
        for (TbDefineFieldType tbDefineFieldType : TbDefineFieldType.values()) {
            if (name.equalsIgnoreCase(tbDefineFieldType.name()) || ("ft" + name).equalsIgnoreCase(tbDefineFieldType.name())){
                return tbDefineFieldType;
            }
        }
        return ftString;
    }
}
