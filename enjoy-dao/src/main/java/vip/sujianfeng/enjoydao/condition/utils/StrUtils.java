package vip.sujianfeng.enjoydao.condition.utils;

import vip.sujianfeng.enjoydao.condition.consts.Constants;

import java.util.Locale;
import java.util.UUID;

/**
 * author Xiao-Bai
 * createTime 2022/9/2 0:44
 */
public class StrUtils {

    public static String getDataBase(String url) {
        int lastIndex =  url.lastIndexOf("/");
        boolean is = url.indexOf("?") > 0;
        if (is) {
            return url.substring(lastIndex + 1, url.indexOf("?"));
        } else {
            return url.substring(url.lastIndexOf("/") + Constants.DEFAULT_ONE);
        }
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase(Locale.CHINA);
    }


    public static String reverse(String str) {
        Asserts.notEmpty(str);
        char[] chars = str.toCharArray();
        int n = chars.length - 1;
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[n - i];
            chars[n - i] = temp;
        }
        return new String(chars);
    }

    public static String firstTableName(String tableName) {

        char first = tableName.charAt(0);
        boolean isLine = false;
        String otherNameStr = tableName.substring(1);
        int upCosNum = (int) otherNameStr.chars().filter(op -> op >= 65 && op <= 90).count();
        if (upCosNum <= 0) {
            upCosNum = (int) otherNameStr.chars().filter(op -> op == 95).count();
            isLine = true;
        }
        char[] upCosNums = new char[upCosNum + 1];
        upCosNums[0] = first > 96 ? first : (char) (first + 32);

        char[] charArray = otherNameStr.toCharArray();
        int tmp = 0;
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (isLine) {
                if (c == 95) {
                    upCosNums[i - tmp + 1] = charArray[i + 1];
                } else {
                    tmp++;
                }
            } else {
                if (Character.isUpperCase(c)) {
                    upCosNums[i - tmp + 1] = (char) (charArray[i] + 32);
                } else {
                    tmp++;
                }
            }

        }
        return new String(upCosNums);
    }

    public static boolean isAllNumber(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isExistNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistMaxLetter(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllMaxLetter(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isExistMinLetter(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllMinLetter(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final  CharSequence cs) {
        return !isBlank(cs);
    }

    public static String toIndexLower(String text) {
        String res = Constants.EMPTY;
        if (JudgeUtil.isEmpty(text)) {
            return res;
        }
        String first = text.substring(0, 1).toLowerCase();
        return first + text.substring(1);
    }

    public static boolean isBlank(final CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int l = cs.length();
        if (l > 0) {
            for (int i = 0; i < l; i++) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(Constants.UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return Constants.EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == Constants.UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static int countStr(String str, String rex) {
        int num = 0;
        while (str.contains(rex)) {
            str = str.substring(str.indexOf(rex) + rex.length());
            num ++;
        }
        return num;
    }


    public static boolean startWith(CharSequence str, CharSequence prefix, boolean isIgnoreCase) {
        if (null != str && null != prefix) {
            return isIgnoreCase ? str.toString().toLowerCase().startsWith(prefix.toString().toLowerCase()) : str.toString().startsWith(prefix.toString());
        } else {
            return null == str && null == prefix;
        }
    }


    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true);
    }


    public static boolean endWith(CharSequence str, CharSequence suffix, boolean isIgnoreCase) {
        if (null != str && null != suffix) {
            return isIgnoreCase ? str.toString().toLowerCase().endsWith(suffix.toString().toLowerCase()) : str.toString().endsWith(suffix.toString());
        } else {
            return null == str && null == suffix;
        }
    }

    public static boolean endWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return endWith(str, suffix, true);
    }



}
