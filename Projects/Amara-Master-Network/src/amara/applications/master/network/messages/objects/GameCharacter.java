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
public class GameCharacter{

    public GameCharacter(){
        
    }
    
    public GameCharacter(int id, String name, String title, GameCharacterSkin[] skins){
        this.id = id;
        this.name = name;
        this.title = title;
        this.skins = skins;
    }
    private int id;
    private String name;
    private String title;
    private GameCharacterSkin[] skins;

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }    

    public String getTitle(){
        return title;
    }

    public GameCharacterSkin[] getSkins(){
        return skins;
    }
}
