/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import java.awt.image.BufferedImage;
import java.awt.Color;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.client.systems.filters.FogOfWarSystem;
import amara.applications.ingame.client.systems.information.PlayerTeamSystem;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.shared.maps.Map;
import amara.core.files.FileAssets;
import amara.core.settings.Settings;
import amara.libraries.applications.display.materials.PaintableImage;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class DisplayMinimapSystem extends GUIDisplaySystem{

    public DisplayMinimapSystem(int playerEntity, ScreenController_HUD screenController_HUD, Map map, PlayerTeamSystem playerTeamSystem, FogOfWarSystem fogOfWarSystem){
        super(playerEntity, screenController_HUD);
        this.map = map;
        this.playerTeamSystem = playerTeamSystem;
        this.fogOfWarSystem = fogOfWarSystem;
        scaleX_Map = (minimapImage.getWidth() / ((float) map.getMinimapInformation().getWidth()));
        scaleY_Map = (minimapImage.getHeight() / ((float) map.getMinimapInformation().getHeight()));
        scaleX_Fog = (fogOfWarSystem.getFogImage().getWidth() / map.getPhysicsInformation().getWidth());
        scaleY_Fog = (fogOfWarSystem.getFogImage().getHeight() / map.getPhysicsInformation().getHeight());
        backgroundImage = FileAssets.getImage("Maps/" + map.getName() + "/minimap.png", minimapImage.getWidth(), minimapImage.getHeight());
        towerImage = FileAssets.getImage("Interface/hud/minimap/tower.png");
    }
    private static final Color COLOR_BORDER = new Color(34, 34, 34);
    private static final Color COLOR_TEAM_ALLIED = new Color(0, 0.9f, 0);
    private static final Color COLOR_TEAM_ENEMY = new Color(0.9f, 0.2f, 0);
    private static final Color COLOR_TEAM_OTHER = new Color(100, 50, 0);
    private Map map;
    private PlayerTeamSystem playerTeamSystem;
    private FogOfWarSystem fogOfWarSystem;
    private PaintableImage minimapImage = new PaintableImage(264, 264);
    private Texture2D texture2D = new Texture2D();
    private float scaleX_Map;
    private float scaleY_Map;
    private float scaleX_Fog;
    private float scaleY_Fog;
    private BufferedImage backgroundImage;
    private BufferedImage towerImage;
    private boolean isInitialized;
    private float timeSinceLastUpdate;

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        if(isInitialized){
            timeSinceLastUpdate += deltaSeconds;
            if(timeSinceLastUpdate > Settings.getFloat("minimap_update_interval")){
                updateMinimap(entityWorld);
            }
        }
        else{
            updateMinimap(entityWorld);
            isInitialized = true;
        }
    }
    
    private void updateMinimap(EntityWorld entityWorld){
        ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class);
        if((!observer.getNew().isEmpty()) || (!observer.getChanged().isEmpty()) || (!observer.getRemoved().isEmpty())){
            minimapImage.loadImage(backgroundImage, false);
            for(int entity : entityWorld.getEntitiesWithAll(PositionComponent.class)){
                paintEntity(entityWorld, entity);
            }
            paintFogOfWar();
            minimapImage.flipY();
            texture2D.setImage(minimapImage.getImage());
            screenController_HUD.setMinimapImage(texture2D);
        }
        timeSinceLastUpdate = 0;
    }
    
    private void paintEntity(EntityWorld entityWorld, int entity){
        Color color;
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        int x = Math.round(((map.getPhysicsInformation().getWidth() - position.getX()) - map.getMinimapInformation().getX()) * scaleX_Map);
        int y = Math.round(((map.getPhysicsInformation().getHeight() - position.getY()) - map.getMinimapInformation().getY()) * scaleY_Map);
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if((teamComponent != null) && (teamComponent.getTeamEntity() != 0)){
            color = (playerTeamSystem.isAllied(teamComponent)?COLOR_TEAM_ALLIED:COLOR_TEAM_ENEMY);
        }
        else{
            color = COLOR_TEAM_OTHER;
        }
        if(entityWorld.hasComponent(entity, IsMinionComponent.class)){
            int size = 2;
            for(int i=(-1 * size);i<(size + 1);i++){
                for(int r=(-1 * size);r<(size + 1);r++){
                    minimapImage.setPixel(x + i, y + r, (((Math.abs(i) == size) || (Math.abs(r) == size))?COLOR_BORDER:color));
                }
            }
        }
        else if(entityWorld.hasComponent(entity, IsCharacterComponent.class)){
            int size = 3;
            for(int i=(-1 * size);i<(size + 1);i++){
                for(int r=(-1 * size);r<(size + 1);r++){
                    minimapImage.setPixel(x + i, y + r, (((Math.abs(i) == size) || (Math.abs(r) == size))?COLOR_BORDER:color));
                }
            }
        }
        else if(entityWorld.hasComponent(entity, IsMonsterComponent.class)){
            int size = 2;
            for(int i=(-1 * size);i<(size + 1);i++){
                for(int r=(-1 * size);r<(size + 1);r++){
                    minimapImage.setPixel(x + i, y + r, (((Math.abs(i) == size) || (Math.abs(r) == size))?COLOR_BORDER:color));
                }
            }
        }
        else if(entityWorld.hasComponent(entity, IsStructureComponent.class)){
            int towerX = (x - (towerImage.getWidth() / 2));
            int towerY = (y - (towerImage.getHeight() / 2));
            for(int i=1;i<(towerImage.getWidth() - 1);i++){
                for(int r=1;r<(towerImage.getHeight() - 1);r++){
                    minimapImage.setPixel(towerX + i, towerY + r, color);
                }
            }
            minimapImage.paintImage(towerImage, towerX, towerY);
        }
    }
    
    private void paintFogOfWar(){
        for(int x=0;x<minimapImage.getWidth();x++){
            for(int y=0;y<minimapImage.getHeight();y++){
                float mapX = (map.getPhysicsInformation().getWidth() - (((x / scaleX_Map) + map.getMinimapInformation().getX())));
                float mapY = (map.getPhysicsInformation().getHeight() - (((y / scaleY_Map) + map.getMinimapInformation().getY())));
                //Check for the maximum boundary since the physiccal size of the map reaches 1 unit further than the images
                int fogX = Math.min((int) (mapX * scaleX_Fog), (fogOfWarSystem.getFogImage().getWidth() - 1));
                int fogY = Math.min((int) (mapY * scaleY_Fog), (fogOfWarSystem.getFogImage().getHeight() - 1));
                int visibility = fogOfWarSystem.getFogImage().getPixel_Red(fogX, fogY);
                int red = ((minimapImage.getPixel_Red(x, y) * visibility) / 255);
                int green = ((minimapImage.getPixel_Green(x, y) * visibility) / 255);
                int blue = ((minimapImage.getPixel_Blue(x, y) * visibility) / 255);
                minimapImage.setPixel_Red(x, y, red);
                minimapImage.setPixel_Green(x, y, green);
                minimapImage.setPixel_Blue(x, y, blue);
            }
        }
    }
}
