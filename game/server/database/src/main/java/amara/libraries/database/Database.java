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
        this.path = path;
        this.user = user;
        this.password = password;
    }
    private String path;
    private String user;
    private String password;
    private Connection connection;

    public boolean executeQuery(String query) {
        boolean result = false;
        try {
            ensureConnection();
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
            ensureConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return new QueryResult(statement, resultSet);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void ensureConnection() throws SQLException {
        if ((connection != null) && connection.isClosed()) {
            System.out.println("Connection closed, trying to reconnect.");
            connection = null;
        }
        if (connection == null) {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(getConnectionUrl(path), user, password);
            System.out.println("Connected to database.");
        }
    }

    protected abstract String getConnectionUrl(String path);

    public void close() {
        try {
            connection.close();
            System.out.println("Connection to database closed.");
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String escape(String text) {
        return text.replaceAll("'", "\\'");
    }
}
