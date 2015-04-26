/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import java.util.Set;
import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.shop.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.shop.ShopUtil;

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
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            Set<Integer> shopEntities = entityWorld.getEntitiesWithAll(ShopRangeComponent.class);
            if(canUseShop == null){
                canUseShop = new boolean[shopEntities.size()];
            }
            int i = 0;
            for(int shopEntity : shopEntities){
                boolean couldUseShop = canUseShop[i];
                canUseShop[i] = ShopUtil.canUseShop(entityWorld, selectedUnitComponent.getEntity(), shopEntity);
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
