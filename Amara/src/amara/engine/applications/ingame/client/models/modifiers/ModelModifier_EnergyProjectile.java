/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.models.modifiers;

import amara.engine.applications.ingame.client.models.*;

/**
 *
 * @author Carl
 */
public class ModelModifier_EnergyProjectile extends ModelModifier{

    @Override
    public void modify(ModelObject modelObject){
        modelObject.getModelSpatial().updateLogicalState(10);
    }
}
