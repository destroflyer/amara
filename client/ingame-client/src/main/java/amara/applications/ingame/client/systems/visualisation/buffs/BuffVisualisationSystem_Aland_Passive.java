package amara.applications.ingame.client.systems.visualisation.buffs;

import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.ingame.models.util.GroundTextures;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class BuffVisualisationSystem_Aland_Passive extends BuffVisualisationSystem {

    public BuffVisualisationSystem_Aland_Passive(EntitySceneMap entitySceneMap) {
        super(entitySceneMap, "aland_passive");
    }
    private final static String NODE_NAME_PREFIX_STACK = "stack";

    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity) {
        Node node = new Node();
        node.attachChild(createStackVisualisation(1));
        node.attachChild(createStackVisualisation(2));
        return node;
    }

    private Geometry createStackVisualisation(int stackIndex) {
        Geometry texture = GroundTextures.create("Textures/effects/aland_passive_stack_" + stackIndex + ".png", -1.5f, 1.5f, 3, 3);
        texture.setName(NODE_NAME_PREFIX_STACK + stackIndex);
        texture.setCullHint(Spatial.CullHint.Always);
        return texture;
    }

    @Override
    protected void updateBuffVisualisationStacks(Spatial visualAttachment, int stacks) {
        super.updateBuffVisualisationStacks(visualAttachment, stacks);
        Node node = (Node) visualAttachment;
        for (int i = 1; i < 3; i++) {
            Spatial stackVisualisation = node.getChild(NODE_NAME_PREFIX_STACK + i);
            stackVisualisation.setCullHint((stacks == i) ? Spatial.CullHint.Inherit : Spatial.CullHint.Always);
        }
    }
}
