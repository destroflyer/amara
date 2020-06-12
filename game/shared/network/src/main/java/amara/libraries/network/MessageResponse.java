/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import java.util.LinkedList;
import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public class MessageResponse {

    public MessageResponse(int clientId) {
        this.clientId = clientId;
    }
    private int clientId;
    private LinkedList<MessageResponse_Entry> entries = new LinkedList<>();

    public void addBroadcastMessage(Message message) {
        entries.add(new MessageResponse_Entry(MessageResponse_Entry.Type.BROADCAST, message));
    }

    public void addAnswerMessage(Message message) {
        entries.add(new MessageResponse_Entry(MessageResponse_Entry.Type.ANSWER, message));
    }

    public int getClientId() {
        return clientId;
    }

    public LinkedList<MessageResponse_Entry> getEntries() {
        return entries;
    }
}
