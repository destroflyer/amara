/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateSpellInformationsSystem extends GUIDisplaySystem{

    public UpdateSpellInformationsSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class, LearnableSpellsComponent.class, SpellsComponent.class, MapSpellsComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(characterEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(characterEntity, PassivesComponent.class));
        checkChangedLearnableSpells(entityWorld, observer.getNew().getComponent(characterEntity, LearnableSpellsComponent.class));
        checkChangedLearnableSpells(entityWorld, observer.getChanged().getComponent(characterEntity, LearnableSpellsComponent.class));;
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, SpellsComponent.class));;
        checkChangedMapSpells(entityWorld, observer.getNew().getComponent(characterEntity, MapSpellsComponent.class));
        checkChangedMapSpells(entityWorld, observer.getChanged().getComponent(characterEntity, MapSpellsComponent.class));
        screenController_HUD.checkAction_SpellInformation();
    }
    
    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent){
        if(passivesComponent != null){
            int[] passives = passivesComponent.getPassiveEntities();
            screenController_HUD.setSpellInformations_Passives(createSpellInformations(entityWorld, passives));
        }
    }
    
    private void checkChangedLearnableSpells(EntityWorld entityWorld, LearnableSpellsComponent learnableSpellsComponent){
        if(learnableSpellsComponent != null){
            int[] spells = learnableSpellsComponent.getSpellsEntities();
            screenController_HUD.setSpellInformations_LearnableSpells(createSpellInformations(entityWorld, spells));
        }
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            screenController_HUD.setSpellInformations_Spells(createSpellInformations(entityWorld, spells));
        }
    }
    
    private void checkChangedMapSpells(EntityWorld entityWorld, MapSpellsComponent mapSpellsComponent){
        if(mapSpellsComponent != null){
            int[] spells = mapSpellsComponent.getSpellsEntities();
            screenController_HUD.setSpellInformations_MapSpells(createSpellInformations(entityWorld, spells));
        }
    }
    
    private SpellInformation[] createSpellInformations(EntityWorld entityWorld, int[] spellEntities){
        SpellInformation[] spellInformations = new SpellInformation[spellEntities.length];
        for(int i=0;i<spellInformations.length;i++){
            NameComponent nameComponent = entityWorld.getComponent(spellEntities[i], NameComponent.class);
            DescriptionComponent descriptionComponent = entityWorld.getComponent(spellEntities[i], DescriptionComponent.class);
            CooldownComponent cooldownComponent = entityWorld.getComponent(spellEntities[i], CooldownComponent.class);
            String name = ((nameComponent != null)?nameComponent.getName():"[Unnamed]");
            String description = ((descriptionComponent != null)?descriptionComponent.getDescription():"[No description available]");
            float cooldown = ((cooldownComponent != null)?cooldownComponent.getDuration():-1);
            spellInformations[i] = new SpellInformation(spellEntities[i], name, description, cooldown);
        }
        return spellInformations;
    }
}
