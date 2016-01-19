/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.OwnedItem;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_OwnedItems extends AbstractMessage{
    
    public Message_OwnedItems(){
        
    }
    
    public Message_OwnedItems(OwnedItem[] ownedItems){
        this.ownedItems = ownedItems;
    }
    private OwnedItem[] ownedItems;

    public OwnedItem[] getOwnedItems(){
        return ownedItems;
    }
}
