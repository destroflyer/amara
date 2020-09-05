/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.*;
import com.jme3.effect.ParticleEmitter;

/**
 *
 * @author Carl
 */
public class ModelModifier_EnergyProjectile extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        ParticleEmitter particleEmitter = (ParticleEmitter) registeredModel.getNode().getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
