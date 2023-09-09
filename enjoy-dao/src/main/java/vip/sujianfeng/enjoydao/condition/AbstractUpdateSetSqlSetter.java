package vip.sujianfeng.enjoydao.condition;

import vip.sujianfeng.enjoydao.condition.utils.Asserts;
import vip.sujianfeng.enjoydao.condition.utils.CustomUtil;
import vip.sujianfeng.enjoydao.condition.utils.DbUtil;
import vip.sujianfeng.enjoydao.condition.utils.JudgeUtil;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.util.function.Consumer;

/**
 * author Xiao-Bai
 * createTime 2022/6/27 0027 12:01
 */
public abstract class AbstractUpdateSetSqlSetter<T, Children> extends UpdateSetWrapper<T> {


    protected abstract Children getInstance();
    @SuppressWarnings("unchecked")
    protected final Children childrenClass = (Children) this;


    protected Children addSetSql(boolean condition, String column, Object val) {
        if (condition) {
            Asserts.notNull(column, "column cannot be null");
            this.addSqlSetter(DbUtil.formatSqlCondition(column));
            Asserts.illegal(!CustomUtil.isBasicType(val),
                    String.format("Parameter types of type '%s' are not supported", val.getClass()));
            this.addParams(val);
        }
        return childrenClass;
    }

    protected Children addSetSql(boolean condition, SFunction<T, ?> column, Object val) {
        String originColumn = this.getColumnParseHandler().parseToColumn(column);
        return this.addSetSql(condition, originColumn, val);
    }


    protected AbstractUpdateSetSqlSetter(Class<T> entityClass) {
        super(entityClass);
    }

    protected Children addSetSqlString(boolean condition, String setSql, Object... params) {
        if (condition) {
            this.addSqlSetter(setSql);
            if (JudgeUtil.isNotEmpty(params)) {
                for (Object param : params) {
                    Asserts.illegal(!CustomUtil.isBasicType(param),
                            String.format("Parameter types of type '%s' are not supported", param.getClass()));
                    this.addParams(param);
                }
            }
        }
        return childrenClass;
    }

    @SuppressWarnings("unchecked")
    protected Children consumerSet(boolean condition, Consumer<Children> consumer) {
        if (condition) {
            Children instance = getInstance();
            consumer.accept(instance);
            AbstractUpdateSetSqlSetter<T, Children> thisSqlSetter = (AbstractUpdateSetSqlSetter<T, Children>) instance;
            this.addParams(thisSqlSetter.getSetParams());
            this.addSqlSetter(thisSqlSetter.getSqlSetter());
        }
        return childrenClass;
    }
}
