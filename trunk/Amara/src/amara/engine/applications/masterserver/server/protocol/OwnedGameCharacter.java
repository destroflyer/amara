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

    public int getActiveSkinID(){
        return activeSkinID;
    }

    @Override
    public String toString(){
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
