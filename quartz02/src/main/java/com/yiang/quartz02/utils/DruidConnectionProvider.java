package com.yiang.quartz02.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.quartz.SchedulerException;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/*
#============================================================================
# JDBC
#============================================================================
org.quartz.jobStore.driverDelegateClass:org.quartz.impl.jdbcjobstore.StdJDBCDelegate
org.quartz.jobStore.useProperties:false
org.quartz.jobStore.dataSource:qzDS
#org.quartz.dataSource.qzDS.connectionProvider.class:org.quartz.utils.PoolingConnectionProvider
org.quartz.dataSource.qzDS.connectionProvider.class:com.javaxl.q03.quartz.DruidConnectionProvider
org.quartz.dataSource.qzDS.driver:com.mysql.jdbc.Driver
org.quartz.dataSource.qzDS.URL:jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8
org.quartz.dataSource.qzDS.user:root
org.quartz.dataSource.qzDS.password:root
org.quartz.dataSource.qzDS.maxConnections:30
org.quartz.dataSource.qzDS.validationQuery: select 0
*/

/**
 * [Druid连接池的Quartz扩展类]
 *
 * @ProjectName: []
 * @Author: [xuguang]
 * @CreateDate: [2015/11/10 17:58]
 * @Update: [说明本次修改内容] BY[xuguang][2015/11/10]
 * @Version: [v1.0]
 */
public class DruidConnectionProvider implements ConnectionProvider {

     /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * 常量配置，与quartz.properties文件的key保持一致(去掉前缀)，同时提供set方法，Quartz框架自动注入值。
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    //JDBC驱动
    public String driver;
    //JDBC连接串
    public String URL;
    //数据库用户名
    public String user;
    //数据库用户密码
    public String password;
    //数据库最大连接数
    public int maxConnection;
    //数据库SQL查询每次连接返回执行到连接池，以确保它仍然是有效的。
    public String validationQuery;

    private boolean validateOnCheckout;

    private int idleConnectionValidationSeconds;

    public String maxCachedStatementsPerConnection;

    private String discardIdleConnectionsSeconds;

    public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;

    public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;

    //Druid连接池
    private DruidDataSource datasource;

    /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *
    * 接口实现
    *
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public void shutdown() throws SQLException {
        datasource.close();
    }
    public void initialize() throws SQLException{
        if (this.URL == null) {
            throw new SQLException("DBPool could not be created: DB URL cannot be null");
        }

        if (this.driver == null) {
            throw new SQLException("DBPool driver could not be created: DB driver class name cannot be null!");
        }

        if (this.maxConnection < 0) {
            throw new SQLException("DBPool maxConnectins could not be created: Max connections must be greater than zero!");
        }

        datasource = new DruidDataSource();
        try{
            datasource.setDriverClassName(this.driver);
        } catch (Exception e) {
            try {
                throw new SchedulerException("Problem setting driver class name on datasource: " + e.getMessage(), e);
            } catch (SchedulerException e1) {
            }
        }

        datasource.setUrl(this.URL);
        datasource.setUsername(this.user);
        datasource.setPassword(this.password);
        datasource.setMaxActive(this.maxConnection);
        datasource.setMinIdle(1);
        datasource.setMaxWait(0);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(this.DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION);

        if (this.validationQuery != null) {
            datasource.setValidationQuery(this.validationQuery);
            if(!this.validateOnCheckout)
                datasource.setTestOnReturn(true);
            else
                datasource.setTestOnBorrow(true);
            datasource.setValidationQueryTimeout(this.idleConnectionValidationSeconds);
        }
    }

    /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *
    * 提供get set方法
    *
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
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

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isValidateOnCheckout() {
        return validateOnCheckout;
    }

    public void setValidateOnCheckout(boolean validateOnCheckout) {
        this.validateOnCheckout = validateOnCheckout;
    }

    public int getIdleConnectionValidationSeconds() {
        return idleConnectionValidationSeconds;
    }

    public void setIdleConnectionValidationSeconds(int idleConnectionValidationSeconds) {
        this.idleConnectionValidationSeconds = idleConnectionValidationSeconds;
    }

    public DruidDataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DruidDataSource datasource) {
        this.datasource = datasource;
    }
}