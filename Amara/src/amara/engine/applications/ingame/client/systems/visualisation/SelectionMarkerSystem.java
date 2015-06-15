/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.systems.visualisation.buffs.BuffVisualisationSystem_SonicWaveMark;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.SelectedUnitComponent;

/**
 *
 * @author Carl
 */
public class SelectionMarkerSystem implements EntitySystem{
    
    public SelectionMarkerSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    public final static String NODE_NAME_SELECTION_MARKER = "selectionMarker";
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, SelectedUnitComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedUnit = observer.getNew().getComponent(entity, SelectedUnitComponent.class).getEntity();
            Node node = entitySceneMap.requestNode(selectedUnit);
            node.attachChild(BuffVisualisationSystem_SonicWaveMark.createGroundTexture("Textures/selection_markers/circle.png", 2, 2));
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(SelectedUnitComponent.class)){
            int selectedUnit = observer.getRemoved().getComponent(entity, SelectedUnitComponent.class).getEntity();
            Node node = entitySceneMap.requestNode(selectedUnit);
            node.detachChildNamed(NODE_NAME_SELECTION_MARKER);
        }
        observer.reset();
    }
}
