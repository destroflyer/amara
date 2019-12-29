/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_Ganfaul_Binding extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Node node = registeredModel.getNode();
        Spatial particles = new ModelObject(null, "Models/ganfaul_binding_particles/skin.xml");
        node.attachChild(particles);
        ParticleEmitter particleEmitter = (ParticleEmitter) node.getChild(0);
        particleEmitter.updateLogicalState(10);
    }
}
