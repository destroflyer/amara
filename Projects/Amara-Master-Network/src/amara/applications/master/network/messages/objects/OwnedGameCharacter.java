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
public class OwnedGameCharacter{

    public OwnedGameCharacter(){
        
    }
    
    public OwnedGameCharacter(GameCharacter character, int activeSkinID, int[] inventory){
        this.character = character;
        this.activeSkinID = activeSkinID;
        this.inventory = inventory;
    }
    private GameCharacter character;
    private int activeSkinID;
    private int[] inventory;

    public GameCharacter getCharacter(){
        return character;
    }

    public void setActiveSkinID(int activeSkinID){
        this.activeSkinID = activeSkinID;
    }

    public int getActiveSkinID(){
        return activeSkinID;
    }

    public void setInventory(int[] inventory){
        this.inventory = inventory;
    }

    public int[] getInventory(){
        return inventory;
    }
}
