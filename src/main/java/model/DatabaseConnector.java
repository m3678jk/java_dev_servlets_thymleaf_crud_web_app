package model;

import model.commandsDB.prefs.Prefs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final DatabaseConnector DATABASE_CONNECTOR = new DatabaseConnector();
    private Connection connection;
    private DatabaseConnector(){
        try{
            String dbUrl = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
            String dbUser = new Prefs().getString(Prefs.DB_JDBC_USER);
            String dbPass = new Prefs().getString(Prefs.DB_JDBC_PASSWORD);
                        connection = DriverManager.getConnection(dbUrl,dbUser,dbPass);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static DatabaseConnector getDatabaseConnector(){
        return DATABASE_CONNECTOR;
    }
    public Connection getConnection(){
        return connection;
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
