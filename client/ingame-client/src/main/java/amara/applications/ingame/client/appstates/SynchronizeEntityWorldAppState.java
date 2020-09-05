/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import java.util.Collections;
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
public class SynchronizeEntityWorldAppState extends BaseDisplayAppState<IngameClientApplication> {

    private ConcurrentLinkedQueue<EntityChange> pendingInitialEntityChanges = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<EntityChange> pendingEntityChanges = new ConcurrentLinkedQueue<>();
    private boolean isInitialWorldLoaded;
    private int initialChangesCount = -1;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        IngameNetworkAppState ingameNetworkAppState = getAppState(IngameNetworkAppState.class);
        PlayerAppState playerAppState = getAppState(PlayerAppState.class);
        ingameNetworkAppState.addMessageBackend(new EntitySynchronizeBackend(this, playerAppState));
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        ConcurrentLinkedQueue<EntityChange> entityChangesQueue;
        int changesToApply;
        if (isInitialWorldLoaded) {
            entityChangesQueue = pendingEntityChanges;
            changesToApply = pendingEntityChanges.size();
        } else {
            entityChangesQueue = pendingInitialEntityChanges;
            changesToApply = Math.min(pendingInitialEntityChanges.size(), 100);
            // Update loading screen
            int percentProgress = 0;
            if (initialChangesCount != -1) {
                percentProgress = (int) ((1 - (((float) pendingInitialEntityChanges.size()) / initialChangesCount)) * 100);
            }
            getAppState(LoadingScreenAppState.class).setTitle("Loading game data... (" + percentProgress + "%)");
        }
        EntityWorld entityWorld = getAppState(LocalEntitySystemAppState.class).getEntityWorld();
        for (int i = 0; i < changesToApply; i++) {
            EntityChange entityChange = entityChangesQueue.poll();
            if (entityChange instanceof RemovedEntityChange) {
                RemovedEntityChange removedEntityChange = (RemovedEntityChange) entityChange;
                entityWorld.removeEntity(removedEntityChange.getEntity());
            } else if (entityChange instanceof NewComponentChange) {
                NewComponentChange newComponentChange = (NewComponentChange) entityChange;
                entityWorld.setComponent(newComponentChange.getEntity(), newComponentChange.getComponent());
            } else if (entityChange instanceof RemovedComponentChange) {
                RemovedComponentChange removedComponentChange = (RemovedComponentChange) entityChange;
                entityWorld.removeComponent(removedComponentChange.getEntity(), removedComponentChange.getComponentClass());
            } else if(entityChange instanceof InitialEntityWorldLoadedChange) {
                getAppState(LocalEntitySystemAppState.class).onInitialWorldLoaded();
                getAppState(PlayerAppState.class).onInitialWorldLoaded();
                getAppState(LoadingScreenAppState.class).onInitialWorldLoaded();
                isInitialWorldLoaded = true;
                NetworkClient networkClient = mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
                networkClient.sendMessage(new Message_ClientReady());
            }
        }
    }

    public void enqueueEntityChanges(EntityChanges entityChanges) {
        ConcurrentLinkedQueue<EntityChange> entityChangesQueue = ((initialChangesCount == -1) ? pendingInitialEntityChanges : pendingEntityChanges);
        Collections.addAll(entityChangesQueue, entityChanges.getChanges());
    }

    public void onInitialEntityWorldReceived() {
        initialChangesCount = pendingInitialEntityChanges.size();
        pendingInitialEntityChanges.add(new InitialEntityWorldLoadedChange());
    }

    private class InitialEntityWorldLoadedChange extends EntityChange {

    }
}
