/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.database.databases;

import amara.libraries.database.Database;
import java.util.regex.Pattern;

/**
 *
 * @author Carl
 */
public class HSQLDatabase extends Database{

    public HSQLDatabase(String path, String user, String password){
        super("hsqldb", path, user, password);
    }

    @Override
    protected String prepareScriptQuery(String query){
        query = super.prepareScriptQuery(query);
        query = query.replaceAll(Pattern.quote("'{}'"), "ARRAY[]");
        return query;
    }
    
    @Override
    public String prepareArray(int[] array){
        String text = "ARRAY[";
        for(int i=0;i<array.length;i++){
            if(i != 0){
                text += ",";
            }
            text += array[i];
        }
        text += "]";
        return text;
    }
}
