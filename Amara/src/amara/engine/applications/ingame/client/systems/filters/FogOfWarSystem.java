/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.filters;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.math.Vector2f;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture2D;
import amara.engine.appstates.PostFilterAppState;
import amara.engine.filters.FogOfWarFilter;
import amara.engine.materials.PaintableImage;
import amara.engine.materials.Raster;
import amara.engine.settings.Settings;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.entitysystem.systems.physics.shapes.Vector2D;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;

/**
 *
 * @author Carl
 */
public class FogOfWarSystem implements EntitySystem{

    public FogOfWarSystem(int playerEntity, PostFilterAppState postFilterAppState, final PolyMapManager polyMapManager){
        this.playerEntity = playerEntity;
        this.postFilterAppState = postFilterAppState;
        this.polyMapManager = polyMapManager;
        fogImage = new PaintableImage((int) (polyMapManager.getWidth() * resolutionFactor), (int) (polyMapManager.getHeight() * resolutionFactor));
        fogRaster = new Raster(fogImage, resolutionFactor, 80, 255);
        fogOfWarFilter = new FogOfWarFilter(){

            @Override
            protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height){
                super.initFilter(manager, renderManager, viewPort, width, height);
                setMapSize((float) polyMapManager.getWidth(), (float) polyMapManager.getHeight());
            }
        };
        postFilterAppState.addFilter(fogOfWarFilter);
        tmpPlayerSightFogImageData = new byte[fogImage.getData().length];
    }
    private final float resolutionFactor = 1;
    private int playerEntity;
    private PostFilterAppState postFilterAppState;
    private PolyMapManager polyMapManager;
    private PaintableImage fogImage;
    private Texture2D fogTexture = new Texture2D();
    private Raster fogRaster;
    private FogOfWarFilter fogOfWarFilter;
    private float timeSinceLastUpdate;
    private boolean isUpdateNeeded;
    private boolean displayMapSight;
    private byte[] tmpPlayerSightFogImageData;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(!displayMapSight){
            float updateInterval = Settings.getFloat("fog_of_war_update_interval");
            if(updateInterval != -1){
                timeSinceLastUpdate += deltaSeconds;
                if(timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval")){
                    SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
                    if(selectedUnitComponent != null){
                        int selectedEntity = selectedUnitComponent.getEntity();
                        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, PositionComponent.class);
                        if((observer.getNew().getComponent(selectedEntity, PositionComponent.class) != null)
                        || (observer.getChanged().getComponent(selectedEntity, PositionComponent.class) != null)){
                            isUpdateNeeded = true;
                        }
                        observer.reset();
                        if(isUpdateNeeded){
                            updateFogTexture_PlayerSight(entityWorld.getComponent(selectedEntity, PositionComponent.class).getPosition());
                            isUpdateNeeded = false;
                        }
                    }
                    timeSinceLastUpdate = 0;
                }
            }
        }
    }
    
    public void setDisplayMapSight(boolean displayMapSight){
        if(displayMapSight != this.displayMapSight){
            this.displayMapSight = displayMapSight;
            if(displayMapSight){
                System.arraycopy(fogImage.getData(), 0, tmpPlayerSightFogImageData, 0, fogImage.getData().length);
                updateFogTexture_MapSight();
            }
            else{
                fogImage.setData(tmpPlayerSightFogImageData);
                onFogImageUpdated();
            }
        }
    }

    public boolean isDisplayMapSight(){
        return displayMapSight;
    }
    
    private void updateFogTexture_MapSight(){
        resetFogTexture();
        Polygon sightPolygon = polyMapManager.getNavigationPolygon(0);
        sightPolygon.rasterize(fogRaster);
        onFogImageUpdated();
    }
    
    private void updateFogTexture_PlayerSight(Vector2f playerUnitPosition){
        resetFogTexture();
        Vector2D position = new Vector2D(playerUnitPosition.getX(), playerUnitPosition.getY());
        double sightRange = 30;
        Polygon sightPolygon = polyMapManager.sightPolygon(position, sightRange);
        sightPolygon.rasterize(fogRaster, position, sightRange);
        onFogImageUpdated();
    }
    
    private void resetFogTexture(){
        for(int x=0;x<fogImage.getWidth();x++){
            for(int y=0;y<fogImage.getHeight();y++){
                fogImage.setPixel_Red(x, y, 80);
            }
        }
    }
    
    private void onFogImageUpdated(){
        fogTexture.setImage(fogImage.getImage());
        fogOfWarFilter.setFog(fogTexture);
    }
}
