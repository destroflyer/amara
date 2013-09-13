/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public interface EntitySceneMap{
    
    public abstract Node requestNode(int entity);
    
    public abstract Node removeNode(int entity);
}
