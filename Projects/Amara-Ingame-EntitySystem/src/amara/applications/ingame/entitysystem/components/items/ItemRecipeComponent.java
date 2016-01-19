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
public class ItemRecipeComponent{

    public ItemRecipeComponent(){
        
    }

    public ItemRecipeComponent(int gold, String... itemIDs){
        this.gold = gold;
        this.itemIDs = itemIDs;
    }
    private int gold;
    private String[] itemIDs;

    public int getGold(){
        return gold;
    }

    public String[] getItemIDs(){
        return itemIDs;
    }
}
