/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Maria_Ult extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel) {
        ParticleEmitter particleEmitter = (ParticleEmitter) registeredModel.getNode().getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
