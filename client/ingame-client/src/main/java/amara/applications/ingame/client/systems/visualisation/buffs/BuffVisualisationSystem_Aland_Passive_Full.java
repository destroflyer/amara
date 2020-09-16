package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.FadeOutControl;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Aland_Passive_Full extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Aland_Passive_Full(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "aland_passive_full");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        Geometry geometry = GroundTextures.create("Textures/effects/aland_passive_full.png", -1.5f, 1.5f, 3, 3);
        geometry.addControl(new FadeOutControl(2));
        node.attachChild(geometry);
        return node;
    }
}
