/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.interfaces;

import amara.engine.applications.HeadlessAppState;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.game.games.Game;

/**
 *
 * @author Carl
 */
public interface MasterserverServerApplicationInterface{
    
    public abstract <T extends HeadlessAppState> T getState(Class<T> stateClass);
    
    public abstract void onGameServerInitialized(Game game);
    
    public abstract void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception);
    
    public abstract void onGameOver(IngameServerApplication ingameServerApplication);
}
