package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * author SuJianFeng
 * createTime 2022/9/2
 * description
 **/
public class ClassA<T extends ClassA> {

    public T and() {
        return (T) this;
    }

    public T me() {
        return (T) this;
    }
}