/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.core.files.FileManager;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.BaseHeadlessAppState;
import amara.libraries.database.Database;
import amara.libraries.database.QueryResult;
import amara.libraries.database.databases.MySQLDatabase;

/**
 *
 * @author Carl
 */
public class DatabaseAppState extends BaseHeadlessAppState {

    private Database database;

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        String[] credentials = FileManager.getFileLines("./db_key_to_the_city.ini");
        database = new MySQLDatabase("//localhost:3306/amara", credentials[0], credentials[1]);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        database.close();
    }

    public boolean executeQuery(String query) {
        return database.executeQuery(query);
    }

    public QueryResult getQueryResult(String query) {
        return database.getQueryResult(query);
    }

    public String escape(String text) {
        return database.escape(text);
    }
}
