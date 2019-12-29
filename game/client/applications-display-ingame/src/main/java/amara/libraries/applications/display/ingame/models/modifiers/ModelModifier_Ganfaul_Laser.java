/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public class ModelModifier_Ganfaul_Laser extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        Node node = registeredModel.getNode();
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(0.87f);
    }
}
