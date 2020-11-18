package com.geek.spring.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.lang.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @program: JAVA-000
 * @description: 获取链接工具，初始化就将两种方式的链接创建
 * @author: yarne
 * @create: 2020-11-18 16:19
 **/
public class ConnectUtil {
    static private String connectString = "jdbc:mysql://39.105.101.222:3306/jdbcdemo";
    static private String sql_user = "root";
    static private String sql_pwd = "tyn123456";
    //Hikari连接池
    static private HikariDataSource ds;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(connectString);
        config.setUsername(sql_user);
        config.setPassword(sql_pwd);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    /**
     * 获取池链接 0 hikari 1 原生
     * @param type
     * @return
     */
    @Nullable
    public static Connection getCon(String type) {
        try {
            if(null!=type&&"1".equals(type)){
                return DriverManager.getConnection(connectString, sql_user, sql_pwd);
            }
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
