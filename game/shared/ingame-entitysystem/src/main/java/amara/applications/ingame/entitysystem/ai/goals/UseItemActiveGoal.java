package amara.applications.ingame.entitysystem.ai.goals;

import amara.applications.ingame.entitysystem.ai.actions.CastSpellAction;
import amara.applications.ingame.entitysystem.components.general.TemporaryComponent;
import amara.applications.ingame.entitysystem.components.items.InventoryComponent;
import amara.applications.ingame.entitysystem.components.items.ItemActiveComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.spells.CastTypeComponent;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.ingame.ai.Action;
import amara.ingame.ai.Goal;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

import java.util.LinkedList;

public class UseItemActiveGoal extends Goal {

    private int spellEntity;
    private int targetEntity;

    @Override
    public boolean isEnabled(EntityWorld entityWorld, int entity) {
        spellEntity = -1;
        targetEntity = -1;
        int[] itemEntities = entityWorld.getComponent(entity, InventoryComponent.class).getItemEntities();
        for (int itemEntity : itemEntities) {
            ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
            if (itemActiveComponent != null) {
                int itemSpellEntity = itemActiveComponent.getSpellEntity();
                if (CastSpellSystem.canCast(entityWorld, entity, itemSpellEntity)) {
                    spellEntity = itemSpellEntity;
                    CastTypeComponent.CastType castType = entityWorld.getComponent(itemSpellEntity, CastTypeComponent.class).getCastType();
                    switch (castType) {
                        case SELFCAST:
                            targetEntity = -1;
                            break;
                        case SINGLE_TARGET:
                            targetEntity = entity;
                            break;
                        case LINEAR_SKILLSHOT:
                            Vector2f direction = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                            targetEntity = entityWorld.createEntity();
                            entityWorld.setComponent(targetEntity, new TemporaryComponent());
                            entityWorld.setComponent(targetEntity, new DirectionComponent(direction.clone()));
                            break;
                        case POSITIONAL_SKILLSHOT:
                            Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                            targetEntity = entityWorld.createEntity();
                            entityWorld.setComponent(targetEntity, new TemporaryComponent());
                            entityWorld.setComponent(targetEntity, new PositionComponent(position.clone()));
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(EntityWorld entityWorld, int entity) {

    }

    @Override
    public double getValue(EntityWorld entityWorld, int entity) {
        return 1;
    }

    @Override
    public void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions) {
        actions.add(new CastSpellAction(spellEntity, targetEntity));
    }
}
