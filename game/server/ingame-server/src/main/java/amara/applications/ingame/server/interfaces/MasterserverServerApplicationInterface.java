/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.interfaces;

import amara.applications.ingame.server.IngameServerApplication;
import amara.libraries.applications.headless.applications.HeadlessAppState;

/**
 *
 * @author Carl
 */
public interface MasterserverServerApplicationInterface {

    <T extends HeadlessAppState> T getState(Class<T> stateClass);

    void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception);

    void onGameOver(IngameServerApplication ingameServerApplication);
}
