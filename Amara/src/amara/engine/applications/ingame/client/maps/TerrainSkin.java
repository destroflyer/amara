/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.maps;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class TerrainSkin{

    public TerrainSkin(TerrainSkin_Texture[] textures, String skyMap){
        this.textures = textures;
        this.skyMap = skyMap;
    }
    private String skyMap;
    private TerrainSkin_Texture[] textures;
    private boolean containsWater = false;
    private float waterHeight;
    private boolean containsSnow = false;
    private static HashMap<String, TerrainSkin> SKINS = new HashMap<String, TerrainSkin>();
    static{
        SKINS.put("cartoon_forest", new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/cracked_cliff.jpg", 18),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/sandstone.jpg", 24),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/pebbles_1.jpg", 24),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/grass_1.jpg", 10),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/stone_bright_1.jpg", 16),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/wood_1.jpg", 16),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/cliff.jpg", 24),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/bush.jpg", 16),
                new TerrainSkin_Texture("Textures/terrain/cartoon_forest/dirt.jpg", 16),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1)
            },
            "Textures/skies/default.jpg"
        ));
        SKINS.put("grass", new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/grass.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/dirt.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/road.jpg", 128),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1),
                new TerrainSkin_Texture("Textures/terrain/stone_dark.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/wall_dark.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/wood.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1),
                new TerrainSkin_Texture("Textures/terrain/magma.jpg", 128),
                new TerrainSkin_Texture("Textures/terrain/snow.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/water.jpg", 128),
                new TerrainSkin_Texture("Textures/terrain/blank.png", 1)
            },
            "Textures/skies/default.jpg"
        ));
        SKINS.put("desert", new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/wood.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/dirt.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/road.jpg", 128)
            },
            "Textures/skies/default.jpg"
        ));
        SKINS.put("volcano", new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/stone_dark.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/wall_dark.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/magma.jpg", 128)
            },
            "Textures/skies/evening.jpg"
        ));
        TerrainSkin iceland = new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/snow.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/snow.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/water.jpg", 128)
            },
            "Textures/skies/default.jpg"
        );
        iceland.setContainsSnow(true);
        SKINS.put("iceland", iceland);
        TerrainSkin iceland_water = new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/snow.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/snow.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/water.jpg", 128)
            },
            "Textures/skies/default.jpg"
        );
        iceland_water.setContainsSnow(true);
        iceland_water.enableWater(10);
        SKINS.put("iceland_water", iceland_water);
        SKINS.put("gold", new TerrainSkin(
            new TerrainSkin_Texture[]{
                new TerrainSkin_Texture("Textures/terrain/gold.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/stone_dark.jpg", 32),
                new TerrainSkin_Texture("Textures/terrain/cartoon_stone.jpg", 128)
            },
            "Textures/skies/default.jpg"
        ));
    }
    
    public TerrainSkin_Texture[] getTextures(){
        return textures;
    }

    public String getSkyMap(){
        return skyMap;
    }

    public void enableWater(float waterHeight){
        containsWater = true;
        this.waterHeight = waterHeight;
    }

    public void disableWater(){
        containsWater = false;
    }

    public boolean containsWater(){
        return containsWater;
    }

    public float getWaterHeight(){
        return waterHeight;
    }

    public boolean containsSnow(){
        return containsSnow;
    }

    public void setContainsSnow(boolean containsSnow){
        this.containsSnow = containsSnow;
    }
    
    public static String[] getSkinNames(){
        String[] skinNames = new String[SKINS.size()];
        SKINS.keySet().toArray(skinNames);
        return skinNames;
    }
    
    public static TerrainSkin getSkin(String id){
        return SKINS.get(id);
    }
}
