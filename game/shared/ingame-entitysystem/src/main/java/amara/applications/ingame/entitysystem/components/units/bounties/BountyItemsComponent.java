/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.bounties;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class BountyItemsComponent {

    public BountyItemsComponent() {

    }

    public BountyItemsComponent(int... itemEntities) {
        this.itemEntities = itemEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] itemEntities;

    public int[] getItemEntities() {
        return itemEntities;
    }
}
