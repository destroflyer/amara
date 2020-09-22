package amara.applications.ingame.entitysystem.systems.spells.casting;

import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;
import com.jme3.math.Vector2f;

public class CastSpellQueueSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int casterEntity : entityWorld.getEntitiesWithAny(SpellCastQueueComponent.class)) {
            SpellCastQueueComponent.SpellCastQueueEntry[] oldEntries = entityWorld.getComponent(casterEntity, SpellCastQueueComponent.class).getEntries();
            int spellEntity = oldEntries[0].getSpellEntity();
            int targetEntity = oldEntries[0].getTargetEntity();
            tryCastSpell(entityWorld, casterEntity, spellEntity, targetEntity);
            if (oldEntries.length > 1) {
                SpellCastQueueComponent.SpellCastQueueEntry[] newEntries = new SpellCastQueueComponent.SpellCastQueueEntry[oldEntries.length - 1];
                System.arraycopy(oldEntries, 1, newEntries, 0, newEntries.length);
                entityWorld.setComponent(casterEntity, new SpellCastQueueComponent(newEntries));
            } else {
                entityWorld.removeComponent(casterEntity, SpellCastQueueComponent.class);
            }
        }
    }

    public void enqueueSpellCast(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity) {
        SpellCastQueueComponent spellCastQueueComponent = entityWorld.getComponent(casterEntity, SpellCastQueueComponent.class);
        SpellCastQueueComponent.SpellCastQueueEntry[] oldEntries = ((spellCastQueueComponent == null) ? new SpellCastQueueComponent.SpellCastQueueEntry[0] : spellCastQueueComponent.getEntries());
        SpellCastQueueComponent.SpellCastQueueEntry[] newEntries = new SpellCastQueueComponent.SpellCastQueueEntry[oldEntries.length + 1];
        System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
        newEntries[oldEntries.length] = new SpellCastQueueComponent.SpellCastQueueEntry(spellEntity, targetEntity);
        entityWorld.setComponent(casterEntity, new SpellCastQueueComponent(newEntries));
    }

    private static void tryCastSpell(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity) {
        boolean isCasted = false;
        if (CastSpellSystem.canCast(entityWorld, casterEntity, spellEntity)) {
            isCasted = true;
            if (targetEntity != -1) {
                SpellTargetRulesComponent spellTargetRulesComponent = entityWorld.getComponent(spellEntity, SpellTargetRulesComponent.class);
                if (spellTargetRulesComponent != null) {
                    isCasted = TargetUtil.isValidTarget(entityWorld, casterEntity, targetEntity, spellTargetRulesComponent.getTargetRulesEntity());
                }
            }
            if (isCasted) {
                isCasted = addSpellCastComponents(entityWorld, casterEntity, spellEntity, targetEntity);
            }
        }
        if ((!isCasted) && entityWorld.hasComponent(targetEntity, TemporaryComponent.class)) {
            entityWorld.removeEntity(targetEntity);
        }
    }

    private static boolean addSpellCastComponents(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity) {
        boolean isCasted = false;
        AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
        boolean isAutoAttack = ((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity()));
        boolean castInstant = true;
        if (entityWorld.hasComponent(spellEntity, RangeComponent.class)) {
            float minimumCastRange = CastSpellSystem.getMinimumCastRange(entityWorld, casterEntity, spellEntity, targetEntity);
            Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
            Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            float distanceSquared = targetPosition.distanceSquared(casterPosition);
            if (distanceSquared > (minimumCastRange * minimumCastRange)) {
                if (ExecutePlayerCommandsSystem.tryWalk(entityWorld, casterEntity, targetEntity, minimumCastRange)) {
                    // Cast spell when target is reached
                    int castSpellTrigger = entityWorld.createEntity();
                    entityWorld.setComponent(castSpellTrigger, new TriggerTemporaryComponent());
                    entityWorld.setComponent(castSpellTrigger, new TargetReachedTriggerComponent());
                    entityWorld.setComponent(castSpellTrigger, new SourceTargetComponent());
                    int castSpellEffect = entityWorld.createEntity();
                    entityWorld.setComponent(castSpellEffect, new EnqueueSpellCastComponent(spellEntity, targetEntity));
                    entityWorld.setComponent(castSpellTrigger, new TriggeredEffectComponent(castSpellEffect));
                    entityWorld.setComponent(castSpellTrigger, new TriggerSourceComponent(casterEntity));
                    if (isAutoAttack) {
                        entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                    }
                    isCasted = true;
                }
                castInstant = false;
            }
        }
        if (castInstant) {
            boolean isAllowed = true;
            boolean cancelMovement = entityWorld.hasComponent(spellEntity, CastCancelMovementComponent.class);
            boolean cancelCast = entityWorld.hasComponent(spellEntity, CastCancelCastComponent.class);
            if (cancelMovement && cancelCast) {
                isAllowed = UnitUtil.tryCancelAction(entityWorld, casterEntity);
            } else if (cancelMovement) {
                isAllowed = UnitUtil.tryCancelMovement(entityWorld, casterEntity);
            } else if (cancelCast) {
                isAllowed = UnitUtil.tryCancelCast(entityWorld, casterEntity);
            }
            if (isAllowed) {
                if (isAutoAttack) {
                    entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                }
                entityWorld.setComponent(casterEntity, new CastSpellComponent(spellEntity, targetEntity));
                isCasted = true;
            }
        }
        return isCasted;
    }
}
