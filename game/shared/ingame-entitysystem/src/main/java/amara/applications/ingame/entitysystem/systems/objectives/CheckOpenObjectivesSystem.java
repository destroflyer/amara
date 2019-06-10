/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.objectives;

import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckOpenObjectivesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAny(OpenObjectiveComponent.class))
        {
            boolean isFinished = true;
            OrObjectivesComponent orObjectivesComponent = entityWorld.getComponent(entity, OrObjectivesComponent.class);
            if(orObjectivesComponent != null){
                isFinished = false;
                for(int objectiveEntity : orObjectivesComponent.getObjectiveEntities()){
                    if(entityWorld.hasComponent(objectiveEntity, FinishedObjectiveComponent.class)){
                        isFinished = true;
                        break;
                    }
                }
            }
            MissingEntitiesComponent missingEntitiesComponent = entityWorld.getComponent(entity, MissingEntitiesComponent.class);
            if(missingEntitiesComponent != null){
                for(int missingEntity : missingEntitiesComponent.getEntities()){
                    if(entityWorld.getComponents(missingEntity).size() > 0){
                        isFinished = false;
                        break;
                    }
                }
            }
            if(isFinished){
                finishObjective(entityWorld, entity);
            }
        }
    }

    public static void finishObjective(EntityWorld entityWorld, int objectiveEntity) {
        entityWorld.removeComponent(objectiveEntity, OpenObjectiveComponent.class);
        entityWorld.setComponent(objectiveEntity, new FinishedObjectiveComponent());
    }
}
