/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class GoldGenerationSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(GoldComponent.class, GoldPerSecondComponent.class)){
            float gold = entityWorld.getComponent(entity, GoldComponent.class).getGold();
            float goldPerSecond = entityWorld.getComponent(entity, GoldPerSecondComponent.class).getValue();
            entityWorld.setComponent(entity, new GoldComponent(gold + (goldPerSecond * deltaSeconds)));
        }
    }
}
