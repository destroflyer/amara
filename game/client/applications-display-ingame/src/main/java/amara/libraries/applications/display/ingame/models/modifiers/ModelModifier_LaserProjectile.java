/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.effect.ParticleEmitter;

/**
 *
 * @author Carl
 */
public class ModelModifier_LaserProjectile extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        ParticleEmitter particleEmitter = (ParticleEmitter) registeredModel.getNode().getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
