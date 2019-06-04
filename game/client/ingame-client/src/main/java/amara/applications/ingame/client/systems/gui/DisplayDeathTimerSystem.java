/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.effects.AffectedTargetsComponent;
import amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent;
import amara.applications.ingame.entitysystem.components.effects.RemainingEffectDelayComponent;
import amara.applications.ingame.entitysystem.components.effects.units.RespawnComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayDeathTimerSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayDeathTimerSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, RemainingEffectDelayComponent.class);
        for (int effectCastEntity : observer.getNew().getEntitiesWithAll(RemainingEffectDelayComponent.class)) {
            check(entityWorld, effectCastEntity);
        }
        for (int effectCastEntity : observer.getChanged().getEntitiesWithAll(RemainingEffectDelayComponent.class)) {
            check(entityWorld, effectCastEntity);
        }
    }

    private void check(EntityWorld entityWorld, int effectCastEntity){
        if (isRespawnEffectCast(entityWorld, effectCastEntity) && isPlayerCharacterAffected(entityWorld, effectCastEntity)) {
            float remainingDuration = entityWorld.getComponent(effectCastEntity, RemainingEffectDelayComponent.class).getDuration();
            screenController.setDeathTimer(remainingDuration);
        }
    }

    private boolean isRespawnEffectCast(EntityWorld entityWorld, int effectCastEntity) {
        int effectEntity = entityWorld.getComponent(effectCastEntity, PrepareEffectComponent.class).getEffectEntity();
        return entityWorld.hasComponent(effectEntity, RespawnComponent.class);
    }

    private boolean isPlayerCharacterAffected(EntityWorld entityWorld, int effectCastEntity) {
        int characterEntity = entityWorld.getComponent(getPlayerEntity(), PlayerCharacterComponent.class).getEntity();
        int[] affectedTargetEntities = entityWorld.getComponent(effectCastEntity, AffectedTargetsComponent.class).getTargetEntities();
        for (int affectedTargetEntity : affectedTargetEntities) {
            if (affectedTargetEntity == characterEntity) {
                return true;
            }
        }
        return false;
    }
}
