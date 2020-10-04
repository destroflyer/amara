package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

public class UpdateSpellInformationsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public UpdateSpellInformationsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class, LearnableSpellsComponent.class, SpellsComponent.class, MapSpellsComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(characterEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(characterEntity, PassivesComponent.class));
        checkChangedLearnableSpells(entityWorld, observer.getNew().getComponent(characterEntity, LearnableSpellsComponent.class));
        checkChangedLearnableSpells(entityWorld, observer.getChanged().getComponent(characterEntity, LearnableSpellsComponent.class));;
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, SpellsComponent.class));;
        checkChangedMapSpells(entityWorld, observer.getNew().getComponent(characterEntity, MapSpellsComponent.class));
        checkChangedMapSpells(entityWorld, observer.getChanged().getComponent(characterEntity, MapSpellsComponent.class));
        screenController.checkAction_SpellInformation();
    }

    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent) {
        if (passivesComponent != null) {
            int[] passives = passivesComponent.getPassiveEntities();
            screenController.setPlayer_SpellInformations_Passives(createSpellInformations(entityWorld, passives, false));
        }
    }

    private void checkChangedLearnableSpells(EntityWorld entityWorld, LearnableSpellsComponent learnableSpellsComponent) {
        if (learnableSpellsComponent != null) {
            int[] spells = learnableSpellsComponent.getSpellsEntities();
            screenController.setPlayer_SpellInformations_LearnableSpells(createSpellInformations(entityWorld, spells, false));
        }
    }

    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent) {
        if (spellsComponent != null) {
            int[] spells = spellsComponent.getSpellsEntities();
            screenController.setPlayer_SpellInformations_Spells(createSpellInformations(entityWorld, spells, false));
        }
    }

    private void checkChangedMapSpells(EntityWorld entityWorld, MapSpellsComponent mapSpellsComponent) {
        if (mapSpellsComponent != null) {
            int[] spells = mapSpellsComponent.getSpellsEntities();
            screenController.setPlayer_SpellInformations_MapSpells(createSpellInformations(entityWorld, spells, false));
        }
    }

    public static SpellInformation[] createSpellInformations(EntityWorld entityWorld, int[] spellEntities, boolean useDeltaDescriptions) {
        SpellInformation[] spellInformations = new SpellInformation[spellEntities.length];
        for (int i = 0; i < spellInformations.length; i++) {
            if (spellEntities[i] != -1) {
                NameComponent nameComponent = entityWorld.getComponent(spellEntities[i], NameComponent.class);
                String name = ((nameComponent != null) ? nameComponent.getName() : "[Unnamed]");
                String description = "[No description available]";
                DeltaDescriptionComponent deltaDescriptionComponent = entityWorld.getComponent(spellEntities[i], DeltaDescriptionComponent.class);
                if (useDeltaDescriptions && (deltaDescriptionComponent != null)) {
                    description = deltaDescriptionComponent.getDescription();
                } else {
                    DescriptionComponent descriptionComponent = entityWorld.getComponent(spellEntities[i], DescriptionComponent.class);
                    if (descriptionComponent != null) {
                        description = descriptionComponent.getDescription();
                    }
                }
                CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntities[i], CooldownComponent.class);
                float cooldown = ((cooldownComponent != null) ? cooldownComponent.getDuration() : -1);
                spellInformations[i] = new SpellInformation(spellEntities[i], name, description, cooldown);
            }
        }
        return spellInformations;
    }
}
