/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class EntitySceneMap {

    public EntitySceneMap(Node parentNode) {
        this.parentNode = parentNode;
    }
    private Node parentNode;
    private HashMap<Integer, Node> nodes = new HashMap<>();

    public Node requestNode(int entity) {
        return nodes.computeIfAbsent(entity, e -> {
            Node node = new Node();
            node.setUserData("entity", entity);
            parentNode.attachChild(node);
            return node;
        });
    }

    public void removeNode(int entity) {
        Node node = nodes.remove(entity);
        if (node != null) {
            parentNode.detachChild(node);
        }
    }
}
