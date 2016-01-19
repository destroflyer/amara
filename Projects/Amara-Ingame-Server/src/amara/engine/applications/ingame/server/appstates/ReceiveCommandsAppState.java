/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.core.Queue;
import amara.engine.applications.ingame.server.network.backends.ReceiveCommandsBackend;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.network.NetworkServer;

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
