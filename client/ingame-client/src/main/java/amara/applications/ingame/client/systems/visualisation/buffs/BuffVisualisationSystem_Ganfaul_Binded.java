package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

public class BuffVisualisationSystem_Ganfaul_Binded extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Ganfaul_Binded(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "ganfaul_binded");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Spatial particles = new ModelObject(assetManager, "Models/ganfaul_binding_particles/skin.xml");
        particles.setLocalTranslation(0, 3.7f, 0);
        return particles;
    }
}
