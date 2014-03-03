/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Spatial;
import amara.engine.applications.ingame.client.systems.visualisation.*;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_SonicWaveMark extends BuffVisualisationSystem{

    public BuffVisualisationSystem_SonicWaveMark(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "sonic_wave_mark");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int entity){
        return SelectionMarkerSystem.createGroundTexture("Textures/effects/sonic_wave_mark.png", 3.5f, 3.5f);
    }
}
