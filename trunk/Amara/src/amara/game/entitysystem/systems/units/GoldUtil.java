/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.units;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.units.GoldComponent;

/**
 *
 * @author Carl
 */
public class GoldUtil{
    
    public static int getGold(EntityWorld entityWorld, int entity){
        int gold = 0;
        GoldComponent goldComponent = entityWorld.getComponent(entity, GoldComponent.class);
        if(goldComponent != null){
            gold = goldComponent.getGold();
        }
        return gold;
    }
}
