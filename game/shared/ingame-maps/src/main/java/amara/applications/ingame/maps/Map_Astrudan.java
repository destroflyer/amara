/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.DescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.maps.PlayerDeathRulesComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnPlayersComponent;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.RespawnTimerComponent;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopRangeComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.AutoAttackAnimationComponent;
import amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsBindableComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsKnockupableComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsSilencableComponent;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.IsStunnableComponent;
import amara.applications.ingame.entitysystem.components.units.types.IsMonsterComponent;
import amara.applications.ingame.entitysystem.components.visuals.ModelComponent;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.physics.shapes.Circle;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class Map_Astrudan extends Map {

    @Override
    public void load(EntityWorld entityWorld) {
        //Boss
        EntityWrapper boss = entityWorld.getWrapped(entityWorld.createEntity());
        boss.setComponent(new NameComponent("Yalee"));
        boss.setComponent(new DescriptionComponent("Stupid."));
        boss.setComponent(new IsMonsterComponent());
        boss.setComponent(new IsAlwaysVisibleComponent());
        boss.setComponent(new ModelComponent("Models/cow/skin_baron.xml"));
        EntityWrapper walkAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        walkAnimation.setComponent(new NameComponent("walk"));
        boss.setComponent(new WalkAnimationComponent(walkAnimation.getId()));
        EntityWrapper autoAttackAnimation = entityWorld.getWrapped(entityWorld.createEntity());
        autoAttackAnimation.setComponent(new NameComponent("auto_attack"));
        boss.setComponent(new AutoAttackAnimationComponent(autoAttackAnimation.getId()));
        boss.setComponent(new ScaleComponent(1.5f));
        boss.setComponent(new PositionComponent(new Vector2f(35, 12)));
        boss.setComponent(new DirectionComponent(new Vector2f(-0.5f, -1)));
        boss.setComponent(new HitboxComponent(new Circle(2.25f)));
        boss.setComponent(new IntersectionPushComponent());
        boss.setComponent(new CollisionGroupComponent(CollisionGroupComponent.MAP | CollisionGroupComponent.UNITS | CollisionGroupComponent.SPELL_TARGETS, CollisionGroupComponent.UNITS));
        boss.setComponent(new HitboxActiveComponent());
        boss.setComponent(new IsAliveComponent());
        int baseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(2000));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(30));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackDamageComponent(150));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatAttackSpeedComponent(0.6f));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatArmorComponent(50));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatMagicResistanceComponent(50));
        entityWorld.setComponent(baseAttributesEntity, new BonusFlatWalkSpeedComponent(2.5f));
        boss.setComponent(new BaseAttributesComponent(baseAttributesEntity));
        boss.setComponent(new RequestUpdateAttributesComponent());
        boss.setComponent(new SightRangeComponent(30));
        boss.setComponent(new IsTargetableComponent());
        boss.setComponent(new IsVulnerableComponent());
        boss.setComponent(new IsBindableComponent());
        boss.setComponent(new IsKnockupableComponent());
        boss.setComponent(new IsSilencableComponent());
        boss.setComponent(new IsStunnableComponent());
        EntityWrapper autoAttack = EntityTemplate.createFromTemplate(entityWorld, "spells/ranged_autoattack");
        boss.setComponent(new AutoAttackComponent(autoAttack.getId()));
        EntityWrapper bodyslam = EntityTemplate.createFromTemplate(entityWorld, "spells/bodyslam");
        boss.setComponent(new SpellsComponent(bodyslam.getId()));
        boss.setComponent(new CastSpellOnCooldownWhileAttackingComponent(0));
        boss.setComponent(new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.BOSS));
        boss.setComponent(new TeamComponent(0));
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
        for(int i=0;i<4;i++){
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
