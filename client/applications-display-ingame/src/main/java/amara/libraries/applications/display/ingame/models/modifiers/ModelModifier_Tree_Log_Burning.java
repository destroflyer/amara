package amara.libraries.applications.display.ingame.models.modifiers;

import amara.libraries.applications.display.models.ModelModifier;
import amara.libraries.applications.display.models.RegisteredModel;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class ModelModifier_Tree_Log_Burning extends ModelModifier {

    @Override
    public void modify(RegisteredModel registeredModel, AssetManager assetManager) {
        Spatial fire = assetManager.loadModel("Models/fireball/fireball.j3o");
        fire.setLocalTranslation(new Vector3f(0, 0.33f, 0));
        fire.setLocalScale(0.33f);
        registeredModel.getNode().attachChild(fire);
    }
}
