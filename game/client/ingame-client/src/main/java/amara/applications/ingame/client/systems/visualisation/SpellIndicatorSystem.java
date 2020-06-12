/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import java.util.LinkedList;
import java.util.function.Supplier;

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

    public SpellIndicatorSystem(Supplier<Integer> playerEntity, EntitySceneMap entitySceneMap){
        this.playerEntity = playerEntity;
        this.entitySceneMap = entitySceneMap;
    }
    private Supplier<Integer> playerEntity;
    private EntitySceneMap entitySceneMap;
    private int currentSpellEntity = -1;
    private LinkedList<Spatial> currentIndicators = new LinkedList<>();
    private boolean updateIndicator;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(updateIndicator){
            SpellIndicatorComponent spellIndicatorComponent = entityWorld.getComponent(currentSpellEntity, SpellIndicatorComponent.class);
            PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity.get(), PlayerCharacterComponent.class);
            if((spellIndicatorComponent != null) && (playerCharacterComponent != null)){
                Node node = entitySceneMap.requestNode(playerCharacterComponent.getEntity());
                CircularIndicatorComponent circularIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), CircularIndicatorComponent.class);
                if(circularIndicatorComponent != null){
                    float diameter = (2 * circularIndicatorComponent.getRadius());
                    float x = (circularIndicatorComponent.getX() - circularIndicatorComponent.getRadius());
                    float y = (circularIndicatorComponent.getY() + circularIndicatorComponent.getRadius());
                    currentIndicators.add(createGroundTexture("Textures/spell_indicators/circular.png", x, y, diameter, diameter, RenderState.BlendMode.AlphaAdditive));
                }
                LinearIndicatorComponent linearIndicatorComponent = entityWorld.getComponent(spellIndicatorComponent.getIndicatorEntity(), LinearIndicatorComponent.class);
                if(linearIndicatorComponent != null){
                    float width = linearIndicatorComponent.getWidth();
                    float heightTarget = Math.min(5, linearIndicatorComponent.getHeight());
                    float heightBase = (linearIndicatorComponent.getHeight() - heightTarget);
                    float x = (linearIndicatorComponent.getX() - (linearIndicatorComponent.getWidth() / 2));
                    float yBase = (linearIndicatorComponent.getY() + heightBase);
                    float yTarget = (linearIndicatorComponent.getY() + heightBase + heightTarget);
                    Geometry indicatorBase = createGroundTexture("Textures/spell_indicators/linear_base.png", x, yBase, width, heightBase, RenderState.BlendMode.AlphaAdditive);
                    Geometry indicatorTarget = createGroundTexture("Textures/spell_indicators/linear_target.png", x, yTarget, width, heightTarget, RenderState.BlendMode.AlphaAdditive);
                    // Ensure that the textures connect seamless (The textures have a high enough resolution that it still looks ok)
                    MaterialFactory.setFilter_Nearest(indicatorBase.getMaterial());
                    MaterialFactory.setFilter_Nearest(indicatorTarget.getMaterial());
                    currentIndicators.add(indicatorBase);
                    currentIndicators.add(indicatorTarget);
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
        return createGroundTexture(textureFilePath, x, y, width, height, RenderState.BlendMode.Alpha);
    }
    
    public static Geometry createGroundTexture(String textureFilePath, float x, float y, float width, float height, RenderState.BlendMode blendMode){
        Geometry geometry = new Geometry(null, new Quad(width, height));
        geometry.setLocalTranslation(x, 0, y);
        geometry.rotate(JMonkeyUtil.getQuaternion_X(-90));
        Material material = MaterialFactory.generateLightingMaterial(textureFilePath);
        material.getAdditionalRenderState().setBlendMode(blendMode);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        material.getAdditionalRenderState().setDepthTest(false);
        geometry.setMaterial(material);
        geometry.setUserData("layer", 2);
        return geometry;
    }
}
