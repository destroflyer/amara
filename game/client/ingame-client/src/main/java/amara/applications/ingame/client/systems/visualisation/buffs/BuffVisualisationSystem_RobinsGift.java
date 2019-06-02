/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.buffs;

import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class BuffVisualisationSystem_RobinsGift extends BuffVisualisationSystem{

    public BuffVisualisationSystem_RobinsGift(EntitySceneMap entitySceneMap){
        super(entitySceneMap, "robins_gift");
    }
    
    @Override
    protected Spatial createBuffVisualisation(EntityWorld entityWorld, int targetEntity){
        Node node = new Node();
        Spatial texture = SpellIndicatorSystem.createGroundTexture("Textures/effects/robins_gift_mark.png", 3.5f, 3.5f);
        node.attachChild(texture);
        return node;
    }
}