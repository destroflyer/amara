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
public class OwnedGameCharacter{

    public OwnedGameCharacter(){
        
    }
    
    public OwnedGameCharacter(GameCharacter character, int activeSkinID){
        this.character = character;
        this.activeSkinID = activeSkinID;
    }
    private GameCharacter character;
    private int activeSkinID;

    public GameCharacter getCharacter(){
        return character;
    }

    public void setActiveSkinID(int activeSkinID){
        this.activeSkinID = activeSkinID;
    }

    public int getActiveSkinID(){
        return activeSkinID;
    }
}
