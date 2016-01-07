/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import amara.engine.applications.*;
import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;

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
