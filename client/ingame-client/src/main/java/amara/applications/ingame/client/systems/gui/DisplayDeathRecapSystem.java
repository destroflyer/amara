package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.DamageHistoryComponent;
import amara.applications.ingame.entitysystem.components.units.IsAliveComponent;
import amara.core.Util;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayDeathRecapSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayDeathRecapSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        if (observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)) {
            String text = "[No damage history existing]";
            DamageHistoryComponent damageHistoryComponent = entityWorld.getComponent(characterEntity, DamageHistoryComponent.class);
            if (damageHistoryComponent != null) {
                float totalDamage = 0;
                for (DamageHistoryComponent.DamageHistoryEntry entry : damageHistoryComponent.getEntries()) {
                    totalDamage += entry.getDamage();
                }
                text = ((int) totalDamage) + " total damage over " + Util.round(damageHistoryComponent.getLastDamageTime() - damageHistoryComponent.getFirstDamageTime(), 3) + " seconds\n";
                for (DamageHistoryComponent.DamageHistoryEntry entry : damageHistoryComponent.getEntries()) {
                    text += "\n";
                    if (entry.getSourceName() != null) {
                        text += entry.getSourceName() + " -> ";
                    }
                    if (entry.getSourceSpellName() != null) {
                        text += entry.getSourceSpellName() + " -> ";
                    }
                    text += ((int) entry.getDamage()) + " " + entry.getDamageType().name().toLowerCase();
                }
            }
            screenController.setDeathLayersVisible(true);
            screenController.setDeathRecapText(text);
            screenController.setDeathRecapVisible(false);
        } else if (observer.getNew().hasComponent(characterEntity, IsAliveComponent.class)) {
            screenController.setDeathLayersVisible(false);
        }
    }
}
