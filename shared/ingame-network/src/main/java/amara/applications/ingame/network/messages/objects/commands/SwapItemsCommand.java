/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SwapItemsCommand extends Command {

    public SwapItemsCommand() {

    }

    public SwapItemsCommand(ItemIndex itemIndex1, ItemIndex itemIndex2) {
        this.itemIndex1 = itemIndex1;
        this.itemIndex2 = itemIndex2;
    }
    private ItemIndex itemIndex1;
    private ItemIndex itemIndex2;

    public ItemIndex getItemIndex1() {
        return itemIndex1;
    }

    public ItemIndex getItemIndex2() {
        return itemIndex2;
    }
}
