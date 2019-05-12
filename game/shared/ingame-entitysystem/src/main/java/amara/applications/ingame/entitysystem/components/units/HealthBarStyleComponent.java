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
public class HealthBarStyleComponent{

    public HealthBarStyleComponent(){
        
    }
    
    public HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle style){
        this.style = style;
    }
    public enum HealthBarStyle{
        DEFAULT,
        CHARACTER,
        BOSS
    }
    private HealthBarStyleComponent.HealthBarStyle style;

    public HealthBarStyle getStyle(){
        return style;
    }
}
