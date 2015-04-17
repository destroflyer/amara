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
    private boolean[] isInShopRange;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            Set<Integer> shopEntities = entityWorld.getEntitiesWithAll(ShopRangeComponent.class);
            if(isInShopRange == null){
                isInShopRange = new boolean[shopEntities.size()];
            }
            int i = 0;
            for(int shopEntity : shopEntities){
                boolean wasInRange = isInShopRange[i];
                isInShopRange[i] = ShopUtil.isInShopRange(entityWorld, selectedUnitComponent.getEntity(), shopEntity);
                if(isInShopRange[i] != wasInRange){
                    ModelComponent modelComponent = entityWorld.getComponent(shopEntity, ModelComponent.class);
                    if(modelComponent.getModelSkinPath().equals("Models/chest/skin.xml")){
                        ModelObject chestModelObject = getModelObject(shopEntity);
                        chestModelObject.playAnimation((isInShopRange[i]?"open":"close"), 1, false);
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
