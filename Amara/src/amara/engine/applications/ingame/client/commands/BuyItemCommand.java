/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BuyItemCommand extends Command{

    public BuyItemCommand(){
        
    }

    public BuyItemCommand(String itemID){
        this.itemID = itemID;
    }
    private String itemID;

    public String getItemID(){
        return itemID;
    }
}
