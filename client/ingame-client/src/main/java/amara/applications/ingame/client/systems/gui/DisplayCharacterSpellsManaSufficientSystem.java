package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayCharacterSpellsManaSufficientSystem extends DisplaySpellsManaSufficientSystem {

    public DisplayCharacterSpellsManaSufficientSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD, 4);
    }

    @Override
    protected int[] getSpells(EntityWorld entityWorld, int characterEntity) {
        SpellsComponent spellsComponent = entityWorld.getComponent(characterEntity, SpellsComponent.class);
        return ((spellsComponent != null) ? spellsComponent.getSpellsEntities() : null);
    }

    @Override
    protected void setSufficientMana(int spellIndex, boolean sufficientMana) {
        screenController.setPlayer_SpellSufficientMana(spellIndex, sufficientMana);
    }
}
