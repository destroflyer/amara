package amara.tools.editors.map.gui;

import java.io.File;
import javax.swing.JOptionPane;

import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.core.Util;
import amara.core.files.FileAssets;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.applications.display.ingame.maps.TerrainAlphamap;
import amara.tools.editors.map.appstates.MapEditorAppState;
import de.lessvoid.nifty.elements.Element;

public class ScreenController_MapEditor extends GameScreenController{

    public ScreenController_MapEditor() {
        super("start");
    }

    public void openMap() {
        File file = Util.chooseFile(true, FileAssets.ROOT + "Maps/", MapFileHandler.FILE_FILTER);
        if (file != null) {
            final String mapName = file.getParentFile().getName();
            mainApplication.enqueue(() -> {
                AppStateManager stateManager = mainApplication.getStateManager();
                stateManager.detach(stateManager.getState(MapAppState.class));
                Map map = MapFileHandler.load(mapName);
                stateManager.attach(new MapAppState(map));
                stateManager.getState(MapObstaclesAppState.class).update();
            });
        }
    }

    public void saveMap(){
        MapAppState mapAppState = mainApplication.getStateManager().getState(MapAppState.class);
        File file = Util.chooseFile(false, FileAssets.ROOT + "Maps/", MapFileHandler.FILE_FILTER);
        if(file != null){
            if(!file.getPath().endsWith("." + MapFileHandler.FILE_EXTENSION)){
                file = new File(file.getPath() + "." + MapFileHandler.FILE_EXTENSION);
            }
            MapFileHandler.saveFile(mapAppState.getMap(), file);
            for(TerrainAlphamap alphamap : mapAppState.getMapTerrain().getAlphamaps()){
                alphamap.saveFile();
            }
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
    
    public void setAction_PaintTerrainAlphamap(){
        getMapEditorAppState().setAction(MapEditorAppState.Action.PAINT_TERRAIN_ALPHAMAP);
    }

    public void changeCameraAngle(){
        getMapEditorAppState().changeCameraAngle();
    }

    public void mirrorObstacles() {
        getMapEditorAppState().mirrorObstacles();
    }

    private MapEditorAppState getMapEditorAppState(){
        return mainApplication.getStateManager().getState(MapEditorAppState.class);
    }

    public void setToolInformation(String toolInformation){
        getTextRenderer("tool_information").setText(toolInformation);
    }
    
    public void showToolInformationImage(String imagePath){
        getImageRenderer("tool_information_image").setImage(createImage(imagePath));
        getElementByID("tool_information_image").show();
    }
    
    public void hideToolInformationImage(){
        getElementByID("tool_information_image").hide();
    }
    
    public void setHoveredLocation(Vector2f hoveredLocation){
        getTextRenderer("hovered_coordinates").setText("(" + hoveredLocation.getX() + ", " + hoveredLocation.getY() + ")");
    }
    
    public void toggleHoveredCoordinates(){
        Element lblHoveredCoordinates = getElementByID("hovered_coordinates");
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
