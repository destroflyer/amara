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

    public GamePlayer(int id, int authentificationKey){
        this.id = id;
        this.authentificationKey = authentificationKey;
    }
    private int id;
    private int authentificationKey;
    private int entityID;

    public int getID(){
        return id;
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
