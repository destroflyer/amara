/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.camera;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.input.ChaseCamera;
import com.jme3.scene.Node;

import java.util.function.Supplier;

/**
 *
 * @author Carl
 */
public class ChaseCameraSystem implements EntitySystem {

    public ChaseCameraSystem(Supplier<Integer> playerEntitySupplier, ChaseCamera chaseCamera, EntitySceneMap entitySceneMap) {
        this.playerEntitySupplier = playerEntitySupplier;
        this.chaseCamera = chaseCamera;
        this.entitySceneMap = entitySceneMap;
    }
    private Supplier<Integer> playerEntitySupplier;
    private ChaseCamera chaseCamera;
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, PlayerCharacterComponent.class);
        int playerEntity = playerEntitySupplier.get();
        checkPlayerCharacterComponent(observer.getNew().getComponent(playerEntity, PlayerCharacterComponent.class));
        checkPlayerCharacterComponent(observer.getChanged().getComponent(playerEntity, PlayerCharacterComponent.class));
    }

    private void checkPlayerCharacterComponent(PlayerCharacterComponent playerCharacterComponent) {
        if (playerCharacterComponent != null){
            int characterEntity = playerCharacterComponent.getEntity();
            Node characterNode = entitySceneMap.requestNode(characterEntity);
            chaseCamera.setSpatial(characterNode);
            characterNode.addControl(chaseCamera);
        }
    }
}
