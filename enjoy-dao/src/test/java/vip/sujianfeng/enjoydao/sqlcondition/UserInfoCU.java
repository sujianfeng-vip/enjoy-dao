package vip.sujianfeng.enjoydao.sqlcondition;

import com.alibaba.fastjson.JSON;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class UserInfoCU extends SqlConditionBuilder<UserInfoCU> {

    private SqlConditionField<UserInfoCU> ID;
    private SqlConditionField<UserInfoCU> NAME;

    public static void main(String[] args) {
        UserInfoCU cu = new UserInfoCU();
        cu.ID = new SqlConditionField<>(cu, "a.id");
        cu.NAME = new SqlConditionField<>(cu, "a.name");
        cu.ID.like("%xxx%").and().NAME.equalsValue("xxx").or().newPar(it->{
            it.ID.between(1, 100).and().block("exists(select 1)");
        }).and().ID.equalsValue("aaa").orderBy(cu.NAME.asc(), cu.ID.desc()).page(1, 100);

        System.out.println(cu.getExpression().toString());
        System.out.println(JSON.toJSONString(cu.getParamMap()));
    }

    public SqlConditionField<UserInfoCU> getID() {
        return ID;
    }

    public void setID(SqlConditionField<UserInfoCU> ID) {
        this.ID = ID;
    }

    public SqlConditionField<UserInfoCU> getNAME() {
        return NAME;
    }

    public void setNAME(SqlConditionField<UserInfoCU> NAME) {
        this.NAME = NAME;
    }

}
