/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Maria_Ult extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        Node node = (Node) modelObject.getModelSpatial();
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
