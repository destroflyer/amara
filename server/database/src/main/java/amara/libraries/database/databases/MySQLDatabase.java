package amara.libraries.database.databases;

import amara.libraries.database.Database;

public class MySQLDatabase extends Database {

    public MySQLDatabase(String path, String user, String password) {
        super(path, user, password);
    }

    @Override
    protected String getConnectionUrl(String path) {
        return "jdbc:mysql:" + path + "?autoReconnect=true&serverTimezone=UTC";
    }
}
