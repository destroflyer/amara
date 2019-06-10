/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import java.util.Set;
import com.jme3.scene.Node;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ShopAnimationSystem implements EntitySystem{
    
    public ShopAnimationSystem(int playerEntity, EntitySceneMap entitySceneMap){
        this.playerEntity = playerEntity;
        this.entitySceneMap = entitySceneMap;
    }
    private int playerEntity;
    private EntitySceneMap entitySceneMap;
    private boolean[] canUseShop;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        PlayerCharacterComponent playerCharacterComponent = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class);
        if(playerCharacterComponent != null){
            Set<Integer> shopEntities = entityWorld.getEntitiesWithAny(ShopRangeComponent.class);
            if(canUseShop == null){
                canUseShop = new boolean[shopEntities.size()];
            }
            int i = 0;
            for(int shopEntity : shopEntities){
                boolean couldUseShop = canUseShop[i];
                canUseShop[i] = ShopUtil.canUseShop(entityWorld, playerCharacterComponent.getEntity(), shopEntity);
                if(canUseShop[i] != couldUseShop){
                    ModelComponent modelComponent = entityWorld.getComponent(shopEntity, ModelComponent.class);
                    if(modelComponent.getModelSkinPath().equals("Models/chest/skin.xml")){
                        ModelObject chestModelObject = getModelObject(shopEntity);
                        chestModelObject.playAnimation((canUseShop[i]?"open":"close"), 1, false);
                    }
                }
                i++;
            }
        }
    }
    
    private ModelObject getModelObject(int entity){
        Node node = entitySceneMap.requestNode(entity);
        return (ModelObject) node.getChild(ModelSystem.NODE_NAME_MODEL);
    }
}
