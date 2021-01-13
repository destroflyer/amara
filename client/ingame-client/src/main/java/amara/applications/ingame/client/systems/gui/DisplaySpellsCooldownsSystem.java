package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;

public class DisplaySpellsCooldownsSystem extends DisplaySpellsComponentSystem<RemainingCooldownComponent> {

    public DisplaySpellsCooldownsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD, RemainingCooldownComponent.class);
    }

    @Override
    protected void show(int spellIndex, RemainingCooldownComponent remainingCooldownComponent) {
        screenController.showPlayer_SpellCooldown(spellIndex, remainingCooldownComponent.getDuration());
    }

    @Override
    protected void hide(int spellIndex) {
        screenController.hidePlayer_SpellCooldown(spellIndex);
    }
}
