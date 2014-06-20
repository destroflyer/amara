/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.launcher.startscreen.screens;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.FogFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.launcher.startscreen.LoginScreenApplication;
import amara.engine.appstates.*;

/**
 *
 * @author Carl
 */
public class LoginScreen_Forest extends LoginScreen{

    public LoginScreen_Forest(){
        backgroundMusicPath = "Sounds/music/Woods of Eremae.ogg";
        loginBoxImagePath = "Interface/client/login/forest/box.png";
    }

    @Override
    public void initialize(LoginScreenApplication application){
        ModelObject tree = new ModelObject(application, "/Models/cartoon_forest_tree_1/skin.xml");
        tree.setLocalTranslation(0.3f, 0.2f, -0.2f);
        application.getRootNode().attachChild(tree);
        ModelObject unit = new ModelObject(application, "/Models/minion/skin_default.xml");
        unit.setLocalTranslation(-1.8f, 0.8f, -0.5f);
        JMonkeyUtil.setLocalRotation(unit, new Vector3f(0.25f, 0, 1));
        unit.setLocalScale(0.4f);
        unit.playAnimation("dance", 6);
        application.getRootNode().attachChild(unit);
        application.getCamera().setLocation(new Vector3f(2.25f, 4.6f, 9.16f));
        application.getCamera().lookAtDirection(new Vector3f(-0.57f, -0.16f, -0.8f), Vector3f.UNIT_Y);
        SnowParticleEmitter snowParticleEmitter = new SnowParticleEmitter();
        snowParticleEmitter.setLocalTranslation(-5, 10, 0);
        application.getRootNode().attachChild(snowParticleEmitter);
        try{
            AbstractHeightMap heightmap = new HillHeightMap(64, 100, 10, 15, 681169129123721854l);
            heightmap.load();
            TerrainQuad terrain = new TerrainQuad("", 20, heightmap.getSize() + 1, heightmap.getHeightMap());
            Material material = new Material(application.getAssetManager(), "Common/MatDefs/Terrain/TerrainLighting.j3md");
            material.setTexture("AlphaMap", application.getAssetManager().loadTexture("Interface/client/login/forest/terrain_alphamap.png"));
            Texture textureGrass = application.getAssetManager().loadTexture("Interface/client/login/forest/stone.jpg");
            textureGrass.setWrap(Texture.WrapMode.Repeat);
            material.setTexture("DiffuseMap", textureGrass);
            material.setFloat("DiffuseMap_0_scale", 24);
            terrain.setMaterial(material);
            terrain.setLocalTranslation(-15, 0, -16);
            terrain.setLocalScale(1, 0.03f, 1);
            application.getRootNode().attachChild(terrain);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        Spatial sky = SkyFactory.createSky(application.getAssetManager(), "Textures/skies/default.jpg", true);
        application.getRootNode().attachChild(sky);
        application.getStateManager().attach(new BaseDisplayAppState(){

            @Override
            public void initialize(AppStateManager stateManager, Application application){
                super.initialize(stateManager, application);
                LightAppState lightAppState = getAppState(LightAppState.class);
                lightAppState.addLight(new AmbientLight());
                lightAppState.addLight(new DirectionalLight(){{ setDirection(new Vector3f(-0.2f, -1, 1)); }});
                getAppState(PostFilterAppState.class).addFilter(new FogFilter(new ColorRGBA(1, 1, 1, 1), 1.5f, 25));
                Geometry waterPlane = getAppState(WaterAppState.class).createWaterPlane(new Vector3f(-30, 0.5f, -10), new Vector2f(35, 20));
                mainApplication.getRootNode().attachChild(waterPlane);
            }
        });
    }
}
