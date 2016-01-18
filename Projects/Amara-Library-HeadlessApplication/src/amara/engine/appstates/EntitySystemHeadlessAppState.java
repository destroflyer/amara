/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.ArrayList;
import amara.engine.applications.HeadlessApplication;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class EntitySystemHeadlessAppState<T extends HeadlessApplication> extends BaseHeadlessAppState<T>{

    public EntitySystemHeadlessAppState(){
        
    }
    protected EntityWorld entityWorld = new EntityWorld();
    private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();
    private final float maxFrameDuration = 0.2f;
    
    public void addEntitySystem(EntitySystem entitySystem){
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        for(int i=0;i<entitySystems.size();i++){
            EntitySystem entitySystem = entitySystems.get(i);
            entitySystem.update(entityWorld, Math.min(lastTimePerFrame, maxFrameDuration));
        }
    }

    public EntityWorld getEntityWorld(){
        return entityWorld;
    }
}
