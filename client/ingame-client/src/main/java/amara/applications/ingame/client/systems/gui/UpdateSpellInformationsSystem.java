package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.entitysystem.components.costs.ManaCostComponent;
import amara.applications.ingame.entitysystem.components.general.DeltaDescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.DescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.spells.CastCostComponent;
import amara.applications.ingame.entitysystem.components.spells.CooldownComponent;
import amara.applications.ingame.entitysystem.components.units.LearnableSpellsComponent;
import amara.applications.ingame.entitysystem.components.units.MapSpellsComponent;
import amara.applications.ingame.entitysystem.components.units.PassivesComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

import java.util.function.Consumer;
import java.util.function.Function;

public class UpdateSpellInformationsSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public UpdateSpellInformationsSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            // Components directly on spell
            PassivesComponent.class,
            LearnableSpellsComponent.class,
            SpellsComponent.class,
            MapSpellsComponent.class,
            NameComponent.class,
            DescriptionComponent.class,
            DeltaDescriptionComponent.class,
            CooldownComponent.class,
            CastCostComponent.class,
            // Nested components
            ManaCostComponent.class
        );
        checkChangedSpells(entityWorld, observer, characterEntity, PassivesComponent.class, PassivesComponent::getPassiveEntities, spellInformations -> screenController.setPlayer_SpellInformations_Passives(spellInformations));
        checkChangedSpells(entityWorld, observer, characterEntity, LearnableSpellsComponent.class, LearnableSpellsComponent::getSpellsEntities, spellInformations -> screenController.setPlayer_SpellInformations_LearnableSpells(spellInformations));
        checkChangedSpells(entityWorld, observer, characterEntity, SpellsComponent.class, SpellsComponent::getSpellsEntities, spellInformations -> screenController.setPlayer_SpellInformations_Spells(spellInformations));
        checkChangedSpells(entityWorld, observer, characterEntity, MapSpellsComponent.class, MapSpellsComponent::getSpellsEntities, spellInformations -> screenController.setPlayer_SpellInformations_MapSpells(spellInformations));
        screenController.checkAction_SpellInformation();
    }

    private <T> void checkChangedSpells(EntityWorld entityWorld, ComponentMapObserver observer, int characterEntity, Class<T> spellsComponentClass, Function<T, int[]> getSpellsEntities, Consumer<SpellInformation[]> displaySpellInformations) {
        T component = entityWorld.getComponent(characterEntity, spellsComponentClass);
        if (component != null) {
            int[] spellsEntities = getSpellsEntities.apply(component);
            boolean hasSpellsComponentChanged = isNewOrChanged(observer, characterEntity, spellsComponentClass);
            if (hasSpellsComponentChanged || haveSpellsInternallyChanged(entityWorld, observer, spellsEntities)) {
                SpellInformation[] spellInformations = createSpellInformations(entityWorld, spellsEntities, false);
                displaySpellInformations.accept(spellInformations);
            }
        }
    }

    private boolean haveSpellsInternallyChanged(EntityWorld entityWorld, ComponentMapObserver observer, int[] spellEntities) {
        for (int spellEntity : spellEntities) {
            if (hasSpellInternallyChanged(entityWorld, observer, spellEntity)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSpellInternallyChanged(EntityWorld entityWorld, ComponentMapObserver observer, int spellEntity) {
        // Components directly on spell
        if (isNewOrChanged(observer, spellEntity, NameComponent.class, DescriptionComponent.class, DeltaDescriptionComponent.class, CooldownComponent.class, CastCostComponent.class)) {
            return true;
        }
        // Nested components
        CastCostComponent castCostComponent = entityWorld.getComponent(spellEntity, CastCostComponent.class);
        if (castCostComponent != null) {
            int costEntity = castCostComponent.getCostEntity();
            return isNewOrChanged(observer, costEntity, ManaCostComponent.class);
        }
        return false;
    }

    private boolean isNewOrChanged(ComponentMapObserver observer, int entity, Class<?>... componentClass) {
        return (observer.getNew().hasAnyComponent(entity, componentClass) || observer.getChanged().hasAnyComponent(entity, componentClass));
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
                Float cooldown = ((cooldownComponent != null) ? cooldownComponent.getDuration() : null);
                Float manaCost = null;
                CastCostComponent castCostComponent = entityWorld.getComponent(spellEntities[i], CastCostComponent.class);
                if (castCostComponent != null) {
                    ManaCostComponent manaCostComponent = entityWorld.getComponent(castCostComponent.getCostEntity(), ManaCostComponent.class);
                    if (manaCostComponent != null) {
                        manaCost = manaCostComponent.getMana();
                    }
                }
                spellInformations[i] = new SpellInformation(spellEntities[i], name, description, cooldown, manaCost);
            }
        }
        return spellInformations;
    }
}
