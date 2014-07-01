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
import amara.engine.settings.Settings;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;

/**
 *
 * @author Carl
 */
public class PlayerVisionDisplaySystem implements EntitySystem{

    public PlayerVisionDisplaySystem(int playerEntity, PostFilterAppState postFilterAppState, final PolyMapManager polyMapManager){
        this.playerEntity = playerEntity;
        this.postFilterAppState = postFilterAppState;
        this.polyMapManager = polyMapManager;
        fogOfWarFilter = new FogOfWarFilter(){

            @Override
            protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height){
                super.initFilter(manager, renderManager, viewPort, width, height);
                setMapSize((float) polyMapManager.getWidth(), (float) polyMapManager.getHeight());
            }
        };
        postFilterAppState.addFilter(fogOfWarFilter);
    }
    private int playerEntity;
    private PostFilterAppState postFilterAppState;
    private PolyMapManager polyMapManager;
    private FogOfWarFilter fogOfWarFilter;
    private float timeSinceLastUpdate;
    private boolean isUpdateNeeded;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        float updateInterval = Settings.getFloat("fog_of_war_update_interval");
        if(updateInterval != -1){
            timeSinceLastUpdate += deltaSeconds;
            if(timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval")){
                SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
                if(selectedUnitComponent != null){
                    int selectedEntity = selectedUnitComponent.getEntityID();
                    ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, PositionComponent.class);
                    if((observer.getNew().getComponent(selectedEntity, PositionComponent.class) != null)
                    || (observer.getChanged().getComponent(selectedEntity, PositionComponent.class) != null)){
                        isUpdateNeeded = true;
                    }
                    observer.reset();
                    if(isUpdateNeeded){
                        updateFog(entityWorld.getComponent(selectedEntity, PositionComponent.class).getPosition());
                        isUpdateNeeded = false;
                    }
                }
                timeSinceLastUpdate = 0;
            }
        }
    }
    
    private void updateFog(Vector2f playerUnitPosition){
        Point2D position = new Point2D(playerUnitPosition.getX(), playerUnitPosition.getY());
        Polygon sightPolygon = polyMapManager.sightPolygon(position, 30);
        float resolutionFactor = 1;
        PaintableImage paintableImage = new PaintableImage((int) (polyMapManager.getWidth() * resolutionFactor), (int) (polyMapManager.getHeight() * resolutionFactor));
        for(int x=0;x<paintableImage.getWidth();x++){
            for(int y=0;y<paintableImage.getHeight();y++){
                Point2D point = new Point2D(((double) x) / resolutionFactor, ((double) y) / resolutionFactor);
                boolean isVisible = sightPolygon.contains(point);
                paintableImage.setPixel_Red(x, y, (isVisible?255:80));
            }
        }
        Texture2D texture2D = new Texture2D();
        texture2D.setImage(paintableImage.getImage());
        fogOfWarFilter.setFog(texture2D);
    }
}
