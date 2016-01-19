/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import com.jme3.network.Message;

/**
 *
 * @author Carl
 */
public interface MessageBackend{
    
    public abstract void onMessageReceived(Message receivedMessage, MessageResponse messageResponse);
}
