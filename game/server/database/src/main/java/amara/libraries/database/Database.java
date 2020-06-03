/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Carl
 */
public abstract class Database {

    public Database(String path, String user, String password) {
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(getConnectionUrl(path), user, password);
            System.out.println("Connected to database.");
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    private Connection connection;

    protected abstract String getConnectionUrl(String path);

    public boolean executeQuery(String query) {
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            result = statement.execute(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public QueryResult getQueryResult(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return new QueryResult(statement, resultSet);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String escape(String text) {
        return text.replaceAll("'", "\\'");
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Connection to database closed.");
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
