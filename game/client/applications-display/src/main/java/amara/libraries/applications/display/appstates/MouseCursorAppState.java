/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.appstates;

import com.jme3.cursors.plugins.JmeCursor;

/**
 *
 * @author Carl
 */
public class MouseCursorAppState extends BaseDisplayAppState{
    
    public void setCursor(String cursorPath){
        JmeCursor cursor = loadCursor(cursorPath);
        setCursor(cursor);
    }
    
    public JmeCursor loadCursor(String cursorPath){
        return (JmeCursor) mainApplication.getAssetManager().loadAsset(cursorPath);
    }
    
    public void setCursor(JmeCursor cursor){
        mainApplication.getInputManager().setMouseCursor(cursor);
    }
}
