package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Dosaz_Teleport extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Dosaz_Teleport(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "dosaz_teleport");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        Geometry geometry = GroundTextures.create("Textures/effects/dosaz_teleport_marker.png", -2.5f, 2.5f, 5, 5, RenderState.BlendMode.AlphaAdditive);
        node.attachChild(geometry);
        return node;
    }
}
