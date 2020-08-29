package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.RequestUpdateAttributesComponent;
import amara.applications.ingame.entitysystem.components.units.ExperienceComponent;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class LevelUpSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, ExperienceComponent.class);
        for (int entity : observer.getNew().getEntitiesWithAny(ExperienceComponent.class)) {
            checkLevelUp(entityWorld, entity);
        }
        for (int entity : observer.getChanged().getEntitiesWithAny(ExperienceComponent.class)) {
            checkLevelUp(entityWorld, entity);
        }
    }

    private void checkLevelUp(EntityWorld entityWorld, int entity) {
        int level = entityWorld.getComponent(entity, LevelComponent.class).getLevel();
        int experience = entityWorld.getComponent(entity, ExperienceComponent.class).getExperience();
        int neededLevelUpExperience = getNeededLevelUpExperience(level);
        if (experience >= neededLevelUpExperience) {
            levelUp(entityWorld, entity, experience - neededLevelUpExperience);
            checkLevelUp(entityWorld, entity);
        }
    }

    public static void levelUp(EntityWorld entityWorld, int entity) {
        levelUp(entityWorld, entity, 0);
    }

    private static void levelUp(EntityWorld entityWorld, int entity, int newExperience) {
        int level = entityWorld.getComponent(entity, LevelComponent.class).getLevel();
        entityWorld.setComponent(entity, new LevelComponent(level + 1));
        entityWorld.setComponent(entity, new ExperienceComponent(newExperience));
        entityWorld.setComponent(entity, new RequestUpdateAttributesComponent());
        SpellsUpgradePointsComponent spellsUpgradePointsComponent = entityWorld.getComponent(entity, SpellsUpgradePointsComponent.class);
        if (spellsUpgradePointsComponent != null) {
            entityWorld.setComponent(entity, new SpellsUpgradePointsComponent(spellsUpgradePointsComponent.getUpgradePoints() + 1));
        }
    }

    public static int getNeededLevelUpExperience(int level) {
        return (180 + (level * 100));
    }
}
