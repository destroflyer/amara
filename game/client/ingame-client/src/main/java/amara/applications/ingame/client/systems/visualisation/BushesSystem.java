/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import amara.applications.ingame.client.systems.visualisation.meshes.BushMesh;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.shapes.Shape;
import amara.libraries.physics.shapes.SimpleConvexPolygon;

/**
 *
 * @author Carl
 */
public class BushesSystem implements EntitySystem{
    
    public BushesSystem(EntitySceneMap entitySceneMap, int playerEntity){
        this.entitySceneMap = entitySceneMap;
        this.playerEntity = playerEntity;
    }
    public final static String GEOMETRY_NAME_BUSH = "bush";
    private EntitySceneMap entitySceneMap;
    private int playerEntity;
    private int currentPlayerHiddenAreaEntity = -1;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHiddenAreaComponent.class, IsInHiddenAreaComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(IsHiddenAreaComponent.class)){
            HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
            if(hitboxComponent != null){
                addBush(entity, hitboxComponent.getShape());
            }
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsHiddenAreaComponent.class)){
            removeBush(entity);
        }
        //Adapt bush transparency
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if(playerCharacterComponent != null){
            int characterEntity = playerCharacterComponent.getEntity();
            if(observer.getNew().hasComponent(characterEntity, IsInHiddenAreaComponent.class)
            || observer.getChanged().hasComponent(characterEntity, IsInHiddenAreaComponent.class)){
                if(currentPlayerHiddenAreaEntity != -1){
                    setBushTransparency(currentPlayerHiddenAreaEntity, false);
                }
                currentPlayerHiddenAreaEntity = entityWorld.getComponent(characterEntity, IsInHiddenAreaComponent.class).getHiddenAreaEntity();
                setBushTransparency(currentPlayerHiddenAreaEntity, true);
            }
            else if(observer.getRemoved().hasComponent(characterEntity, IsInHiddenAreaComponent.class)){
                setBushTransparency(currentPlayerHiddenAreaEntity, false);
            }
        }
    }
    
    private void addBush(int entity, Shape shape){
        Node node = entitySceneMap.requestNode(entity);
        Geometry geometry = new Geometry(null, new BushMesh((SimpleConvexPolygon) shape, 1, 2, 7));
        geometry.setName(GEOMETRY_NAME_BUSH);
        Material material = MaterialFactory.generateUnshadedMaterial("Textures/plants/bush.png");
        material.setFloat("AlphaDiscardThreshold", 0.05f);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        node.attachChild(geometry);
        setBushTransparency(entity, false);
    }
    
    private void removeBush(int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(GEOMETRY_NAME_BUSH);
    }
    
    private void setBushTransparency(int entity, boolean isTransparent){
        Node node = entitySceneMap.requestNode(entity);
        Geometry geometry = (Geometry) node.getChild(GEOMETRY_NAME_BUSH);
        Material material = geometry.getMaterial();
        if(isTransparent){
            geometry.setQueueBucket(RenderQueue.Bucket.Opaque);
            material.getAdditionalRenderState().setDepthTest(false);
            geometry.setUserData("layer", 1);
        }
        else{
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
            material.getAdditionalRenderState().setDepthTest(true);
            geometry.setUserData("layer", null);
        }
    }
}
