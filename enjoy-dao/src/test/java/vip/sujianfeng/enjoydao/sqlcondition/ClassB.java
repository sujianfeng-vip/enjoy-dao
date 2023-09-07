package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * @Author SuJianFeng
 * @Date 2022/9/2
 * @Description
 **/
public class ClassB extends ClassA<ClassB> {

    private String b1;

    public static void main(String[] args) {
        ClassB b = new ClassB();
        String value = b.me().getB1();
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }
}
