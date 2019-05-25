/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.shop;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ShopItemsComponent {

    public ShopItemsComponent(){

    }

    public ShopItemsComponent(String... itemTemplateNames){
        this.itemTemplateNames = itemTemplateNames;
    }
    private String[] itemTemplateNames;

    public String[] getItemTemplateNames() {
        return itemTemplateNames;
    }
}
