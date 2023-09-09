package vip.sujianfeng.enjoydao.condition.utils.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * author Xiao-Bai
 * createTime 2022/3/3 14:36
 **/
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
