package vip.sujianfeng.enjoydao.condition;

import java.util.function.Consumer;

/**
 * author Xiao-Bai
 * createTime 2022/8/6 16:28
 */
public interface UpdateSet<Children, Setter, Wrapper> {


    Children setter(boolean condition, Setter setter);

    default Children setter(Setter setter) {
        return setter(true, setter);
    }


    Children setter(boolean condition, Consumer<Setter> consumer);

    default Children setter(Consumer<Setter> consumer) {
        return setter(true, consumer);
    }


    Children where(boolean condition, Wrapper wrapper);

    default  Children where(Wrapper wrapper) {
        return where(true, wrapper);
    }

    Children where(boolean condition, Consumer<Wrapper> consumer);

    default  Children where(Consumer<Wrapper> consumer) {
        return where(true, consumer);
    }

}
