/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import amara.engine.client.commands.Command;

/**
 *
 * @author Carl
 */
public class PlayerCommand{

    public PlayerCommand(int clientID, Command command){
        this.clientID = clientID;
        this.command = command;
    }
    private int clientID;
    private Command command;

    public int getClientID(){
        return clientID;
    }

    public Command getCommand(){
        return command;
    }
}
