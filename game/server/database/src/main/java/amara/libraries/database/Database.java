/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Carl
 */
public abstract class Database{

    public Database(String subProtocolName, String path, String user, String password){
        try{
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection("jdbc:" + subProtocolName + ":" + path, user, password);
            System.out.println("Connected to database.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    private Connection connection;
    
    public void executeResourceScript(String resourcePath){
        InputStream resourceInputStream = getClass().getResourceAsStream(resourcePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceInputStream));
        try{
            String query;
            while((query = bufferedReader.readLine()) != null){
                query = prepareScriptQuery(query);
                executeQuery(query);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    protected String prepareScriptQuery(String query){
        return query;
    }
    
    public boolean executeQuery(String query){
        boolean result = false;
        try{
            Statement statement = connection.createStatement();
            result = statement.execute(query);
            statement.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public QueryResult getQueryResult(String query){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return new QueryResult(statement, resultSet);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public String escape(String text){
        return text.replaceAll("'", "\\'");
    }
    
    public abstract String prepareArray(int[] array);

    public void close(){
        try{
            connection.close();
            System.out.println("Connection to database closed.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
