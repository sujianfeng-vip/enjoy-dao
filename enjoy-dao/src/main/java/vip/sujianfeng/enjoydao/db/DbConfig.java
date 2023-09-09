package vip.sujianfeng.enjoydao.db;

/**
 * author SuJianFeng
 * createTime 2019/5/27 15:05
 **/
public class DbConfig {

    private String driverName;
    private String url;
    private String user;
    private String password;
    private String dbName;
    private int initialSize = 0;
    private int maxActive = 500;
    private int minIdle = 0;
    private int maxWait = -1;


    public String toKey(){
        return String.format("%s/%s/%s/%s", driverName, url, user, password);
    }


    public DbConfig(){

    }

    public DbConfig(String driverName, String url, String user, String password){
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
