/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.protocol;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class GameCharacter{

    public GameCharacter(){
        
    }
    
    public GameCharacter(int id, String title, GameCharacterSkin[] skins){
        this.id = id;
        this.title = title;
        this.skins = skins;
    }
    private int id;
    private String title;
    private GameCharacterSkin[] skins;

    public int getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public GameCharacterSkin[] getSkins(){
        return skins;
    }
}
