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
public class ItemCategoriesComponent {

    public ItemCategoriesComponent() {

    }

    public ItemCategoriesComponent(String... categories) {
        this.categories = categories;
    }
    private String[] categories;

    public String[] getCategories() {
        return categories;
    }
}
