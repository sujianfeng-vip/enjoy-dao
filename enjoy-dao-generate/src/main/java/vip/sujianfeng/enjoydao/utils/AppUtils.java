package vip.sujianfeng.enjoydao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.sujianfeng.utils.comm.ConvertUtils;
import vip.sujianfeng.utils.comm.StringUtilsEx;

/**
 * @Author SuJianFeng
 * @Date 2022/9/27
 * @Description
 **/
public class AppUtils {

    public static String getCurrProjectRoot() {
        String projectRoot = ConvertUtils.cStr(System.getProperties().getProperty("user.dir"));
        projectRoot = leftStr(projectRoot, "/output", "\\output", "/tools", "\\tools");
        logger.info("项目根目录：{}", projectRoot);
        return projectRoot;
    }

    private static String leftStr(String s, String... divs) {
        for (String div : divs) {
            if (s.contains(div)) {
                s = StringUtilsEx.leftStr(s, div);
            }
        }
        return s;
    }

    private static Logger logger = LoggerFactory.getLogger(AppUtils.class);
}
