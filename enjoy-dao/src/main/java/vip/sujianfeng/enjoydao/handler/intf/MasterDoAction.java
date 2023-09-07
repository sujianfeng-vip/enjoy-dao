package vip.sujianfeng.enjoydao.handler.intf;

import vip.sujianfeng.utils.define.CallResult;

/**
 * @Author SuJianFeng
 * @Date 2022/9/21
 * @Description
 **/
public interface MasterDoAction<T> {
    void proc(CallResult<T> op) throws Exception;
}
