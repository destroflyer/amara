/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.games;

/**
 *
 * @author Carl
 */
public class GamePlayer{

    public GamePlayer(PlayerData playerData, int authentificationKey){
        this.playerData = playerData;
        this.authentificationKey = authentificationKey;
    }
    private PlayerData playerData;
    private int authentificationKey;
    private int entityID;

    public PlayerData getPlayerData(){
        return playerData;
    }

    public int getAuthentificationKey(){
        return authentificationKey;
    }

    public void setEntityID(int entityID){
        this.entityID = entityID;
    }

    public int getEntityID(){
        return entityID;
    }
}
