/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
@Serializable
public class ShopGoldExpensesComponent {

    public ShopGoldExpensesComponent() {

    }

    public ShopGoldExpensesComponent(HashMap<String, Float> goldPerCategory) {
        this.goldPerCategory = goldPerCategory;
    }
    private HashMap<String, Float> goldPerCategory;

    public HashMap<String, Float> getGoldPerCategory() {
        return goldPerCategory;
    }
}
