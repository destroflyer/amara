/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import java.awt.EventQueue;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.Message_GameSelectionUpdate;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class UpdateGameSelectionBackend implements MessageBackend {

    public UpdateGameSelectionBackend(PanPlay panPlay) {
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameSelectionUpdate) {
            Message_GameSelectionUpdate message = (Message_GameSelectionUpdate) receivedMessage;
            final GameSelection gameSelection = message.getGameSelection();
            gameSelection.repairOnUnserialize();
            EventQueue.invokeLater(() -> {
                panPlay.getPanGameSelection().update(gameSelection);
                panPlay.displayGameSelectionPanel();
            });
        }
    }
}
