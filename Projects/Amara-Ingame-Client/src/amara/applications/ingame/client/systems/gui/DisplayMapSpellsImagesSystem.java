/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayMapSpellsImagesSystem extends GUIDisplaySystem{

    public DisplayMapSpellsImagesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MapSpellsComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(selectedEntity, MapSpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(selectedEntity, MapSpellsComponent.class));
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, MapSpellsComponent mapSpellsComponent){
        if(mapSpellsComponent != null){
            int[] spells = mapSpellsComponent.getSpellsEntities();
            for(int i=0;i<2;i++){
                String imagePath = DisplaySpellsImagesSystem.getSpellImageFilePath(entityWorld, spells, i);
                screenController_HUD.setMapSpellImage(i, imagePath);
            }
        }
    }
}
