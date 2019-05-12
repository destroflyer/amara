/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class OwnedItem{

    public OwnedItem(){
        
    }
    
    public OwnedItem(Item item, int amount){
        this.item = item;
        this.amount = amount;
    }
    private Item item;
    private int amount;

    public Item getItem(){
        return item;
    }

    public int getAmount(){
        return amount;
    }
}
