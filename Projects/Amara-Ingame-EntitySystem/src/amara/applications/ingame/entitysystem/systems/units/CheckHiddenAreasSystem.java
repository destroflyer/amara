/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersection.*;

/**
 *
 * @author Carl
 */
public class CheckHiddenAreasSystem implements EntitySystem{

    public CheckHiddenAreasSystem(IntersectionInformant intersectionInformant){
        this.intersectionInformant = intersectionInformant;
    }
    private IntersectionInformant intersectionInformant;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        IntersectionTracker<Pair<Integer>> tracker = intersectionInformant.getTracker(entityWorld, this);
        for(Pair<Integer> pair : tracker.getLeavers()){
            checkHiddenArea_Leaver(entityWorld, pair.getA(), pair.getB());
            checkHiddenArea_Leaver(entityWorld, pair.getB(), pair.getA());
        }
        for(Pair<Integer> pair : tracker.getEntries()){
            checkHiddenArea_Entry(entityWorld, pair.getA(), pair.getB());
            checkHiddenArea_Entry(entityWorld, pair.getB(), pair.getA());
        }
    }
    
    private void checkHiddenArea_Leaver(EntityWorld entityWorld, int hiddenAreaEntity, int leavingEntity){
        if(entityWorld.hasComponent(hiddenAreaEntity, IsHiddenAreaComponent.class)){
            entityWorld.removeComponent(leavingEntity, IsInHiddenAreaComponent.class);
        }
    }
    
    private void checkHiddenArea_Entry(EntityWorld entityWorld, int hiddenAreaEntity, int enteringEntity){
        if(entityWorld.hasComponent(hiddenAreaEntity, IsHiddenAreaComponent.class)){
            entityWorld.setComponent(enteringEntity, new IsInHiddenAreaComponent(hiddenAreaEntity));
        }
    }
}
