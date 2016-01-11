/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.engine.applications.ingame.client.gui.objects.SpellInformation;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class UpdateSpellInformationsSystem extends GUIDisplaySystem{

    public UpdateSpellInformationsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class, SpellsComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(selectedEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(selectedEntity, PassivesComponent.class));
        checkChangedSpells(entityWorld, observer.getNew().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(selectedEntity, SpellsComponent.class));
        screenController_HUD.checkAction_SpellInformation();
    }
    
    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent){
        if(passivesComponent != null){
            int[] passives = passivesComponent.getPassiveEntities();
            screenController_HUD.setSpellInformations_Passives(createSpellInformations(entityWorld, passives));
        }
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            screenController_HUD.setSpellInformations_Spells(createSpellInformations(entityWorld, spells));
        }
    }
    
    private SpellInformation[] createSpellInformations(EntityWorld entityWorld, int[] entities){
        SpellInformation[] spellInformations = new SpellInformation[entities.length];
        for(int i=0;i<spellInformations.length;i++){
            NameComponent nameComponent = entityWorld.getComponent(entities[i], NameComponent.class);
            DescriptionComponent descriptionComponent = entityWorld.getComponent(entities[i], DescriptionComponent.class);
            CooldownComponent cooldownComponent = entityWorld.getComponent(entities[i], CooldownComponent.class);
            String name = ((nameComponent != null)?nameComponent.getName():"[Unnamed]");
            String description = ((descriptionComponent != null)?descriptionComponent.getDescription():"[No description available]");
            float cooldown = ((cooldownComponent != null)?cooldownComponent.getDuration():-1);
            spellInformations[i] = new SpellInformation(name, description, cooldown);
        }
        return spellInformations;
    }
}