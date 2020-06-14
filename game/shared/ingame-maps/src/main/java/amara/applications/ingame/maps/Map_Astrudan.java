/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import amara.applications.ingame.entitysystem.components.conditions.NameAmountConditionComponent;
import amara.applications.ingame.entitysystem.components.effects.spawns.SpawnComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent;
import amara.applications.ingame.entitysystem.components.spawns.SpawnTemplateComponent;
import amara.applications.ingame.entitysystem.components.units.LevelComponent;
import amara.applications.ingame.entitysystem.components.units.SpellsUpgradePointsComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerConditionsComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggeredEffectComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.CustomTargetComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RepeatingTriggerComponent;
import amara.applications.ingame.entitysystem.components.visuals.ModelComponent;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class Map_Astrudan extends Map {

    @Override
    public void load(EntityWorld entityWorld) {
        // Spawn mobs trigger
        int spawnMobsTrigger = entityWorld.createEntity();
        entityWorld.setComponent(spawnMobsTrigger, new RepeatingTriggerComponent(5));
        int spawnMobsTarget = entityWorld.createEntity();
        entityWorld.setComponent(spawnMobsTarget, new PositionComponent(new Vector2f(35, 12)));
        entityWorld.setComponent(spawnMobsTrigger, new CustomTargetComponent(spawnMobsTarget));
        int spawnMobsCondition = entityWorld.createEntity();
        int spawnMobsConditionTarget = entityWorld.createEntity();
        entityWorld.setComponent(spawnMobsConditionTarget, new NameComponent("Pseudospider"));
        entityWorld.setComponent(spawnMobsCondition, new CustomTargetComponent(spawnMobsConditionTarget));
        entityWorld.setComponent(spawnMobsCondition, new NameAmountConditionComponent(5));
        entityWorld.setComponent(spawnMobsTrigger, new TriggerConditionsComponent(spawnMobsCondition));
        int spawnMobsEffect = entityWorld.createEntity();
        int spawnInformation = entityWorld.createEntity();
        entityWorld.setComponent(spawnInformation, new SpawnTemplateComponent("units/pseudospider"));
        entityWorld.setComponent(spawnMobsEffect, new SpawnComponent(spawnInformation));
        entityWorld.setComponent(spawnMobsTrigger, new TriggeredEffectComponent(spawnMobsEffect));
        entityWorld.setComponent(spawnMobsTrigger, new TriggerSourceComponent(GAME_ENTITY));
        // Shop
        EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
        shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
        shop.setComponent(new PositionComponent(new Vector2f(52, 25)));
        shop.setComponent(new DirectionComponent(new Vector2f(-1, -1)));
        shop.setComponent(new ShopItemsComponent(MapDefaults.getShopItemTemplateNames()));
        shop.setComponent(new ShopRangeComponent(15));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(3, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void initializePlayer(EntityWorld entityWorld, int playerEntity) {
        super.initializePlayer(entityWorld, playerEntity);
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new LevelComponent(6));
        entityWorld.setComponent(characterEntity, new SpellsUpgradePointsComponent(6));
        for (int i = 0; i < 4; i++) {
            SpellUtil.learnSpell(entityWorld, characterEntity, i);
        }
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity) {
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new PositionComponent(new Vector2f(20, 20)));
        entityWorld.setComponent(characterEntity, new DirectionComponent(new Vector2f(0, -1)));
        entityWorld.setComponent(characterEntity, new TeamComponent(1));
    }
}
