/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.units.PassivesComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayPassivesImagesSystem extends GUIDisplaySystem{

    public DisplayPassivesImagesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(characterEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(characterEntity, PassivesComponent.class));
    }
    
    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent){
        if(passivesComponent != null){
            int[] passives = passivesComponent.getPassiveEntities();
            String imageFilePath = DisplaySpellsImagesSystem.getSpellImageFilePath(entityWorld, passives, 0);
            screenController_HUD.setPassiveImage(imageFilePath);
        }
    }
}
