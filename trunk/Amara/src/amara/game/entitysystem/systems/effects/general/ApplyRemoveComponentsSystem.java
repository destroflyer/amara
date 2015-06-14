/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.general;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.general.*;
/**
 *
 * @author Carl
 */
public class ApplyRemoveComponentsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveComponentsComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            for(String componentClassName : entityWrapper.getComponent(RemoveComponentsComponent.class).getComponentsClassesNames()){
                try{
                    entityWorld.removeComponent(targetID, Class.forName(componentClassName));
                }catch(ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
