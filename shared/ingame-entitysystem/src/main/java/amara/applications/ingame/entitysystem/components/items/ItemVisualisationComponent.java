/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.items;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ItemVisualisationComponent {

    public ItemVisualisationComponent() {

    }

    public ItemVisualisationComponent(String name) {
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }
}
