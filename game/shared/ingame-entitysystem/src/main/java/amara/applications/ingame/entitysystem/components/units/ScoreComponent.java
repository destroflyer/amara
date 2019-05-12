/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class ScoreComponent{

    public ScoreComponent(){
        
    }
    
    public ScoreComponent(int scoreEntity){
        this.scoreEntity = scoreEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int scoreEntity;

    public int getScoreEntity(){
        return scoreEntity;
    }
}
