/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.interfaces;

import amara.engine.applications.masterserver.server.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
public interface MasterserverClientInterface{
    
    public abstract int getPlayerID();
    
    public abstract PlayerProfileData getPlayerProfile(int playerID);
}
