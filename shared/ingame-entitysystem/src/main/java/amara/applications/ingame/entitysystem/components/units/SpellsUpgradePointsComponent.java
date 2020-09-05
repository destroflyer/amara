/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class SpellsUpgradePointsComponent{

    public SpellsUpgradePointsComponent(){
        
    }

    public SpellsUpgradePointsComponent(int upgradePoints){
        this.upgradePoints = upgradePoints;
    }
    private int upgradePoints;

    public int getUpgradePoints(){
        return upgradePoints;
    }
}
