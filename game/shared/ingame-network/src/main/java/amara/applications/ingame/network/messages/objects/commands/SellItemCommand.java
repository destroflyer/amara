/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SellItemCommand extends Command{

    public SellItemCommand(){
        
    }

    public SellItemCommand(int inventoryIndex){
        this.inventoryIndex = inventoryIndex;
    }
    private int inventoryIndex;

    public int getInventoryIndex(){
        return inventoryIndex;
    }
}
