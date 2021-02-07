package amara.libraries.applications.display.ingame.maps;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import amara.applications.ingame.shared.maps.*;
import amara.applications.ingame.shared.maps.terrain.*;
import amara.core.settings.Settings;
import amara.core.settings.SettingsSubscriptions;

import java.util.function.Consumer;

public class MapTerrain {

    public MapTerrain(Map map, SettingsSubscriptions settingsSubscriptions, AssetManager assetManager) {
        this.map = map;
        this.settingsSubscriptions = settingsSubscriptions;
        node = new Node();
        material = new Material(assetManager, "Common/MatDefs/Terrain/TerrainLighting.j3md");
        loadTerrain(assetManager);
        loadMaterialParameters(assetManager);
    }
    private Map map;
    private SettingsSubscriptions settingsSubscriptions;
    private Node node;
    private Material material;
    private TerrainQuad terrain;
    private TerrainAlphamap[] alphamaps;

    private void loadTerrain(AssetManager assetManager) {
        MapPhysicsInformation physicsInformation = map.getPhysicsInformation();
        Texture heightMapImage = assetManager.loadTexture("Maps/" + map.getName() + "/terrain_heightmap.png");
        ImageBasedHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        settingsSubscriptions.subscribeInteger("terrain_quality", terrainQuality -> {
            if (terrain != null) {
                node.detachChild(terrain);
            }
            int scaledHeightmapSize = (int) ((heightmap.getSize() / Math.pow(2, terrainQuality)) + 1);
            terrain = new TerrainQuad("terrain", 129, scaledHeightmapSize, scaleHeightmap(heightmap, scaledHeightmapSize));
            float scaleX = (physicsInformation.getWidth() / terrain.getTotalSize());
            float scaleZ = (physicsInformation.getHeight() / terrain.getTotalSize());
            terrain.setLocalScale(scaleX, 1, scaleZ);
            terrain.setLocalTranslation((physicsInformation.getWidth() / 2), 0, (physicsInformation.getHeight() / 2));
            terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
            terrain.setMaterial(material);
            node.attachChild(terrain);
        });
    }

    private float[] scaleHeightmap(HeightMap heightmap, int scaledHeightmapSize) {
        float[] scaledHeightmap = new float[scaledHeightmapSize * scaledHeightmapSize];
        float scaleFactor = (((float) scaledHeightmapSize) / heightmap.getSize());
        for (int x = 0; x < scaledHeightmapSize; x++) {
            for (int y = 0; y < scaledHeightmapSize; y++) {
                scaledHeightmap[x + (y * scaledHeightmapSize)] = (heightmap.getInterpolatedHeight(x / scaleFactor, y / scaleFactor) * map.getPhysicsInformation().getHeightmapScale());
            }
        }
        return scaledHeightmap;
    }

    private void loadMaterialParameters(AssetManager assetManager) {
        TerrainSkin skin = map.getTerrainSkin();
        // AlphaMaps
        alphamaps = new TerrainAlphamap[(int) Math.ceil(skin.getTextures().length / 4.0)];
        for (int i = 0; i < alphamaps.length; i++) {
            alphamaps[i] = new TerrainAlphamap("Maps/" + map.getName() + "/terrain_alphamap_" + i + ".png");
            updateAlphamap(i);
        }
        // DiffuseMaps
        forEachDiffuseMap(textureInfo -> {
            TerrainSkin_Texture terrainTexture = textureInfo.getTexture();
            Texture texture = assetManager.loadTexture(terrainTexture.getFilePath());
            texture.setWrap(terrainTexture.getWrapMode());
            material.setTexture("DiffuseMap" + ((textureInfo.getTextureIndex() == 0) ? "" : "_" + textureInfo.getTextureIndex()), texture);
        });
        settingsSubscriptions.subscribeBoolean("terrain_triplanar_mapping", triPlanarMapping -> {
            material.setBoolean("useTriPlanarMapping", triPlanarMapping);
            updateDiffuseMapScales();
        });
    }

    public void updateAlphamap(int index) {
        TerrainAlphamap alphamap = alphamaps[index];
        alphamap.updateTexture();
        material.setTexture("AlphaMap" + ((index == 0) ? "" : "_" + index), alphamap.getTexture2D());
    }

    private void updateDiffuseMapScales() {
        forEachDiffuseMap(textureInfo -> {
            TerrainSkin_Texture terrainTexture = textureInfo.getTexture();
            float textureScale = terrainTexture.getScale();
            if (Settings.getBoolean("terrain_triplanar_mapping")) {
                textureScale = (1 / (terrain.getTotalSize() / textureScale));
            }
            material.setFloat("DiffuseMap_" + textureInfo.getTextureIndex() + "_scale", textureScale);
        });
    }

    private void forEachDiffuseMap(Consumer<MapTerrainTextureInfo> textureInfoConsumer) {
        TerrainSkin skin = map.getTerrainSkin();
        int textureIndex = 0;
        for (int i = 0; i < skin.getTextures().length; i++) {
            textureInfoConsumer.accept(new MapTerrainTextureInfo(map.getTerrainSkin().getTextures()[i], textureIndex));
            textureIndex++;
            // Alpha channel not used
            if (((textureIndex + 1) % 4) == 0) {
                textureIndex++;
            }
        }
    }

    public float getHeight(Vector2f location) {
        return terrain.getHeight(location);
    }

    public Node getNode() {
        return node;
    }

    public TerrainAlphamap[] getAlphamaps() {
        return alphamaps;
    }
}
