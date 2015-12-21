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
    public static final String DIRECTORY = "Interface/hud/spells/";

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, SpellsComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(selectedEntity, SpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(selectedEntity, SpellsComponent.class));
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, SpellsComponent spellsComponent){
        if(spellsComponent != null){
            int[] spells = spellsComponent.getSpellsEntities();
            String imagePath;
            for(int i=0;i<4;i++){
                if((i < spells.length) && (spells[i] != -1)){
                    imagePath = getSpellImagePath(entityWorld, spells[i]);
                }
                else{
                    imagePath = (DIRECTORY + "none.png");
                }
                screenController_HUD.setSpellImage(i, imagePath);
                
            }
        }
    }
    
    public static String getSpellImagePath(EntityWorld entityWorld, int entity){
        String fileName;
        SpellVisualisationComponent spellVisualisationComponent = entityWorld.getComponent(entity, SpellVisualisationComponent.class);
        if(spellVisualisationComponent != null){
            fileName = (spellVisualisationComponent.getName() + ".png");
        }
        else{
            fileName = "unknown.png";
        }
        return (DIRECTORY + fileName);
    }
}
