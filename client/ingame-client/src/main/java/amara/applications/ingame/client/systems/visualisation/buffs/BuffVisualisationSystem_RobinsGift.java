package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;

public class BuffVisualisationSystem_RobinsGift extends BuffVisualisationSystem {

    public BuffVisualisationSystem_RobinsGift(EntitySceneMap entitySceneMap, AssetManager assetManager) {
        super(entitySceneMap, assetManager, "robins_gift");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        Spatial texture = GroundTextures.create(assetManager, "Textures/effects/robins_gift_mark.png", 3.5f, 3.5f);
        node.attachChild(texture);
        return node;
    }
}
