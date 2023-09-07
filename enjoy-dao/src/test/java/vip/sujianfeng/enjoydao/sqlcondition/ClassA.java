package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class ClassA<T extends ClassA> {

    public T and() {
        return (T) this;
    }

    public T me() {
        return (T) this;
    }
}