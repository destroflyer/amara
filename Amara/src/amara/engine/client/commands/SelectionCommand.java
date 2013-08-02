/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.commands;

/**
 *
 * @author Carl
 */
public class SelectionCommand extends Command{

    public SelectionCommand(int entity){
        this.entity = entity;
    }
    private int entity;

    public int getEntity(){
        return entity;
    }
}
