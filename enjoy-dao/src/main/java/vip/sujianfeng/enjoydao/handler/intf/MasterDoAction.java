package vip.sujianfeng.enjoydao.handler.intf;

import vip.sujianfeng.utils.define.CallResult;

/**
 * author SuJianFeng
 * createTime 2022/9/21
 * description
 **/
public interface MasterDoAction<T> {
    void proc(CallResult<T> op) throws Exception;
}
