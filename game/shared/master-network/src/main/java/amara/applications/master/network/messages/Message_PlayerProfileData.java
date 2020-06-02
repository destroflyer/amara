/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.PlayerProfileData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_PlayerProfileData extends AbstractMessage{
    
    public Message_PlayerProfileData(){
        
    }
    
    public Message_PlayerProfileData(PlayerProfileData playerProfileData){
        this.playerProfileData = playerProfileData;
    }
    private PlayerProfileData playerProfileData;

    public PlayerProfileData getPlayerProfileData(){
        return playerProfileData;
    }
}
