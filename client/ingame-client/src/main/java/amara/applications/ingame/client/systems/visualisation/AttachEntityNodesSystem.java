package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsMinionComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsStructureComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.scene.Node;

import java.util.Map;

public class AttachEntityNodesSystem implements EntitySystem {

    public AttachEntityNodesSystem(EntitySceneMap entitySceneMap, Node nodeVisibleToMouse, Node nodeNotVisibleToMouse) {
        this.entitySceneMap = entitySceneMap;
        this.nodeVisibleToMouse = nodeVisibleToMouse;
        this.nodeNotVisibleToMouse = nodeNotVisibleToMouse;
    }
    private EntitySceneMap entitySceneMap;
    private Node nodeVisibleToMouse;
    private Node nodeNotVisibleToMouse;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (Map.Entry<Integer, Node> entry : entitySceneMap.getNodes().entrySet()) {
            int entity = entry.getKey();
            Node node = entry.getValue();
            if (isHoverable(entityWorld, entity)) {
                nodeVisibleToMouse.attachChild(node);
            } else {
                nodeNotVisibleToMouse.attachChild(node);
            }
        }
    }

    private static boolean isHoverable(EntityWorld entityWorld, int entity) {
        return isInspectable(entityWorld, entity) || entityWorld.hasComponent(entity, ShopItemsComponent.class);
    }

    public static boolean isInspectable(EntityWorld entityWorld, int entity) {
        return entityWorld.hasAnyComponent(entity, IsCharacterComponent.class, IsMinionComponent.class, IsMonsterComponent.class, IsStructureComponent.class);
    }
}
