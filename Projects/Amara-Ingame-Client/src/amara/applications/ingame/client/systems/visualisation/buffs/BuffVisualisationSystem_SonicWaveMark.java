/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Spatial;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_SonicWaveMark extends BuffVisualisationSystem{

    public BuffVisualisationSystem_SonicWaveMark(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "sonic_wave_mark");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        Node node = new Node();
        Spatial texture = createGroundTexture("Textures/effects/sonic_wave_mark.png", 3.5f, 3.5f);
        node.attachChild(texture);
        return node;
    }
    
    public static Spatial createGroundTexture(String textureFilePath, float width, float height){
        Spatial texture = new Geometry(null, new Quad(width, height));
        texture.setLocalTranslation((width / -2), 0, (height / 2));
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