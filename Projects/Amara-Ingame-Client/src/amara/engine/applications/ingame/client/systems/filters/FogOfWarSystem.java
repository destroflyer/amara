/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.filters;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture2D;
import amara.core.settings.Settings;
import amara.engine.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.engine.appstates.PostFilterAppState;
import amara.engine.filters.FogOfWarFilter;
import amara.engine.materials.PaintableImage;
import amara.engine.materials.Raster;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.SightRangeComponent;
import amara.game.entitysystem.components.units.TeamComponent;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.intersectionHelper.PolyMapManager;
import amara.libraries.physics.shapes.Vector2D;
import amara.libraries.physics.shapes.PolygonMath.Polygon;

/**
 *
 * @author Carl
 */
public class FogOfWarSystem implements EntitySystem{

    public FogOfWarSystem(PlayerTeamSystem playerTeamSystem, PostFilterAppState postFilterAppState, final PolyMapManager polyMapManager){
        this.playerTeamSystem = playerTeamSystem;
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
    private PlayerTeamSystem playerTeamSystem;
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
            timeSinceLastUpdate += deltaSeconds;
            if(timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval")){
                ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class);
                for(int entity : observer.getNew().getEntitiesWithAll(PositionComponent.class)){
                    checkChangedPositionComponent(entityWorld, entity);
                }
                for(int entity : observer.getChanged().getEntitiesWithAll(PositionComponent.class)){
                    checkChangedPositionComponent(entityWorld, entity);
                }
                if(isUpdateNeeded){
                    updateFogTexture_PlayerSight(entityWorld);
                    isUpdateNeeded = false;
                }
                timeSinceLastUpdate = 0;
            }
        }
    }
    
    private void checkChangedPositionComponent(EntityWorld entityWorld, int entity){
        if(entityWorld.hasComponent(entity, SightRangeComponent.class) && playerTeamSystem.isAllied(entityWorld, entity)){
            isUpdateNeeded = true;
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
    
    private void updateFogTexture_PlayerSight(EntityWorld entityWorld){
        resetFogTexture();
        for(int entity : entityWorld.getEntitiesWithAll(TeamComponent.class, PositionComponent.class, SightRangeComponent.class)){
            if(playerTeamSystem.isAllied(entityWorld, entity)){
                PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
                Vector2D position = new Vector2D(positionComponent.getPosition().getX(), positionComponent.getPosition().getY());
                double sightRange = entityWorld.getComponent(entity, SightRangeComponent.class).getRange();
                Polygon sightPolygon = polyMapManager.sightPolygon(position, sightRange);
                sightPolygon.rasterize(fogRaster, position, sightRange);
            }
        }
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
