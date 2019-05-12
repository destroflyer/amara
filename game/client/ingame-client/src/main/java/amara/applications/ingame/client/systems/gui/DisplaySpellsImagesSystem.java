/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplaySpellsImagesSystem extends GUIDisplaySystem{

    public DisplaySpellsImagesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, SpellsComponent.class));
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            for(int i=0;i<4;i++){
                String imageFilePath = getSpellImageFilePath(entityWorld, spells, i);
                screenController_HUD.setSpellImage(i, imageFilePath);
            }
        }
    }
    
    public static String getSpellImageFilePath(EntityWorld entityWorld, int[] spells, int spellIndex){
        String filePath = "Interface/hud/spells/";
        if((spellIndex < spells.length) && (spells[spellIndex] != -1)){
            SpellVisualisationComponent spellVisualisationComponent = entityWorld.getComponent(spells[spellIndex], SpellVisualisationComponent.class);
            if(spellVisualisationComponent != null){
                filePath += spellVisualisationComponent.getName();
            }
            else{
                filePath += "unknown";
            }
        }
        else{
            filePath += "none";
        }
        filePath += ".png";
        return filePath;
    }
}
