/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Spatial;
import amara.applications.ingame.client.systems.visualisation.EntitySceneMap;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_Backporting extends BuffVisualisationSystem{

    public BuffVisualisationSystem_Backporting(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "backporting");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        return new ModelObject(null, "Models/backport_particles/skin.xml");
    }
}
