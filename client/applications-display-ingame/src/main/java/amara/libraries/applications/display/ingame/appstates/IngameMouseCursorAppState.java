/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.cursors.plugins.JmeCursor;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;

/**
 *
 * @author Carl
 */
public class IngameMouseCursorAppState extends BaseDisplayAppState<DisplayApplication>{

    private JmeCursor cursorDefault;
    private JmeCursor cursorEnemy;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        MouseCursorAppState mouseCursorAppState = getAppState(MouseCursorAppState.class);
        cursorDefault = mouseCursorAppState.loadCursor("Interface/cursors/glove_silver.cur");
        cursorEnemy = mouseCursorAppState.loadCursor("Interface/cursors/glove_red.cur");
        setCursor_Default();
    }
    
    public void setCursor_Default(){
        setCursor(cursorDefault);
    }
    
    public void setCursor_Enemy(){
        setCursor(cursorEnemy);
    }
    
    private void setCursor(JmeCursor cursor){
        getAppState(MouseCursorAppState.class).setCursor(cursor);
    }
}
