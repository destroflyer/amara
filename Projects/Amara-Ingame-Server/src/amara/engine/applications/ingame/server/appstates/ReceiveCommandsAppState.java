/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.Queue;
import amara.engine.applications.*;
import amara.engine.applications.ingame.server.network.backends.ReceiveCommandsBackend;
import amara.engine.appstates.NetworkServerAppState;
import amara.engine.network.NetworkServer;
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
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new ReceiveCommandsBackend(this));
    }
    
    public void onCommandReceived(final PlayerCommand playerCommand){
        mainApplication.enqueueTask(new Runnable(){

            @Override
            public void run(){
                playerCommandsQueue.add(playerCommand);
            }
        });
    }

    public Queue<PlayerCommand> getPlayerCommandsQueue(){
        return playerCommandsQueue;
    }
}
