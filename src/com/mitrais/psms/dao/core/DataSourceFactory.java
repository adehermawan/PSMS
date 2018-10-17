package com.mitrais.psms.dao.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceFactory {
	private final DataSource daso;

    DataSourceFactory()
    {
        MysqlDataSource daso = new MysqlDataSource();
        String rootPath =
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("database.properties")).getPath();

        try (InputStream input = new FileInputStream(rootPath)){
            Properties prop = new Properties();
            prop.load(input);

            daso.setDatabaseName(prop.getProperty("database"));
            daso.setServerName(prop.getProperty("serverName"));
            daso.setPort(Integer.parseInt(prop.getProperty("port")));
            daso.setUser(prop.getProperty("user"));
            daso.setPassword(prop.getProperty("password"));
/*
            daso.setDatabaseName("psmsdb");
            daso.setServerName("localhost");
            daso.setPort(3306);
            daso.setUser("root");
            daso.setPassword("");
            */
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Other IO error");
            e.printStackTrace();
        }

        this.daso = daso;
    }

    
    public static Connection getConnection() throws SQLException
    {
        return SingletonHelper.INSTANCE.daso.getConnection();
    }

    private static class SingletonHelper
    {
        private static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }
}
