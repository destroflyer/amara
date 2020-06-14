/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import amara.applications.master.network.messages.objects.GameSelectionPlayerData;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_JoinMMOMap extends AbstractMessage {

    public Message_JoinMMOMap() {

    }

    public Message_JoinMMOMap(String mapName, GameSelectionPlayerData gameSelectionPlayerData) {
        this.mapName = mapName;
        this.gameSelectionPlayerData = gameSelectionPlayerData;
    }
    private String mapName;
    private GameSelectionPlayerData gameSelectionPlayerData;

    public String getMapName() {
        return mapName;
    }

    public GameSelectionPlayerData getGameSelectionPlayerData() {
        return gameSelectionPlayerData;
    }
}
