/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

/**
 *
 * @author Carl
 */
public abstract class LobbyPlayer{
    
    private int lobbyPlayerID;

    public void setLobbyPlayerID(int lobbyPlayerID){
        this.lobbyPlayerID = lobbyPlayerID;
    }

    public int getLobbyPlayerID(){
        return lobbyPlayerID;
    }
}
