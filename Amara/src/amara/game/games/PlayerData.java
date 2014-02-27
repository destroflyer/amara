/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.games;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PlayerData{

    public PlayerData(){
        
    }
    
    public PlayerData(int id, String unitTemplate){
        this.id = id;
        this.unitTemplate = unitTemplate;
    }
    private int id;
    private String unitTemplate;

    public int getID(){
        return id;
    }

    public String getUnitTemplate(){
        return unitTemplate;
    }
}
