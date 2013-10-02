/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import java.util.ArrayList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import amara.engine.client.maps.MapTerrain;
import amara.engine.client.systems.visualisation.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.selection.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.physics.*;
import shapes.*;

/**
 *
 * @author Carl
 */
public class MapAppState extends BaseAppState{

    public MapAppState(String mapName){
        this.mapName = mapName;
    }
    private String mapName;
    private MapTerrain mapTerrain;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        int mapWidth = 100;
        int mapHeight = 100;
        mapTerrain = new MapTerrain(mapName, mapWidth, mapHeight);
        mainApplication.getRootNode().attachChild(mapTerrain.getTerrain());
        EntitySystemAppState entitySystemAppState = getAppState(EntitySystemAppState.class);
        entitySystemAppState.addEntitySystem(new ModelYAdjustingSystem(entitySystemAppState.getEntitySceneMap(), mapTerrain));
        //Test-Obstacles
        ArrayList<Shape> obstacles = new ArrayList<Shape>();
        obstacles.add(new SimpleConvex(new Vector2D(0, 0), new Vector2D(100, 0)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 0), new Vector2D(100, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(100, 100), new Vector2D(0, 100)));
        obstacles.add(new SimpleConvex(new Vector2D(0, 100), new Vector2D(0, 0)));
        obstacles.add(new Rectangle(10, 7, 5, 8));
        obstacles.add(new Circle(30, 25, 3));
        entitySystemAppState.addEntitySystem(new MapIntersectionSystem(mapWidth, mapHeight, obstacles));
        //Test-Entities
        EntityWorld entityWorld = entitySystemAppState.getEntityWorld();
        //Entity #1
        EntityWrapper entity1 = entityWorld.getWrapped(entityWorld.createEntity());
        entity1.setComponent(new ModelComponent("Models/minion/skin.xml"));
        entity1.setComponent(new AnimationComponent("dance", 1));
        entity1.setComponent(new ScaleComponent(0.75f));
        entity1.setComponent(new PositionComponent(new Vector2f(22, 16)));
        entity1.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        entity1.setComponent(new HitboxComponent(new RegularCyclic(6, 2)));
        entity1.setComponent(new AntiGhostComponent());
        entity1.setComponent(new IsSelectableComponent());
        entity1.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        entity1.setComponent(new TeamComponent(1));
        //Entity #2
        EntityWrapper entity2 = entityWorld.getWrapped(entityWorld.createEntity());
        entity2.setComponent(new ModelComponent("Models/wizard/skin.xml"));
        entity2.setComponent(new ScaleComponent(0.5f));
        entity2.setComponent(new PositionComponent(new Vector2f(7, 7)));
        entity2.setComponent(new DirectionComponent(new Vector2f(1, 1)));
        entity2.setComponent(new MovementSpeedComponent(new Vector2f(2, 2)));
        entity2.setComponent(new HitboxComponent(new Circle(1)));
        entity2.setComponent(new AntiGhostComponent());
        entity2.setComponent(new HealthComponent(400));
        entity2.setComponent(new IsSelectableComponent());
        entity2.setComponent(new IsSelectedComponent());
        entity2.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
        entity2.setComponent(new TeamComponent(2));
        //Entity #2 - Attributes
        entity2.setComponent(new BaseMaximumHealthComponent(500));
        entity2.setComponent(new BaseAttackDamageComponent(60));
        entity2.setComponent(new BaseAbilityPowerComponent(0));
        EntityWrapper item1 = entityWorld.getWrapped(entityWorld.createEntity());
        item1.setComponent(new BonusFlatMaximumHealthComponent(80));
        item1.setComponent(new BonusFlatAttackDamageComponent(10));
        EntityWrapper item2 = entityWorld.getWrapped(entityWorld.createEntity());
        item2.setComponent(new BonusFlatMaximumHealthComponent(80));
        item2.setComponent(new BonusFlatAbilityPowerComponent(15));
        EntityWrapper item3 = entityWorld.getWrapped(entityWorld.createEntity());
        item3.setComponent(new BonusFlatAbilityPowerComponent(80));
        entity2.setComponent(new InventoryComponent(new int[]{item1.getId(), item2.getId(), item3.getId()}));
        entity2.setComponent(new RequestUpdateAttributesComponent());
        //Entity #1 - Spells
        EntityWrapper spell1 = entityWorld.getWrapped(entityWorld.createEntity());
        spell1.setComponent(new NameComponent("Scream"));
        spell1.setComponent(new DescriptionComponent("Silences an enemy for a short duration."));
        EntityWrapper effect1 = entityWorld.getWrapped(entityWorld.createEntity());
        effect1.setComponent(new SilenceComponent(0.5f));
        spell1.setComponent(new InstantTargetEffectComponent(effect1.getId()));
        entity1.setComponent(new SpellsComponent(new int[]{spell1.getId()}));
        spell1.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        //Entity #2 - Spells
        //Spell #1
        EntityWrapper spell2 = entityWorld.getWrapped(entityWorld.createEntity());
        spell2.setComponent(new NameComponent("Flamethrower"));
        spell2.setComponent(new DescriptionComponent("Burns an enemy with a flame that damages and stuns."));
        EntityWrapper effect2 = entityWorld.getWrapped(entityWorld.createEntity());
        effect2.setComponent(new FlatMagicDamageComponent(100));
        effect2.setComponent(new ScalingAbilityPowerMagicDamageComponent(0.7f));
        effect2.setComponent(new StunComponent(1.5f));
        spell2.setComponent(new InstantTargetEffectComponent(effect2.getId()));
        spell2.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        //Spell #2
        EntityWrapper spell3 = entityWorld.getWrapped(entityWorld.createEntity());
        spell3.setComponent(new NameComponent("Fireball"));
        spell3.setComponent(new DescriptionComponent("Throws a fireball."));
        EntityWrapper spawnInformation1 = entityWorld.getWrapped(entityWorld.createEntity());
        spawnInformation1.setComponent(new SpawnTemplateComponent("fireball"));
        spawnInformation1.setComponent(new RelativeSpawnPositionComponent(new Vector2f(0, 0)));
        spawnInformation1.setComponent(new SpawnMovementSpeedComponent(4));
        spell3.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation1.getId()}));
        spell3.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SKILLSHOT));
        spell3.setComponent(new CooldownComponent(1));
        entity2.setComponent(new SpellsComponent(new int[]{spell2.getId(), spell3.getId()}));
        //Autoattack #1
        EntityWrapper autoAttack1 = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper spawnInformation2 = entityWorld.getWrapped(entityWorld.createEntity());
        spawnInformation2.setComponent(new SpawnTemplateComponent("fireball"));
        spawnInformation2.setComponent(new SpawnMovementSpeedComponent(15));
        autoAttack1.setComponent(new InstantSpawnsComponent(new int[]{spawnInformation2.getId()}));
        autoAttack1.setComponent(new CooldownComponent(1));
        autoAttack1.setComponent(new CastTypeComponent(CastTypeComponent.CastType.SINGLE_TARGET));
        entity2.setComponent(new AutoAttackComponent(autoAttack1.getId()));
        entity2.setComponent(new AutoAggroComponent(9));
        //Field of test units
        for(int x=0;x<5;x++){
            for(int y=0;y<4;y++){
                EntityWrapper entity = entityWorld.getWrapped(entityWorld.createEntity());
                entity.setComponent(new ModelComponent("Models/wizard/skin.xml"));
                entity.setComponent(new ScaleComponent(0.5f));
                entity.setComponent(new PositionComponent(new Vector2f(12 + (x * 2), 22 + (y * 2))));
                entity.setComponent(new DirectionComponent(new Vector2f(1, 1)));
                entity.setComponent(new HitboxComponent(new Circle(1)));
                entity.setComponent(new AntiGhostComponent());
                entity.setComponent(new CollisionGroupComponent(CollisionGroupComponent.COLLISION_GROUP_UNITS, CollisionGroupComponent.COLLISION_GROUP_MAP | CollisionGroupComponent.COLLISION_GROUP_UNITS));
                entity.setComponent(new TeamComponent(1));
                entity.setComponent(new BaseMaximumHealthComponent(500));
                entity.setComponent(new RequestUpdateAttributesComponent());
            }
        }
        //Debug View
        Node collisionDebugNode = new Node();
        collisionDebugNode.setLocalTranslation(0, 1, 0);
        mainApplication.getRootNode().attachChild(collisionDebugNode);
        entitySystemAppState.addEntitySystem(new CollisionDebugSystem(collisionDebugNode, obstacles));
    }

    public MapTerrain getMapTerrain(){
        return mapTerrain;
    }
}
