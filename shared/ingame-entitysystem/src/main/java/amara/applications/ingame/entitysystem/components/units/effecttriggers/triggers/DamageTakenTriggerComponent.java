/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class DamageTakenTriggerComponent{

    public DamageTakenTriggerComponent(){
        
    }

    public DamageTakenTriggerComponent(boolean physicalDamage, boolean magicDamage, boolean trueDamage){
        this.physicalDamage = physicalDamage;
        this.magicDamage = magicDamage;
        this.trueDamage = trueDamage;
    }
    private boolean physicalDamage;
    private boolean magicDamage;
    private boolean trueDamage;

    public boolean isPhysicalDamage(){
        return physicalDamage;
    }

    public boolean isMagicDamage(){
        return magicDamage;
    }

    public boolean isTrueDamage(){
        return trueDamage;
    }
}
