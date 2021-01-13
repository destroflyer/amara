package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.MapSpellsComponent;
import amara.libraries.entitysystem.EntityWorld;

public class DisplayMapSpellsManaSufficientSystem extends DisplaySpellsManaSufficientSystem {

    public DisplayMapSpellsManaSufficientSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD, 2);
    }

    @Override
    protected int[] getSpells(EntityWorld entityWorld, int characterEntity) {
        MapSpellsComponent mapSpellsComponent = entityWorld.getComponent(characterEntity, MapSpellsComponent.class);
        return ((mapSpellsComponent != null) ? mapSpellsComponent.getSpellsEntities() : null);
    }

    @Override
    protected void setSufficientMana(int spellIndex, boolean sufficientMana) {
        screenController.setPlayer_MapSpellSufficientMana(spellIndex, sufficientMana);
    }
}
