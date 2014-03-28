/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class DisplaySpellsImagesSystem extends GUIDisplaySystem{

    public DisplaySpellsImagesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, SpellsComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(selectedEntity, SpellsComponent.class));
        observer.reset();
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            for(int i=0;i<4;i++){
                String visualisationName = "none";
                if(spellsComponent != null){
                    int[] spells = spellsComponent.getSpellsEntities();
                    if(i < spells.length){
                        SpellVisualisationComponent spellVisualisationComponent = entityWorld.getComponent(spells[i], SpellVisualisationComponent.class);
                        if(spellVisualisationComponent != null){
                            visualisationName = spellVisualisationComponent.getName();
                        }
                        else{
                            visualisationName = "unknown";
                        }
                    }
                }
                screenController_HUD.setSpellImage(i, "Interface/hud/spells/" + visualisationName + ".png");
            }
        }
    }
}
