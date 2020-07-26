package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.applications.ingame.client.systems.visualisation.SpellIndicatorSystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.material.RenderState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_DosazUlt extends BuffVisualisationSystem {

    public BuffVisualisationSystem_DosazUlt(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "dosaz_ult");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        Spatial texture = SpellIndicatorSystem.createGroundTexture("Textures/effects/dosaz_ult_area.png", -7.5f, 7.5f, 15, 15, RenderState.BlendMode.AlphaAdditive);
        node.attachChild(texture);
        return node;
    }
}
