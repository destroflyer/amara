/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.database.databases;

import amara.libraries.database.Database;

/**
 *
 * @author Carl
 */
public class MySQLDatabase extends Database {

    public MySQLDatabase(String path, String user, String password) {
        super("mysql", path, user, password);
    }
}
