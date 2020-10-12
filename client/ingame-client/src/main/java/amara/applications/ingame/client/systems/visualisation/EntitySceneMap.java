package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;

import java.util.HashMap;

public class EntitySceneMap {

    private HashMap<Integer, Node> nodes = new HashMap<>();

    public Node requestNode(int entity) {
        return nodes.computeIfAbsent(entity, e -> {
            Node node = new Node();
            node.setUserData("entity", entity);
            return node;
        });
    }

    public void removeNode(int entity) {
        Node node = nodes.remove(entity);
        if ((node != null) && (node.getParent() != null)) {
            node.getParent().detachChild(node);
        }
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }
}
