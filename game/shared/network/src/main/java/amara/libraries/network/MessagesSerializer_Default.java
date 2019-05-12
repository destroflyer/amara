/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.network;

import com.jme3.network.serializing.Serializer;
import amara.libraries.network.messages.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer_Default{
    
    public static void registerClasses(){
        Serializer.registerClasses(            
            Message_ClientConnection.class,
            Message_ClientDisconnection.class
        );
    }
}
