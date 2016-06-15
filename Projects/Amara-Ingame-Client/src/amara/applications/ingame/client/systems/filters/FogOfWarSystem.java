/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.shared.maps.MapPhysicsInformation;
import amara.core.settings.Settings;
import amara.libraries.applications.display.appstates.PostFilterAppState;
import amara.libraries.applications.display.filters.FogOfWarFilter;
import amara.libraries.applications.display.materials.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.PolyHelper;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;

/**
 *
 * @author Carl
 */
public class FogOfWarSystem implements EntitySystem{

    public FogOfWarSystem(PlayerTeamSystem playerTeamSystem, PostFilterAppState postFilterAppState, final MapPhysicsInformation mapPhysicsInformation){
        this.playerTeamSystem = playerTeamSystem;
        this.postFilterAppState = postFilterAppState;
        this.mapPhysicsInformation = mapPhysicsInformation;
        mapBorderPoints = new Vector2D[]{
            new Vector2D(0, 0),
            new Vector2D(0, mapPhysicsInformation.getHeight()),
            new Vector2D(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight()),
            new Vector2D(mapPhysicsInformation.getWidth(), 0)
        };
        float resolutionFactor = Settings.getFloat("fog_of_war_resolution");
        fogImage = new PaintableImage((int) (mapPhysicsInformation.getWidth() * resolutionFactor), (int) (mapPhysicsInformation.getHeight() * resolutionFactor));
        fogRaster = new Raster(fogImage, resolutionFactor, 80, 255);
        fogOfWarFilter = new FogOfWarFilter(){

            @Override
            protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height){
                super.initFilter(manager, renderManager, viewPort, width, height);
                setMapSize(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight());
            }
        };
        postFilterAppState.addFilter(fogOfWarFilter);
        tmpPlayerSightFogImageData = new byte[fogImage.getData().length];
    }
    private PlayerTeamSystem playerTeamSystem;
    private PostFilterAppState postFilterAppState;
    private MapPhysicsInformation mapPhysicsInformation;
    private Vision vision = new Vision();
    private ArrayList<Vector2D> visionEdges = new ArrayList<Vector2D>();
    private Vector2D[] mapBorderPoints;
    private HashMap<Double, Circle> visionCircleShapes = new HashMap<Double, Circle>();
    private PaintableImage fogImage;
    private Texture2D fogTexture = new Texture2D();
    private Raster fogRaster;
    private FogOfWarFilter fogOfWarFilter;
    private boolean isInitialized;
    private float timeSinceLastUpdate;
    private boolean isUpdateNeeded;
    private boolean displayMapSight;
    private byte[] tmpPlayerSightFogImageData;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        if(isInitialized){
            if(!displayMapSight){
                timeSinceLastUpdate += deltaSeconds;
                if(timeSinceLastUpdate > Settings.getFloat("fog_of_war_update_interval")){
                    updateFogOfWar(entityWorld);
                }
            }
        }
        else{
            updateFogOfWar(entityWorld);
            isInitialized = true;
        }
    }
    
    private void updateFogOfWar(EntityWorld entityWorld){
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
        Polygon sightPolygon = mapPhysicsInformation.getPolyMapManager().getNavigationPolygon(0);
        sightPolygon.rasterize(fogRaster);
        onFogImageUpdated();
    }
    
    private void updateFogTexture_PlayerSight(EntityWorld entityWorld){
        resetFogTexture();
        for(int entity : entityWorld.getEntitiesWithAll(PositionComponent.class, SightRangeComponent.class)){
            if(playerTeamSystem.isAllied(entityWorld, entity)){
                PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
                Vector2D position = new Vector2D(positionComponent.getPosition().getX(), positionComponent.getPosition().getY());
                double sightRange = entityWorld.getComponent(entity, SightRangeComponent.class).getRange();
                Polygon sightPolygon;
                //Fast way using shapes
                if(true){
                    visionEdges.clear();
                    addPointEdges(visionEdges, mapBorderPoints);
                    Circle visionCircleShape = getVisionCircleShape(sightRange);
                    visionCircleShape.setTransform(new Transform2D(1, 0, position.getX(), position.getY()));
                    ArrayList<Vector2D> visionCircleOutline = PolyHelper.fromShape(visionCircleShape).edges();
                    for(int i=(visionCircleOutline.size() - 1);i>=0;i--){
                        visionEdges.add(visionCircleOutline.get(i));
                    }
                    for(ConvexShape obstacle : mapPhysicsInformation.getObstacles()){
                        if(obstacle.intersects(visionCircleShape)){
                            if(obstacle instanceof SimpleConvexPolygon){
                                SimpleConvexPolygon simpleConvexPolygon = (SimpleConvexPolygon) obstacle;
                                addPointEdges(visionEdges, simpleConvexPolygon.getGlobalPoints());
                            }
                            else if(obstacle instanceof Circle){
                                ArrayList<Vector2D> circleOutline = PolyHelper.fromShape(obstacle).edges();
                                visionEdges.addAll(circleOutline);
                            }
                        }
                    }
                    sightPolygon = PolyHelper.fromOutline(vision.sightPolyOutline(position, visionEdges));
                }
                //Slow way using polygons
                else{
                    sightPolygon = mapPhysicsInformation.getPolyMapManager().sightPolygon(position, sightRange);
                }
                sightPolygon.rasterize(fogRaster, position, sightRange);
            }
        }
        onFogImageUpdated();
    }
    
    private static void addPointEdges(List<Vector2D> edges, Vector2D[] points){
        Vector2D lastPoint = points[points.length - 1];
        for(Vector2D point : points){
            edges.add(lastPoint);
            edges.add(point);
            lastPoint = point;
        }
    }
    
    private Circle getVisionCircleShape(double radius){
        Circle circle = visionCircleShapes.get(radius);
        if(circle == null){
            circle = new Circle(radius);
            visionCircleShapes.put(radius, circle);
        }
        return circle;
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

    public PaintableImage getFogImage(){
        return fogImage;
    }
}
