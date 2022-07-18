package amara.applications.ingame.shared.maps;

import amara.applications.ingame.shared.maps.terrain.TerrainSkin;
import amara.libraries.entitysystem.EntityWorld;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public abstract class Map {

    public static int GAME_ENTITY = 0;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    protected MapCamera camera;
    @Getter
    protected List<MapLight> lights = new LinkedList<>();
    @Getter
    protected List<MapFilter> filters = new LinkedList<>();
    @Getter
    @Setter
    protected TerrainSkin terrainSkin;
    @Getter
    @Setter
    protected MapMinimapInformation minimapInformation;
    @Getter
    @Setter
    protected MapPhysicsInformation physicsInformation;
    @Getter
    protected List<MapVisual> visuals = new LinkedList<>();
    @Getter
    protected MapSpells[] spells = new MapSpells[0];
    @Getter
    @Setter
    protected int entity;

    public abstract void load(EntityWorld entityWorld, int playersCount);

    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {

    }

    public abstract void spawnPlayer(EntityWorld entityWorld, int playerEntity);

    public void initializeItem(EntityWorld entityWorld, int itemEntity, int buyerEntity) {

    }

    public void addLight(MapLight light) {
        lights.add(light);
    }

    public void addFilter(MapFilter filter) {
        filters.add(filter);
    }

    public void addVisual(MapVisual visual) {
        visuals.add(visual);
    }

    public void removeVisual(MapVisual visual) {
        visuals.remove(visual);
    }
}
