/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.BaseHeadlessAppState;
import amara.libraries.database.*;
import amara.libraries.database.databases.*;

/**
 *
 * @author Carl
 */
public class DatabaseAppState extends BaseHeadlessAppState{

    private Database database;
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        //database = new HSQLDatabase("file:C:/Databases/Amara", "amara_admin", "");
        database = new PostgreDatabase("//localhost/amaradb", "amara_admin", "test");
        //executeScript("delete_tables");
        executeScript("create_tables");
        //executeScript("testdata");
    }

    @Override
    public void cleanup(){
        super.cleanup();
        database.close();
    }
    
    public void executeScript(String scriptName){
        System.out.println("Execute database script '" + scriptName + "'.");
        database.executeResourceScript("/scripts/" + scriptName + ".sql");
    }
    
    public boolean executeQuery(String query){
        return database.executeQuery(query);
    }
    
    public QueryResult getQueryResult(String query){
        return database.getQueryResult(query);
    }
    
    public String escape(String text){
        return database.escape(text);
    }
    
    public String prepareArray(int[] array){
        return database.prepareArray(array);
    }

    public Database getDatabase(){
        return database;
    }
}
