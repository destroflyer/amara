/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.database;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Carl
 */
public class QueryResult{

    public QueryResult(Statement statement, ResultSet resultSet){
        this.statement = statement;
        this.resultSet = resultSet;
    }
    private Statement statement;
    private ResultSet resultSet;
    
    public Integer nextInteger_Close(){
        if(next()){
            int value = getInteger(1);
            close();
            return value;
        }
        return null;
    }
    
    public Long nextLong_Close(){
        if(next()){
            long value = getLong(1);
            close();
            return value;
        }
        return null;
    }
    
    public String nextString_Close(){
        if(next()){
            String value = getString(1);
            close();
            return value;
        }
        return null;
    }
    
    public boolean next(){
        try{
            return resultSet.next();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    //Integer
    
    public int getInteger(int columnIndex){
        int value = 0;
        try{
            value = resultSet.getInt(columnIndex);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    public int getInteger(String columnName){
        int value = 0;
        try{
            value = resultSet.getInt(columnName);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    //Long
    
    public long getLong(int columnIndex){
        long value = 0;
        try{
            value = resultSet.getLong(columnIndex);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    public long getLong(String columnName){
        long value = 0;
        try{
            value = resultSet.getLong(columnName);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    //String
    
    public String getString(int columnIndex){
        String value = null;
        try{
            value = resultSet.getString(columnIndex);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    public String getString(String columnName){
        String value = null;
        try{
            value = resultSet.getString(columnName);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    //Array
    
    public Array getArray(int columnIndex){
        Array value = null;
        try{
            value = resultSet.getArray(columnIndex);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    public Array getArray(String columnName){
        Array value = null;
        try{
            value = resultSet.getArray(columnName);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return value;
    }
    
    public void close(){
        try{
            resultSet.close();
            statement.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
