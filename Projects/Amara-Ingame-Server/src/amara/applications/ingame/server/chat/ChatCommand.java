/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat;

import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class ChatCommand{
    
    private String responseMessage;
    
    public abstract void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer);

    public void setResponseMessage(String responseMessage){
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage(){
        return responseMessage;
    }
}
