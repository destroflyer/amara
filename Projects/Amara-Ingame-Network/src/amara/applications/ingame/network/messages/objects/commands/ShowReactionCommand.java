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
public class ShowReactionCommand extends Command{

    public ShowReactionCommand(){
        
    }
    
    public ShowReactionCommand(String reaction){
        this.reaction = reaction;
    }
    private String reaction;

    public String getReaction(){
        return reaction;
    }
}
