/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.applications.ingame.entitysystem.components.units.PassivesComponent;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
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
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PassivesComponent.class);
        checkChangedPassives(entityWorld, observer.getNew().getComponent(selectedEntity, PassivesComponent.class));
        checkChangedPassives(entityWorld, observer.getChanged().getComponent(selectedEntity, PassivesComponent.class));
    }
    
    private void checkChangedPassives(EntityWorld entityWorld, PassivesComponent passivesComponent){
        if(passivesComponent != null){
            int[] passives = passivesComponent.getPassiveEntities();
            String imagePath;
            if((passives.length > 0) && (passives[0] != -1)){
                imagePath = DisplaySpellsImagesSystem.getSpellImagePath(entityWorld, passives[0]);
            }
            else{
                imagePath = (DisplaySpellsImagesSystem.DIRECTORY + "none.png");
            }
            screenController_HUD.setPassiveImage(imagePath);
        }
    }
}
