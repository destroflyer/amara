/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameInfo extends AbstractMessage {

    public Message_GameInfo() {
        
    }

    public Message_GameInfo(String mapName, GameSelectionPlayer[][] teams) {
        this.mapName = mapName;
        this.teams = teams;
    }
    private String mapName;
    private GameSelectionPlayer[][] teams;

    public String getMapName() {
        return mapName;
    }

    public GameSelectionPlayer[][] getTeams() {
        return teams;
    }

    // TODO: Check if still needed
    //[jME 3.1 SNAPSHOT] A nested array with length 0 isn't sent correctly
    public void repairOnUnserialize() {
        if (teams != null) {
            for (int i = 0; i < teams.length; i++) {
                GameSelectionPlayer[] team = teams[i];
                if ((team.length > 0) && (team[0] == null)) {
                    teams[i] = new GameSelectionPlayer[0];
                }
            }
        }
    }
}
