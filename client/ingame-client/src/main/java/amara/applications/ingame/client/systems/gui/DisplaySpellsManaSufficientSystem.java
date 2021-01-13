package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.attributes.ManaComponent;
import amara.applications.ingame.entitysystem.components.costs.ManaCostComponent;
import amara.applications.ingame.entitysystem.components.spells.CastCostComponent;
import amara.libraries.entitysystem.EntityWorld;

public abstract class DisplaySpellsManaSufficientSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplaySpellsManaSufficientSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD, int spellsCount) {
        super(playerAppState, screenController_HUD);
        this.spellsCount = spellsCount;
    }
    private int spellsCount;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ManaComponent manaComponent = entityWorld.getComponent(characterEntity, ManaComponent.class);
        float characterMana = ((manaComponent != null) ? manaComponent.getValue() : 0);
        int[] spells = getSpells(entityWorld, characterEntity);
        for (int i = 0; i < spellsCount; i++) {
            float manaCost = 0;
            if ((spells != null) && (i < spells.length)) {
                CastCostComponent castCostComponent = entityWorld.getComponent(spells[i], CastCostComponent.class);
                if (castCostComponent != null) {
                    ManaCostComponent manaCostComponent = entityWorld.getComponent(castCostComponent.getCostEntity(), ManaCostComponent.class);
                    if (manaCostComponent != null) {
                        manaCost = manaCostComponent.getMana();
                    }
                }
            }
            setSufficientMana(i, (characterMana >= manaCost));
        }
    }

    protected abstract int[] getSpells(EntityWorld entityWorld, int characterEntity);

    protected abstract void setSufficientMana(int spellIndex, boolean sufficientMana);
}
