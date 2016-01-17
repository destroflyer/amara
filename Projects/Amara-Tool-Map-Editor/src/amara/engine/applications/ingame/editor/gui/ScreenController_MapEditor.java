/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.editor.gui;

import java.io.File;
import javax.swing.JOptionPane;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import de.lessvoid.nifty.elements.Element;
import amara.core.Util;
import amara.core.files.FileAssets;
import amara.engine.applications.ingame.editor.appstates.MapEditorAppState;
import amara.engine.appstates.*;
import amara.engine.gui.GameScreenController;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class ScreenController_MapEditor extends GameScreenController{

    public ScreenController_MapEditor(){
        super("start");
    }
    
    public void openMap(){
        File file = Util.chooseFile(true, FileAssets.ROOT + "Maps/", MapFileHandler.FILE_FILTER);
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
        File file = Util.chooseFile(false, FileAssets.ROOT + "Maps/", MapFileHandler.FILE_FILTER);
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

    public void setHoveredLocation(Vector2f hoveredLocation){
        getTextRenderer("hoveredCoordinates").setText("(" + hoveredLocation.getX() + ", " + hoveredLocation.getY() + ")");
    }
    
    public void showHoveredCoordinates(){
        Element lblHoveredCoordinates = getElementByID("hoveredCoordinates");
        lblHoveredCoordinates.setVisible(!lblHoveredCoordinates.isVisible());
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
