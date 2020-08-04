package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.BubbleUtil;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Bubble_Default extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Bubble_Default(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "bubble");
    }

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        return BubbleUtil.createDefault();
    }
}
