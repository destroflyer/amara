/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameCharacterSkin{

    public GameCharacterSkin(){
        
    }
    
    public GameCharacterSkin(int id, String title){
        this.id = id;
        this.title = title;
    }
    private int id;
    private String title;

    public int getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }
}
