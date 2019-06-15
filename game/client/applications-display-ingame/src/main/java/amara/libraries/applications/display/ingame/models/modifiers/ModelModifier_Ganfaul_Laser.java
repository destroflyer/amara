/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.ModelObject;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public class ModelModifier_Ganfaul_Laser extends ModelModifier {

    @Override
    public void modify(ModelObject modelObject) {
        Node node = (Node) modelObject.getModelSpatial();
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(0.87f);
    }
}
