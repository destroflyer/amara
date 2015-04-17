/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.shop;

import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.shop.*;

/**
 *
 * @author Carl
 */
public class ShopUtil{
    
    public static boolean isInShopRange(EntityWorld entityWorld, int entity){
        for(int shopEntity : entityWorld.getEntitiesWithAll(ShopRangeComponent.class, PositionComponent.class)){
            if(isInShopRange(entityWorld, entity, shopEntity)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isInShopRange(EntityWorld entityWorld, int entity, int shopEntity){
        PositionComponent entityPositionComponent = entityWorld.getComponent(entity, PositionComponent.class);
        PositionComponent shopPositionComponent = entityWorld.getComponent(shopEntity, PositionComponent.class);
        if((entityPositionComponent != null) && (shopPositionComponent != null)){
            float shopRange = entityWorld.getComponent(shopEntity, ShopRangeComponent.class).getRange();
            return shopPositionComponent.getPosition().distanceSquared(entityPositionComponent.getPosition()) <= (shopRange * shopRange);
        }
        return false;
    }
}
