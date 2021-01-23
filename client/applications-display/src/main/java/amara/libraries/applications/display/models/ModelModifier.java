package amara.libraries.applications.display.models;

import com.jme3.asset.AssetManager;

public abstract class ModelModifier {

    public abstract void modify(RegisteredModel registeredModel, AssetManager assetManager);
}
