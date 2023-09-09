package vip.sujianfeng.enjoydao.sqlcondition;

/**
 * author SuJianFeng
 * createTime 2022/9/2
 * description
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
