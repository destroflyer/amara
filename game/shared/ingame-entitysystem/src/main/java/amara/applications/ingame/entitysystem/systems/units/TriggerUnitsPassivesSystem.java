/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.HashMap;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class TriggerUnitsPassivesSystem implements EntitySystem{
    
    private HashMap<Integer, int[]> cachedPassives = new HashMap<Integer, int[]>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(PassivesComponent.class)){
            checkPassives(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(PassivesComponent.class)){
            checkPassives(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(PassivesComponent.class)){
            for(int passiveEntity : observer.getRemoved().getComponent(entity, PassivesComponent.class).getPassiveEntities()){
                PassiveUtil.removePassives(entityWorld, entity, passiveEntity);
            }
        }
    }
    
    private void checkPassives(EntityWorld entityWorld, int entity){
        int[] oldPassiveEntities = cachedPassives.get(entity);
        int[] newPassiveEntities = entityWorld.getComponent(entity, PassivesComponent.class).getPassiveEntities();
        boolean wasPassiveAdded;
        for(int newPassiveEntity : newPassiveEntities){
            wasPassiveAdded = true;
            if(oldPassiveEntities != null){
                for(int oldPassiveEntity : oldPassiveEntities){
                    if(newPassiveEntity == oldPassiveEntity){
                        wasPassiveAdded = false;
                        break;
                    }
                }
            }
            if(wasPassiveAdded){
                PassiveUtil.addPassives(entityWorld, entity, newPassiveEntity);
            }
        }
        if(oldPassiveEntities != null){
            boolean wasPassiveRemoved;
            for(int oldPassiveEntity : oldPassiveEntities){
                wasPassiveRemoved = true;
                for(int newPassiveEntity : newPassiveEntities){
                    if(newPassiveEntity == oldPassiveEntity){
                        wasPassiveRemoved = false;
                        break;
                    }
                }
                if(wasPassiveRemoved){
                    PassiveUtil.removePassives(entityWorld, entity, oldPassiveEntity);
                }
            }
        }
        cachedPassives.put(entity, newPassiveEntities);
    }
}
