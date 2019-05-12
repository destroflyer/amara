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
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if(playerCharacterComponent != null){
            int characterEntity = playerCharacterComponent.getEntity();
            ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
            if(observer.getNew().hasComponent(characterEntity, IsAliveComponent.class)){
                postFilterAppState.removeFilter(grayscaleFilter);
            }
            else if(observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)){
                postFilterAppState.addFilter(grayscaleFilter);
            }
        }
    }
}
