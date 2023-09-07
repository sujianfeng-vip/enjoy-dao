package vip.sujianfeng.enjoydao.interfaces;

/**
 * @Author 苏建锋
 * @create 2020-02-09 19:23
 * 事务处理
 */
public interface DbAction {
    boolean doTrans() throws Exception;
}
