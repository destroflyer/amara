/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.filters;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.applications.display.appstates.PostFilterAppState;
import amara.libraries.applications.display.filters.GrayScaleFilter;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PlayerDeathDisplaySystem implements EntitySystem{

    public PlayerDeathDisplaySystem(int playerEntity, PostFilterAppState postFilterAppState){
        this.playerEntity = playerEntity;
        this.postFilterAppState = postFilterAppState;
    }
    private int playerEntity;
    private PostFilterAppState postFilterAppState;
    private GrayScaleFilter grayscaleFilter = new GrayScaleFilter();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            int selectedEntity = selectedUnitComponent.getEntity();
            ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
            if(observer.getNew().hasComponent(selectedEntity, IsAliveComponent.class)){
                postFilterAppState.removeFilter(grayscaleFilter);
            }
            else if(observer.getRemoved().hasComponent(selectedEntity, IsAliveComponent.class)){
                postFilterAppState.addFilter(grayscaleFilter);
            }
        }
    }
}