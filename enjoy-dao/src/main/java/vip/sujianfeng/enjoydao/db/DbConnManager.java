package vip.sujianfeng.enjoydao.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author SuJianFeng
 * createTime 2019/5/27 14:37
 **/
public class DbConnManager {

    private static Map<String, Map<String, Connection>> CONN_MAP = new ConcurrentHashMap<>();

    private static Connection getCacheConn(DbConfig dbConfig, String tag){
        Map<String, Connection> map = CONN_MAP.get(dbConfig.toKey());
        if (map == null){
            return null;
        }
        return map.get(tag);
    }

    public static void clearCacheConn(DbConfig dbConfig, String tag){
        Map<String, Connection> map = CONN_MAP.get(dbConfig.toKey());
        if (map != null){
            map.remove(tag);
        }
    }

    private static Connection setCacheConn(DbConfig dbConfig, String tag, Connection conn){
        Map<String, Connection> map = CONN_MAP.get(dbConfig.toKey());
        if (map == null){
            map = new HashMap<>();
            CONN_MAP.put(dbConfig.toKey(), map);
        }
        return map.put(tag, conn);
    }


    public static Connection getConn(DbConfig dbConfig, String tag) throws Exception {
        Connection result = getCacheConn(dbConfig, tag);
        if (result != null){
            return result;
        }
        result = DbUtils.buildConn(dbConfig);
        setCacheConn(dbConfig, tag, result);
        System.out.println(String.format("\n================================\n%s\nconnect size:%s\ncurr tag: %s\n================================\n",
                dbConfig.toKey(), CONN_MAP.get(dbConfig.toKey()).values().size(), tag));
        return result;
    }

}
