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
public class LobbyPlayerData{

    public LobbyPlayerData(){
        
    }
    
    public LobbyPlayerData(String unitTemplate){
        this.unitTemplate = unitTemplate;
    }
    private String unitTemplate;

    public String getUnitTemplate(){
        return unitTemplate;
    }
}
