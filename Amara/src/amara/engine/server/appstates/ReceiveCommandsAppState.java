/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.server.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import amara.Queue;
import amara.engine.server.network.NetworkServer;
import amara.engine.server.network.backends.*;
import amara.game.entitysystem.systems.commands.PlayerCommand;

/**
 *
 * @author Carl
 */
public class ReceiveCommandsAppState extends ServerBaseAppState{

    public ReceiveCommandsAppState(){
        
    }
    private Queue<PlayerCommand> playerCommandsQueue = new Queue<PlayerCommand>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceiveCommandsBackend(playerCommandsQueue));
    }

    public Queue<PlayerCommand> getPlayerCommandsQueue(){
        return playerCommandsQueue;
    }
}
