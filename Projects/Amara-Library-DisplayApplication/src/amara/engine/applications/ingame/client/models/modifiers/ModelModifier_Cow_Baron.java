/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import com.jme3.animation.SkeletonControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.*;
import amara.engine.materials.MaterialFactory;

/**
 *
 * @author Carl
 */
public class ModelModifier_Cow_Baron extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        SkeletonControl skeletonControl = modelObject.getModelSpatial().getControl(SkeletonControl.class);
        Node headNode = skeletonControl.getAttachmentsNode("head");
        float width = 1.6f;
        float height = (width * (4f / 5));
        Quad quad = new Quad(width, height, true);
        Spatial plane = new Geometry(null, quad);
        plane.setLocalTranslation((width / -2), 0.3f, -0.73f);
        JMonkeyUtil.setLocalRotation(plane, new Vector3f(0, -0.7f, 1));
        Material material = MaterialFactory.generateUnshadedMaterial("Models/cow/resources/baron.png");
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        plane.setMaterial(material);
        headNode.attachChild(plane);
    }
}
