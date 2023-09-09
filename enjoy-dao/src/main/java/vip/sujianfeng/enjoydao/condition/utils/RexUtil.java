package vip.sujianfeng.enjoydao.condition.utils;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author Xiao-Bai
 * createTime 2022/4/8 15:02
 **/
public class RexUtil {

    /**
     * Match single quotation marks
     */
    public final static String single_quotes = "(?<=').*?(?=')";
    
    /**
     * Match anti single quotation marks
     */
    public final static String back_quotes = "(?<=`).*?(?=`)";

    /**
     * Match double quotes
     */
    public final static String double_quotes = "(?<=\").*?(?=\")";

    /**
     * Match mybatis parameter format
     */
    public final static String sql_set_param = "\\#\\{(.+?)\\}";
    public final static String sql_rep_param = "\\$\\{(.+?)\\}";

    /**
     * Match SQL ifnull function
     */
    public final static String sql_if_null = "ifnull(.+?)$";

    /**
     * Match custom formatting settings
     */
    public final static String custom_format = "\\{(.+?)\\}";

    /**
     * Is the match a number, decimal, or negative number
     */
    public final static String check_number = "-?[0-9]+.?[0-9]*";


    //Returns the first string that meets the regular condition
    public static String regexStr(String str, String regex) {
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    //Replace strings that match the regular (only replace the first occurrence)
    //this is {name} -> this is {zhangsan}
    public static String replaceStr(String str, String regex, String replaceStr) {
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        if (matcher.find()) {
            return matcher.replaceFirst(replaceStr);
        }
        return str;
    }

    //Replace string that matches regular (replace all)
    //this is {name} -> this is zhangsan
    public static String replaceStr(String str, String regex, String oldStr, String replaceStr) {
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            if (oldStr.equals(matcher.group(1))) {
                matcher.appendReplacement(sb, JudgeUtil.isEmpty(replaceStr) ? Constants.EMPTY : replaceStr);
                 return matcher.appendTail(sb).toString();
            }
        }
        return str;
    }

    //Replace string that matches regular (replace first occurrence character)
    //this is {name} and {name} years -> this is {zahngsan} and {zahngsan}
    public static String replaceAllRex(String str, String regex, String replaceStr) {
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()) {
           str = matcher.replaceFirst(replaceStr);
        }
        return str;
    }


    //Replace strings that match the regular (replace all that match the criteria)
    //this is {name} and {age} years -> this is zahngsan and 18
    public static String replaceAllRex(String str, String regex, String oldStr, String replaceStr) {
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            if (key.equals(oldStr)) {
                matcher.appendReplacement(sb, JudgeUtil.isEmpty(replaceStr) ? Constants.EMPTY : replaceStr);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // Match or not
    public static boolean hasRegex(String str, String regex) {
        Pattern pattern= Pattern.compile(regex);
        return pattern.matcher(str).find();
    }
}
