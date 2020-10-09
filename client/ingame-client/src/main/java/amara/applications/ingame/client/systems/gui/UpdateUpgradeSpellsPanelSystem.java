package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.libraries.entitysystem.*;

public class UpdateUpgradeSpellsPanelSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public UpdateUpgradeSpellsPanelSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }
    private boolean isUpdateRequired;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        isUpdateRequired = false;
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class, SpellsUpgradePointsComponent.class);
        checkChangedComponent(observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedComponent(observer.getChanged().getComponent(characterEntity, SpellsComponent.class));
        checkChangedComponent(observer.getNew().getComponent(characterEntity, SpellsUpgradePointsComponent.class));
        checkChangedComponent(observer.getChanged().getComponent(characterEntity, SpellsUpgradePointsComponent.class));
        if (isUpdateRequired) {
            updateUpgradeSpellsPanel(entityWorld, characterEntity);
        }
    }

    private void checkChangedComponent(Object changedComponent) {
        if (changedComponent != null) {
            isUpdateRequired = true;
        }
    }

    private void updateUpgradeSpellsPanel(EntityWorld entityWorld, int characterEntity) {
        SpellsComponent spellsComponent = entityWorld.getComponent(characterEntity, SpellsComponent.class);
        SpellsUpgradePointsComponent spellsUpgradePointsComponent = entityWorld.getComponent(characterEntity, SpellsUpgradePointsComponent.class);
        if ((spellsComponent != null) && (spellsUpgradePointsComponent != null)) {
            boolean showContainer = false;
            boolean showButton;
            boolean learnOrUpgrade = false;
            for (int i = 0; i < 4; i++) {
                showButton = false;
                if (SpellUtil.canUpgradeSpell(entityWorld, characterEntity, i, 0)) {
                    showButton = true;
                    learnOrUpgrade = false;
                } else if (SpellUtil.canLearnSpell(entityWorld, characterEntity, i)) {
                    showButton = true;
                    learnOrUpgrade = true;
                }
                screenController.setPlayer_UpgradeSpellsButtonVisible(i, showButton);
                if (showButton) {
                    screenController.setPlayer_UpgradeSpellsButtonImage(i, "Interface/hud/" + (learnOrUpgrade ? "learn" : "upgrade") + "_spell_button.png");
                    showContainer = true;
                }
            }
            screenController.setPlayer_UpgradeSpellsContainerVisible(showContainer);
        }
    }
}
