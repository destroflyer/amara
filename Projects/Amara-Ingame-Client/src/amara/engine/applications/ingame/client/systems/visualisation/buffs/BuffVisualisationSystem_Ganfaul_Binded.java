/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Ganfaul_Binded extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Ganfaul_Binded(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "ganfaul_binded");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int buffStatusEntity, int targetEntity){
        Spatial particles = new ModelObject(null, "Models/ganfaul_binding_particles/skin.xml");
        particles.setLocalTranslation(0, 3.7f, 0);
        return particles;
    }
}
