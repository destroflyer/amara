/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.interfaces;

import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.libraries.applications.headless.applications.HeadlessAppState;

/**
 *
 * @author Carl
 */
public interface MasterserverClientInterface{
    
    public abstract <T extends HeadlessAppState> T getState(Class<T> stateClass);
    
    public abstract int getPlayerID();
    
    public abstract PlayerProfileData getPlayerProfile(int playerID);
}
