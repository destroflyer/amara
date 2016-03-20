/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.games;

import amara.applications.master.network.messages.objects.GameSelectionPlayer;

/**
 *
 * @author Carl
 */
public class GamePlayer{

    public GamePlayer(GameSelectionPlayer gameSelectionPlayer, int authentificationKey){
        this.gameSelectionPlayer = gameSelectionPlayer;
        this.authentificationKey = authentificationKey;
    }
    private GameSelectionPlayer gameSelectionPlayer;
    private int authentificationKey;
    private int clientID = -1;
    private int entity;
    private boolean isInitialized;

    public GameSelectionPlayer getGameSelectionPlayer(){
        return gameSelectionPlayer;
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

    public void setEntity(int entity){
        this.entity = entity;
    }

    public int getEntity(){
        return entity;
    }

    public void setInitialized(boolean isInitialized){
        this.isInitialized = isInitialized;
    }

    public boolean isInitialized(){
        return isInitialized;
    }
}
