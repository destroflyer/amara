/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import java.util.ArrayList;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class EntitySystemDisplayAppState<T extends DisplayApplication> extends BaseDisplayAppState<T> {

    public EntitySystemDisplayAppState() {
        
    }
    protected EntityWorld entityWorld = new EntityWorld();
    private ArrayList<EntitySystem> entitySystems = new ArrayList<>();

    public void addEntitySystem(EntitySystem entitySystem){
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        for (EntitySystem entitySystem : entitySystems) {
            entitySystem.update(entityWorld, lastTimePerFrame);
        }
    }

    public EntityWorld getEntityWorld() {
        return entityWorld;
    }

    public ArrayList<EntitySystem> getEntitySystems() {
        return entitySystems;
    }
}
