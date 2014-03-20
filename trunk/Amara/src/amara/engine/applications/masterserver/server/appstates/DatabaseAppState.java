/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.appstates;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import amara.engine.applications.*;

/**
 *
 * @author Carl
 */
public class DatabaseAppState extends ServerBaseAppState{

    private Connection connection = null;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        try{
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection("jdbc:hsqldb:file:C:\\Databases\\Amara", "amara_admin", "");
            System.out.println("Connected to database.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        try{
            connection.close();
            System.out.println("Connection to database closed.");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public int getInteger(String query){
        try{
            ResultSet resultSet = getResultSet(query);
            if(resultSet.next()){
                int value = resultSet.getInt(1);
                resultSet.close();
                return value;
            }
            else{
                resultSet.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public long getLong(String query){
        try{
            ResultSet resultSet = getResultSet(query);
            if(resultSet.next()){
                long value = resultSet.getLong(1);
                resultSet.close();
                return value;
            }
            else{
                resultSet.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public String getString(String query){
        try{
            ResultSet resultSet = getResultSet(query);
            if(resultSet.next()){
                String value = resultSet.getString(1);
                resultSet.close();
                return value;
            }
            else{
                resultSet.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public ResultSet getResultSet(String query){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            statement.close();
            return resultSet;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public void executeQuery(String query){
        try{
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public static String escape(String text){
        return text.replaceAll("'", "\\'");
    }
}
