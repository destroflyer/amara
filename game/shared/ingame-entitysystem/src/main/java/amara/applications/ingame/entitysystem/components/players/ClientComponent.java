/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.players;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ClientComponent{

    public ClientComponent(){
        
    }

    public ClientComponent(int clientID){
        this.clientID = clientID;
    }
    private int clientID;

    public int getClientID(){
        return clientID;
    }
}
