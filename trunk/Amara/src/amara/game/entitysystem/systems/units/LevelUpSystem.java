/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class LevelUpSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, ExperienceComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(ExperienceComponent.class)){
            checkLevelUp(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(ExperienceComponent.class)){
            checkLevelUp(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void checkLevelUp(EntityWorld entityWorld, int entity){
        int level = entityWorld.getComponent(entity, LevelComponent.class).getLevel();
        int experience = entityWorld.getComponent(entity, ExperienceComponent.class).getExperience();
        int neededLevelUpExperience = getNeededLevelUpExperience(level);
        if(experience >= neededLevelUpExperience){
            entityWorld.setComponent(entity, new LevelComponent(level + 1));
            entityWorld.setComponent(entity, new ExperienceComponent(experience - neededLevelUpExperience));
            SpellsUpgradePointsComponent spellsUpgradePointsComponent = entityWorld.getComponent(entity, SpellsUpgradePointsComponent.class);
            if(spellsUpgradePointsComponent != null){
                entityWorld.setComponent(entity, new SpellsUpgradePointsComponent(spellsUpgradePointsComponent.getUpgradePoints() + 1));
            }
            checkLevelUp(entityWorld, entity);
        }
    }
    
    public static int getNeededLevelUpExperience(int level){
        return (180 + (level * 100));
    }
}
