package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

public class BuffVisualisationSystem_Backporting extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Backporting(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "backporting");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        return new ModelObject(assetManager, "Models/backport_particles/skin.xml");
    }
}
