/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units.bounties;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RedirectReceivedBountiesComponent {

    public RedirectReceivedBountiesComponent(){

    }

    public RedirectReceivedBountiesComponent(int receiverEntity){
        this.receiverEntity = receiverEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int receiverEntity;

    public int getReceivedEntity(){
        return receiverEntity;
    }
}
