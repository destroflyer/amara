package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Scarlet_Passive extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Scarlet_Passive(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "scarlet_passive");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        Node node = new Node();
        Spatial texture = GroundTextures.create("Textures/effects/scarlet_passive.png", 3, 3);
        node.attachChild(texture);
        return node;
    }
}
