/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.ArrayList;
import java.util.Iterator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.engine.client.systems.visualisation.*;
import amara.engine.client.systems.visualisation.effects.crodwcontrol.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.systems.aggro.*;
import amara.game.entitysystem.systems.attributes.*;
import amara.game.entitysystem.systems.buffs.*;
import amara.game.entitysystem.systems.commands.*;
import amara.game.entitysystem.systems.effects.*;
import amara.game.entitysystem.systems.effects.crowdcontrol.*;
import amara.game.entitysystem.systems.effects.damage.*;
import amara.game.entitysystem.systems.effects.triggers.*;
import amara.game.entitysystem.systems.general.*;
import amara.game.entitysystem.systems.movement.*;
import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.*;

/**
 *
 * @author Carl
 */
public class EntitySystemAppState extends BaseAppState{

    public EntitySystemAppState(){
        
    }
    private EntityWorld entityWorld = new EntityWorld();
    private ArrayList<EntitySystem> entitySystems = new ArrayList<EntitySystem>();
    private Node entitiesNode = new Node();
    private EntitySceneMap entitySceneMap = new EntitySceneMap(){

        @Override
        public Node requestNode(int entity){
            Node node = getNode(entity);
            if(node == null){
                node = new Node();
                node.setUserData("entity", entity);
                entitiesNode.attachChild(node);
            }
            return node;
        }

        public Node removeNode(int entity){
            Node node = getNode(entity);
            if(node != null){
                entitiesNode.detachChild(node);
            }
            return node;
        }
        
        private Node getNode(int entity){
            Iterator<Spatial> childrenIterator = entitiesNode.getChildren().iterator();
            while(childrenIterator.hasNext()){
                Spatial child = childrenIterator.next();
                if(((Integer) child.getUserData("entity")) == entity){
                    return (Node) child;
                }
            }
            return null;
        }
    };

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getRootNode().attachChild(entitiesNode);
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(SendPlayerCommandsAppState.TEST_COMMAND_QUEUE));
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new CastSingleTargetSpellSystem());
        addEntitySystem(new CastLinearSkillshotSpellSystem());
        addEntitySystem(new CastPositionalSkillshotSpellSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyPhysicalDamageSystem());
        addEntitySystem(new ApplyMagicDamageSystem());
        addEntitySystem(new ApplyBindingSystem());
        addEntitySystem(new ApplySilenceSystem());
        addEntitySystem(new ApplyStunSystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new DeathSystem());
        IntersectionSystem intersectionSystem = new IntersectionSystem();
        addEntitySystem(new IntersectionAntiGhostSystem(intersectionSystem));
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TargetedMovementSystem());
        addEntitySystem(new TransformUpdateSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionSystem));
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(intersectionSystem);
        addEntitySystem(new AggroSystem());
        addEntitySystem(new ModelSystem(entitySceneMap));
        addEntitySystem(new PositionSystem(entitySceneMap));
        addEntitySystem(new DirectionSystem(entitySceneMap));
        addEntitySystem(new ScaleSystem(entitySceneMap));
        addEntitySystem(new AnimationSystem(entitySceneMap));
        addEntitySystem(new SelectionMarkerSystem(entitySceneMap));
        addEntitySystem(new MaximumHealthBarSystem(entitySceneMap));
        addEntitySystem(new CurrentHealthBarSystem(entitySceneMap));
        addEntitySystem(new StunVisualisationSystem(entitySceneMap));
        addEntitySystem(new SilenceVisualisationSystem(entitySceneMap));
    }
    
    public void addEntitySystem(EntitySystem entitySystem){
        entitySystems.add(entitySystem);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        for(int i=0;i<entitySystems.size();i++){
            EntitySystem entitySystem = entitySystems.get(i);
            entitySystem.update(entityWorld, lastTimePerFrame);
        }
        entityWorld.onFrameEnded();
    }

    public int getEntity(Spatial spatial){
        while(spatial != null){
            Integer entity = spatial.getUserData("entity");
            if(entity != null){
                return entity;
            }
            spatial = spatial.getParent();
        }
        return -1;
    }

    public EntityWorld getEntityWorld(){
        return entityWorld;
    }
    
    public Node getEntitiesNode(){
        return entitiesNode;
    }

    public EntitySceneMap getEntitySceneMap(){
        return entitySceneMap;
    }
}
