/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayMapSpellsImagesSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayMapSpellsImagesSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD) {
        super(playerAppState, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MapSpellsComponent.class);
        checkChangedSpells(entityWorld, observer.getNew().getComponent(characterEntity, MapSpellsComponent.class));
        checkChangedSpells(entityWorld, observer.getChanged().getComponent(characterEntity, MapSpellsComponent.class));
    }
    
    private void checkChangedSpells(EntityWorld entityWorld, MapSpellsComponent mapSpellsComponent){
        if(mapSpellsComponent != null){
            int[] spells = mapSpellsComponent.getSpellsEntities();
            for(int i=0;i<2;i++){
                String imagePath = DisplaySpellsImagesSystem.getSpellImageFilePath(entityWorld, spells, i);
                screenController.setPlayer_MapSpellImage(i, imagePath);
            }
        }
    }
}
