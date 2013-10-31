/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.ArrayList;
import com.jme3.app.SimpleApplication;
import amara.game.entitysystem.*;

/**
 *
 * @author Carl
 */
public class EntitySystemAppState<T extends SimpleApplication> extends BaseAppState<T>{

    public EntitySystemAppState(){
        
    }
    protected EntityWorld entityWorld = new EntityWorld();
    private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();
    
    public void addEntitySystem(EntitySystem entitySystem){
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        for(int i=0;i<entitySystems.size();i++){
            EntitySystem entitySystem = entitySystems.get(i);
            entitySystem.update(entityWorld, lastTimePerFrame);
        }
    }

    public EntityWorld getEntityWorld(){
        return entityWorld;
    }
}
