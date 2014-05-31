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
public class LobbyData{

    public LobbyData(){
        
    }

    public LobbyData(String mapName){
        this.mapName = mapName;
    }
    private String mapName;

    public String getMapName(){
        return mapName;
    }
}
