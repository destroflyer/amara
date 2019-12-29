/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.materials.MaterialFactory;
import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class ModelModifier_Tree_Log_Burning extends ModelModifier{

    @Override
    public void modify(RegisteredModel registeredModel){
        Spatial fire = MaterialFactory.getAssetManager().loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(new Vector3f(0, 1, 0));
        registeredModel.getNode().attachChild(fire);
    }
}
