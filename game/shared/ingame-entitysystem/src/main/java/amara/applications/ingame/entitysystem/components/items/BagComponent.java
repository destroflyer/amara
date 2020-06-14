/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.items;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BagComponent {

    public BagComponent() {

    }

    public BagComponent(int... itemEntities) {
        this.itemEntities = itemEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] itemEntities;

    public int[] getItemEntities() {
        return itemEntities;
    }
}
