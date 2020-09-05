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
public class GameSelectionData{

    public GameSelectionData(){
        
    }

    public GameSelectionData(String mapName, TeamFormat teamFormat){
        this.mapName = mapName;
        this.teamFormat = teamFormat;
    }
    private String mapName;
    private TeamFormat teamFormat;

    public String getMapName(){
        return mapName;
    }

    public TeamFormat getTeamFormat(){
        return teamFormat;
    }
}
