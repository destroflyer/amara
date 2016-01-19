/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.master.network.messages.objects.LobbyPlayer;

/**
 *
 * @author Carl
 */
public class GamePlayer{

    public GamePlayer(LobbyPlayer lobbyPlayer, int authentificationKey){
        this.lobbyPlayer = lobbyPlayer;
        this.authentificationKey = authentificationKey;
    }
    private LobbyPlayer lobbyPlayer;
    private int authentificationKey;
    private int clientID = -1;
    private int entityID;
    private boolean isInitialized;

    public LobbyPlayer getLobbyPlayer(){
        return lobbyPlayer;
    }

    public int getAuthentificationKey(){
        return authentificationKey;
    }

    public void setClientID(int clientID){
        this.clientID = clientID;
    }

    public int getClientID(){
        return clientID;
    }

    public void setEntityID(int entityID){
        this.entityID = entityID;
    }

    public int getEntityID(){
        return entityID;
    }

    public void setInitialized(boolean isInitialized){
        this.isInitialized = isInitialized;
    }

    public boolean isInitialized(){
        return isInitialized;
    }
}
