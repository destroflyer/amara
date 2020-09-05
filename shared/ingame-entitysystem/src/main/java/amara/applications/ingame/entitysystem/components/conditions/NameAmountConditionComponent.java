/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.conditions;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class NameAmountConditionComponent {

    public NameAmountConditionComponent() {

    }

    public NameAmountConditionComponent(int maximum) {
        this.maximum = maximum;
    }
    private int maximum;

    public int getMaximum() {
        return maximum;
    }
}
