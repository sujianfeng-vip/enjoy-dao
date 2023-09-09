package vip.sujianfeng.enjoydao.condition.support;

import vip.sujianfeng.enjoydao.condition.utils.ReflectUtil;
import vip.sujianfeng.enjoydao.condition.utils.lambda.SFunction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author Xiao-Bai
 * createTime 2022/8/11 2:07
 */
public interface ColumnParseHandler<T> {


    Class<T> getThisClass();

   default List<Field> loadFields() {
       Class<T> thisClass = getThisClass();
       return ReflectUtil.loadFields(thisClass);
   }

   default List<String> parseToFields(List<SFunction<T, ?>> funcList) {
       List<String> parseFieldList = new ArrayList<>();
       for (SFunction<T, ?> func : funcList) {
           parseFieldList.add(this.parseToField(func));
       }
       return parseFieldList;
   }

    String parseToField(SFunction<T, ?> func);

   default List<String> parseToColumns(List<SFunction<T, ?>> funcList) {
       List<String> parseColumnList = new ArrayList<>();
       for (SFunction<T, ?> func : funcList) {
           parseColumnList.add(this.parseToColumn(func));
       }
       return parseColumnList;
   }

    String parseToColumn(SFunction<T, ?> func);

    String parseToNormalColumn(SFunction<T, ?> func);

}
