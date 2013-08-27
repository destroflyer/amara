/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.items;

/**
 *
 * @author Carl
 */
public class InventoryComponent{

    public InventoryComponent(int[] itemEntities){
        this.itemEntities = itemEntities;
    }
    private int[] itemEntities;

    public int[] getItemEntities(){
        return itemEntities;
    }
}
