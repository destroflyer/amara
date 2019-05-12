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
public class PostgreDatabase extends Database{

    public PostgreDatabase(String path, String user, String password){
        super("postgresql", path, user, password);
    }
    
    @Override
    public String prepareArray(int[] array){
        String text = "'{";
        for(int i=0;i<array.length;i++){
            if(i != 0){
                text += ",";
            }
            text += array[i];
        }
        text += "}'";
        return text;
    }
}
