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
    
    public GameCharacter(int id, String name, String title, String lore, boolean isPublic, GameCharacterSkin[] skins){
        this.id = id;
        this.name = name;
        this.title = title;
        this.lore = lore;
        this.isPublic = isPublic;
        this.skins = skins;
    }
    private int id;
    private String name;
    private String title;
    private String lore;
    private boolean isPublic;
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

    public String getLore(){
        return lore;
    }

    public boolean isPublic(){
        return isPublic;
    }

    public GameCharacterSkin[] getSkins(){
        return skins;
    }
}
