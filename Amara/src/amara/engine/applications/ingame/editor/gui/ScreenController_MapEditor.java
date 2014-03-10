/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.editor.gui;

import java.io.File;
import javax.swing.JOptionPane;
import com.jme3.app.state.AppStateManager;
import amara.Util;
import amara.engine.applications.ingame.editor.appstates.MapEditorAppState;
import amara.engine.appstates.*;
import amara.engine.gui.GameScreenController;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class ScreenController_MapEditor extends GameScreenController{
    
    public void openMap(){
        File file = Util.chooseFile(true, "./assets/Maps/", MapFileHandler.FILE_FILTER);
        if(file != null){
            AppStateManager stateManager = mainApplication.getStateManager();
            stateManager.detach(stateManager.getState(MapAppState.class));
            String mapName = file.getParentFile().getName();
            Map map = MapFileHandler.load(mapName);
            stateManager.attach(new MapAppState(map));
            stateManager.getState(MapObstaclesAppState.class).update();
        }
    }
    
    public void saveMap(){
        Map map = mainApplication.getStateManager().getState(MapAppState.class).getMap();
        File file = Util.chooseFile(false, "./assets/Maps/", MapFileHandler.FILE_FILTER);
        if(file != null){
            if(!file.getPath().endsWith("." + MapFileHandler.FILE_EXTENSION)){
                file = new File(file.getPath() + "." + MapFileHandler.FILE_EXTENSION);
            }
            MapFileHandler.saveFile(map, file);
        }
    }
    
    public void setAction_View(){
        getMapEditorAppState().setAction(MapEditorAppState.Action.VIEW);
    }
    
    public void setAction_PlaceHitboxCircle(){
        getMapEditorAppState().setAction(MapEditorAppState.Action.PLACE_HITBOX_CIRCLE);
    }
    
    public void setAction_PlaceHitboxCustom(){
        getMapEditorAppState().setAction(MapEditorAppState.Action.PLACE_HITBOX_CUSTOM);
    }
    
    public void setAction_PlaceVisual(){
        getMapEditorAppState().setAction(MapEditorAppState.Action.PLACE_VISUAL);
    }
    
    public void changeCameraAngle(){
        getMapEditorAppState().changeCameraAngle();
    }
    
    private MapEditorAppState getMapEditorAppState(){
        return mainApplication.getStateManager().getState(MapEditorAppState.class);
    }
    
    public void showCheesecakeMessage(){
        new Thread(new Runnable(){

            @Override
            public void run(){
                JOptionPane.showMessageDialog(null, "Cheesecake is awesome!");
            }
        }).start();
    }
}
