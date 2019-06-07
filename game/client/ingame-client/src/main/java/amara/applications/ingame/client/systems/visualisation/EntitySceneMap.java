/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public interface EntitySceneMap {
    
    Node requestNode(int entity);
    
    Node removeNode(int entity);
}
