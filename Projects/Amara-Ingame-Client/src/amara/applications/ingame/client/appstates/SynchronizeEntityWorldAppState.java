/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.applications.ingame.client.IngameClientApplication;
import amara.applications.ingame.client.network.backends.EntitySynchronizeBackend;
import amara.applications.ingame.network.messages.Message_ClientReady;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class SynchronizeEntityWorldAppState extends BaseDisplayAppState<IngameClientApplication>{

    public SynchronizeEntityWorldAppState(){
        
    }
    private ConcurrentLinkedQueue<EntityChange> pendingEntityChanges = new ConcurrentLinkedQueue<EntityChange>();
    private int initialChangesCount = -1;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new EntitySynchronizeBackend(this));
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        int changesToApply = pendingEntityChanges.size();
        if(initialChangesCount != -1){
            changesToApply = Math.min(changesToApply, 100);
            int percentProgress = Math.max(0, (int) ((1 - (((float) pendingEntityChanges.size()) / initialChangesCount)) * 100));
            getAppState(LoadingScreenAppState.class).setTitle("Loading game data... (" + percentProgress + "%)");
        }
        for(int i=0;i<changesToApply;i++){
            EntityChange entityChange = pendingEntityChanges.poll();
            if(entityChange instanceof RemovedEntityChange){
                RemovedEntityChange removedEntityChange = (RemovedEntityChange) entityChange;
                entityWorld.removeEntity(removedEntityChange.getEntity());
            }
            else if(entityChange instanceof NewComponentChange){
                NewComponentChange newComponentChange = (NewComponentChange) entityChange;
                entityWorld.setComponent(newComponentChange.getEntity(), newComponentChange.getComponent());
            }
            else if(entityChange instanceof RemovedComponentChange){
                RemovedComponentChange removedComponentChange = (RemovedComponentChange) entityChange;
                entityWorld.removeComponent(removedComponentChange.getEntity(), removedComponentChange.getComponentClass());
            }
            else if(entityChange instanceof InitialEntityWorldLoadedChange){
                getAppState(LocalEntitySystemAppState.class).onInitialWorldLoaded();
                getAppState(PlayerAppState.class).onInitialWorldLoaded();
                getAppState(LoadingScreenAppState.class).onInitialWorldLoaded();
                initialChangesCount = -1;
                NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
                networkClient.sendMessage(new Message_ClientReady());
            }
        }
    }
    
    public void enqueueEntityChanges(EntityChanges entityChanges){
        for(EntityChange entityChange : entityChanges.getChanges()){
            pendingEntityChanges.add(entityChange);
        }
    }
    
    public void onInitialEntityWorldReceived(){
        initialChangesCount = pendingEntityChanges.size();
        pendingEntityChanges.add(new InitialEntityWorldLoadedChange());
    }
    
    private class InitialEntityWorldLoadedChange extends EntityChange{
        
    }
}
