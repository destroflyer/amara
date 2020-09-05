/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.physics;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class TransformOriginComponent {

    public TransformOriginComponent() {
        
    }

    public TransformOriginComponent(int originEntity) {
        this.originEntity = originEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int originEntity;

    public int getOriginEntity() {
        return originEntity;
    }
}
