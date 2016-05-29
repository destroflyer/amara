/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import java.util.LinkedList;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.spells.indicators.*;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SpellIndicatorSystem implements EntitySystem{

    public SpellIndicatorSystem(int playerEntity, EntitySceneMap entitySceneMap){
        this.playerEntity = playerEntity;
        this.entitySceneMap = entitySceneMap;
    }
    private int playerEntity;
    private EntitySceneMap entitySceneMap;
    private int currentSpellEntity = -1;
    private LinkedList<Spatial> currentIndicators = new LinkedList<Spatial>();;
    private boolean updateIndicator;
    
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(updateIndicator){
            SpellIndicatorComponent spellIndicatorComponent = entityWorld.getComponent(currentSpellEntity, SpellIndicatorComponent.class);
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
            if((spellIndicatorComponent != null) && (playerCharacterComponent != null)){
                Node node = entitySceneMap.requestNode(playerCharacterComponent.getEntity());
                CircleIndicatorComponent circleIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), CircleIndicatorComponent.class);
                if(circleIndicatorComponent != null){
                    float diameter = (2 * circleIndicatorComponent.getRadius());
                    float x = (circleIndicatorComponent.getX() - circleIndicatorComponent.getRadius());
                    float y = (circleIndicatorComponent.getY() + circleIndicatorComponent.getRadius());
                    currentIndicators.add(createGroundTexture("Textures/spell_indicators/circle.png", x, y, diameter, diameter));
                }
                RectangleIndicatorComponent rectangleIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), RectangleIndicatorComponent.class);
                if(rectangleIndicatorComponent != null){
                    float width = rectangleIndicatorComponent.getWidth();
                    float height = rectangleIndicatorComponent.getHeight();
                    float x = (rectangleIndicatorComponent.getX() - (rectangleIndicatorComponent.getWidth() / 2));
                    float y = (rectangleIndicatorComponent.getY() + rectangleIndicatorComponent.getHeight());
                    currentIndicators.add(createGroundTexture("Textures/spell_indicators/square.png", x, y, width, height));
                }
                for(Spatial indicator : currentIndicators){
                    node.attachChild(indicator);
                }
            }
            updateIndicator = false;
        }
    }
    
    public void showIndicator(int spellEntity){
        hideIndicator();
        currentSpellEntity = spellEntity;
        updateIndicator = true;
    }
    
    public void hideIndicator(){
        if(currentSpellEntity != -1){
            currentSpellEntity = -1;
            for(Spatial indicator : currentIndicators){
                indicator.removeFromParent();
            }
            currentIndicators.clear();
            updateIndicator = false;
        }
    }
    
    public static Spatial createGroundTexture(String textureFilePath, float width, float height){
        return createGroundTexture(textureFilePath, (width / -2), (height / 2), width, height);
    }
    
    public static Spatial createGroundTexture(String textureFilePath, float x, float y, float width, float height){
        Spatial texture = new Geometry(null, new Quad(width, height));
        texture.setLocalTranslation(x, 0, y);
        texture.rotate(JMonkeyUtil.getQuaternion_X(-90));
        Material material = MaterialFactory.generateLightingMaterial(textureFilePath);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        material.getAdditionalRenderState().setDepthTest(false);
        texture.setMaterial(material);
        texture.setUserData("layer", 1);
        return texture;
    }
}
