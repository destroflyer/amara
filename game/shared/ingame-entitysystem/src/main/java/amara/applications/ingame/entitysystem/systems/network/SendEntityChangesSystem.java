/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.entitysystem.synchronizing.ClientComponentBlacklist;
import amara.applications.ingame.network.messages.*;
import amara.core.Util;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class SendEntityChangesSystem implements EntitySystem {

    public SendEntityChangesSystem(SubNetworkServer subNetworkServer, List<Integer> initializedClientIds, ClientComponentBlacklist clientComponentBlacklist) {
        this.subNetworkServer = subNetworkServer;
        this.initializedClientIds = initializedClientIds;
        this.clientComponentBlacklist = clientComponentBlacklist;
    }
    private SubNetworkServer subNetworkServer;
    private List<Integer> initializedClientIds;
    private ClientComponentBlacklist clientComponentBlacklist;
    public static ComponentEqualityDefinition COMPONENT_EQUALITY_DEFINITION = new DefaultComponentEqualityDefinition() {

        @Override
        public boolean areComponentsEqual(Object oldComponent, Object newComponent) {
            if ((newComponent instanceof RestartClientAnimationComponent)
             || (newComponent instanceof StartPlayingAudioComponent)
             || (newComponent instanceof StopPlayingAudioComponent)) {
                return false;
            }
            return super.areComponentsEqual(oldComponent, newComponent);
        }
    };
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        LinkedList<EntityChange> changes = new LinkedList<>();
        ComponentMapObserver componentsObserver = entityWorld.requestObserver(this, COMPONENT_EQUALITY_DEFINITION);
        for (int entity : componentsObserver.getNew().getEntitiesWithAll()) {
            for (Object component : componentsObserver.getNew().getComponents(entity)){
                if (!clientComponentBlacklist.contains(ClientComponentBlacklist.ChangeType.NEW, component.getClass())){
                    changes.add(new NewComponentChange(entity, component));
                }
            }
        }
        for (int entity : componentsObserver.getChanged().getEntitiesWithAll()) {
            for (Object component : componentsObserver.getChanged().getComponents(entity)) {
                if (!clientComponentBlacklist.contains(ClientComponentBlacklist.ChangeType.CHANGED, component.getClass())) {
                    changes.add(new NewComponentChange(entity, component));
                }
            }
        }
        for (int entity : componentsObserver.getRemoved().getEntitiesWithAll()) {
            if (entityWorld.hasEntity(entity)) {
                for (Object component : componentsObserver.getRemoved().getComponents(entity)) {
                    if (!clientComponentBlacklist.contains(ClientComponentBlacklist.ChangeType.REMOVED, component.getClass())) {
                        changes.add(new RemovedComponentChange(entity, component.getClass()));
                    }
                }
            } else {
                for (Object component : componentsObserver.getRemoved().getComponents(entity)) {
                    if (!clientComponentBlacklist.contains(ClientComponentBlacklist.ChangeType.REMOVED, component.getClass())) {
                        changes.add(new RemovedEntityChange(entity));
                        break;
                    }
                }
            }
        }
        Message_EntityChanges[] entityChangeMessages = getEntityChangesMessages(changes);
        for (Message_EntityChanges entityChangeMessage : entityChangeMessages) {
            subNetworkServer.sendMessageToClients(initializedClientIds, entityChangeMessage);
        }
    }
    
    public static Message_EntityChanges[] getEntityChangesMessages(LinkedList<EntityChange> changes) {
        LinkedList<EntityChange[]> splitChanges = Util.split(changes, 1000, EntityChange.class);
        Message_EntityChanges[] messages = new Message_EntityChanges[splitChanges.size()];
        Iterator<EntityChange[]> changesIterator = splitChanges.iterator();
        for (int i = 0; changesIterator.hasNext(); i++) {
            EntityChanges entityChanges = new EntityChanges(changesIterator.next());
            byte[] data = NetworkUtil.writeToBytes(entityChanges);
            messages[i] = new Message_EntityChanges(data);
        }
        return messages;
    }
}
