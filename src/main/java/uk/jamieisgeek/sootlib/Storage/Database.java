package uk.jamieisgeek.sootlib.Storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/**
 * @Author JamieIsGeek
 */
public class Database {
    private final HikariDataSource dataSource;

    private final String address;
    private final String databaseName;
    private final String username;
    private final String password;
    private final String port;

    /**
     * @param address The IP Address of the database
     * @param database The name of the database
     * @param username The username for the Database user
     * @param password The password for the Database user
     * @param port The port of the database server
     */

    public Database(String address, String database, String username, String password, String port) {
        this.address = address;
        this.databaseName = database;
        this.username = username;
        this.password = password;
        this.port = port;

        this.dataSource = createDataSource();
    }

    /**
     * @return The HikariDataSource
     */

    private HikariDataSource createDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s?autoReconnect=true&useSSL=false", address, port, databaseName));
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            return new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return The Connection to the database
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}