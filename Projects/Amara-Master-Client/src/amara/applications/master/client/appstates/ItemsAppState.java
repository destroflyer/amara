/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import amara.applications.master.client.network.backends.*;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class ItemsAppState extends ClientBaseAppState{

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceiveItemsBackend(this));
        networkClient.addMessageBackend(new ReceiveOwnedItemsBackend(this));
    }
    private Item[] items;
    private OwnedItem[] ownedItems;

    public void setItems(Item[] items){
        this.items = items;
    }
    
    public Item getItem(int itemID){
        for(Item item : items){
            if(item.getID() == itemID){
                return item;
            }
        }
        return null;
    }

    public Item[] getItems(){
        return items;
    }

    public void setOwnedItems(OwnedItem[] ownedItems){
        this.ownedItems = ownedItems;
    }

    public OwnedItem[] getOwnedItems(){
        return ownedItems;
    }
}
