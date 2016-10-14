/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.applications.ingame.entitysystem.components.camps.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.effects.heals.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.shop.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.bounties.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.shared.games.Game;
import amara.applications.ingame.shared.maps.*;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.EntityTemplate;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class Map_Arama extends Map{

    public Map_Arama(){
        spells = new MapSpells[]{
            new MapSpells(new String[]{"player_0", "player_1"},
                new MapSpell("spells/instant_transmission/base"),
                new MapSpell("spells/bleed_out/base"),
                new MapSpell("spells/x_defense/base"),
                new MapSpell("spells/fatique/base"),
                new MapSpell("spells/recovery/base")
            ),
            new MapSpells("backport", new MapSpell("spells/backport/base(-1)"))
        };
    }
    private final float laneCenterY = 260.25f;
    private final float towerOffsetY = 6;
    private final float timeUntilWaveStart = 5;
    private final float waveInterval = 55;
    private int[] backportPositionEntities = new int[2];

    @Override
    public void load(EntityWorld entityWorld){
        EntityWrapper audioBackgroundMusic = entityWorld.getWrapped(entityWorld.createEntity());
        audioBackgroundMusic.setComponent(new AudioComponent("Sounds/music/minds_eye.ogg"));
        audioBackgroundMusic.setComponent(new AudioVolumeComponent(2));
        audioBackgroundMusic.setComponent(new AudioLoopComponent());
        audioBackgroundMusic.setComponent(new AudioGlobalComponent());
        audioBackgroundMusic.setComponent(new StartPlayingAudioComponent());
        //Backports
        backportPositionEntities[0] = entityWorld.createEntity();
        backportPositionEntities[1] = entityWorld.createEntity();
        entityWorld.setComponent(backportPositionEntities[0], new PositionComponent(new Vector2f(405, laneCenterY)));
        entityWorld.setComponent(backportPositionEntities[1], new PositionComponent(new Vector2f(120, laneCenterY)));
        //Nexus
        EntityWrapper[] nexi = new EntityWrapper[2];
        float[] fountainX = new float[]{408, 117};
        float[] nexiX = new float[]{378, 147};
        for(int i=0;i<2;i++){
            //Fountain
            EntityWrapper fountain = entityWorld.getWrapped(entityWorld.createEntity());
            fountain.setComponent(new NameComponent("Fountain"));
            fountain.setComponent(new TeamComponent(i + 1));
            //Fountain Area (Allies)
            EntityWrapper fountainBuffArea_Allies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainBuffArea_Allies.setComponent(new HitboxComponent(new Rectangle(24, 36)));
            fountainBuffArea_Allies.setComponent(new HitboxActiveComponent());
            fountainBuffArea_Allies.setComponent(new PositionComponent(new Vector2f(fountainX[i], 260)));
            EntityWrapper fountainBuff_Allies = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper fountainBuffEffect_Allies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainBuffEffect_Allies.setComponent(new HealComponent("(0.021 * target.maximumHealth)"));
            fountainBuff_Allies.setComponent(new RepeatingEffectComponent(fountainBuffEffect_Allies.getId(), 0.25f));
            fountainBuffArea_Allies.setComponent(new AreaBuffComponent(fountainBuff_Allies.getId()));
            EntityWrapper fountainAreaBuffTargetRules_Allies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainAreaBuffTargetRules_Allies.setComponent(new AcceptAlliesComponent());
            fountainBuffArea_Allies.setComponent(new AreaBuffTargetRulesComponent(fountainAreaBuffTargetRules_Allies.getId()));
            fountainBuffArea_Allies.setComponent(new AreaOriginComponent(Game.ENTITY));
            fountainBuffArea_Allies.setComponent(new AreaSourceComponent(fountain.getId()));
            //Fountain Area (Enemies)
            EntityWrapper fountainBuffArea_Enemies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainBuffArea_Enemies.setComponent(new HitboxComponent(new Rectangle(24, 36)));
            fountainBuffArea_Enemies.setComponent(new HitboxActiveComponent());
            fountainBuffArea_Enemies.setComponent(new PositionComponent(new Vector2f(fountainX[i], 260)));
            EntityWrapper fountainBuff_Enemies = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper fountainBuffEffect__Enemies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainBuffEffect__Enemies.setComponent(new MagicDamageComponent("1000"));
            fountainBuff_Enemies.setComponent(new RepeatingEffectComponent(fountainBuffEffect__Enemies.getId(), 0.5f));
            fountainBuffArea_Enemies.setComponent(new AreaBuffComponent(fountainBuff_Enemies.getId()));
            EntityWrapper fountainAreaBuffTargetRules__Enemies = entityWorld.getWrapped(entityWorld.createEntity());
            fountainAreaBuffTargetRules__Enemies.setComponent(new AcceptEnemiesComponent());
            fountainBuffArea_Enemies.setComponent(new AreaBuffTargetRulesComponent(fountainAreaBuffTargetRules__Enemies.getId()));
            fountainBuffArea_Enemies.setComponent(new AreaOriginComponent(Game.ENTITY));
            fountainBuffArea_Enemies.setComponent(new AreaSourceComponent(fountain.getId()));
            //Nexus
            EntityWrapper nexus = entityWorld.getWrapped(entityWorld.createEntity());
            nexus.setComponent(new NameComponent("Nexus"));
            nexus.setComponent(new IsStructureComponent());
            nexus.setComponent(new IsAlwaysVisibleComponent());
            nexus.setComponent(new TeamModelComponent("Models/3dsa_fantasy_forest_waypoint_base/skin_nexus.xml"));
            nexus.setComponent(new PositionComponent(new Vector2f(nexiX[i], laneCenterY)));
            nexus.setComponent(new DirectionComponent(new Vector2f(0, -1)));
            nexus.setComponent(new HitboxComponent(new Circle(3.5f)));
            int baseAttributesEntity = entityWorld.createEntity();
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatMaximumHealthComponent(1000));
            entityWorld.setComponent(baseAttributesEntity, new BonusFlatHealthRegenerationComponent(2));
            nexus.setComponent(new BaseAttributesComponent(baseAttributesEntity));
            nexus.setComponent(new RequestUpdateAttributesComponent());
            nexus.setComponent(new SightRangeComponent(28));
            nexus.setComponent(new IsAliveComponent());
            nexus.setComponent(new TeamComponent(i + 1));
            nexi[i] = nexus;
        }
        for(int i=0;i<2;i++){
            EntityWrapper shop = entityWorld.getWrapped(entityWorld.createEntity());
            shop.setComponent(new IsAlwaysVisibleComponent());
            shop.setComponent(new ModelComponent("Models/chest/skin.xml"));
            shop.setComponent(new PositionComponent(new Vector2f(((i == 0)?410:115), laneCenterY)));
            shop.setComponent(new DirectionComponent(new Vector2f(((i == 0)?-1:1), 0)));
            shop.setComponent(new ShopRangeComponent(10));
            shop.setComponent(new TeamComponent(i + 1));
            //Towers
            Vector2f tower1Position = new Vector2f(((i == 0)?354:171), laneCenterY + towerOffsetY);
            Vector2f tower2Position = new Vector2f(((i == 0)?321:204), laneCenterY - towerOffsetY);
            Vector2f towerDirection = new Vector2f(((i == 0)?-1:1), 0);
            EntityWrapper tower1 = EntityTemplate.createFromTemplate(entityWorld, "structures/tower");
            tower1.setComponent(new PositionComponent(tower1Position));
            tower1.setComponent(new DirectionComponent(towerDirection));
            tower1.setComponent(new TeamComponent(i + 1));
            tower1.removeComponent(IsTargetableComponent.class);
            tower1.removeComponent(IsVulnerableComponent.class);
            EntityWrapper tower2 = EntityTemplate.createFromTemplate(entityWorld, "structures/tower");
            tower2.setComponent(new PositionComponent(tower2Position));
            tower2.setComponent(new DirectionComponent(towerDirection));
            tower2.setComponent(new TeamComponent(i + 1));
            //Tower 1 death trigger / Nexus Vulnerability
            EntityWrapper tower1DeathTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            tower1DeathTrigger.setComponent(new DeathTriggerComponent());
            tower1DeathTrigger.setComponent(new CustomTargetComponent(nexi[i].getId()));
            EntityWrapper tower1DeathEffect = entityWorld.getWrapped(entityWorld.createEntity());
            tower1DeathEffect.setComponent(new AddTargetabilityComponent());
            tower1DeathEffect.setComponent(new AddVulnerabilityComponent());
            tower1DeathTrigger.setComponent(new TriggeredEffectComponent(tower1DeathEffect.getId()));
            tower1DeathTrigger.setComponent(new TriggerSourceComponent(tower1.getId()));
            //Tower 1 death trigger / Remains
            EntityWrapper tower1DeathTrigger_2 = entityWorld.getWrapped(entityWorld.createEntity());
            tower1DeathTrigger_2.setComponent(new DeathTriggerComponent());
            int spawnTargetEntity_Tower1Remains = entityWorld.createEntity();
            entityWorld.setComponent(spawnTargetEntity_Tower1Remains, new PositionComponent(tower1Position));
            entityWorld.setComponent(spawnTargetEntity_Tower1Remains, new DirectionComponent(towerDirection));
            tower1DeathTrigger_2.setComponent(new CustomTargetComponent(spawnTargetEntity_Tower1Remains));
            EntityWrapper tower1DeathEffect_2 = entityWorld.getWrapped(entityWorld.createEntity());
            int spawnInformationEntity_Tower1Remains = entityWorld.createEntity();
            entityWorld.setComponent(spawnInformationEntity_Tower1Remains, new SpawnTemplateComponent("structures/tower_remains"));
            tower1DeathEffect_2.setComponent(new SpawnComponent(spawnInformationEntity_Tower1Remains));
            tower1DeathTrigger_2.setComponent(new TriggeredEffectComponent(tower1DeathEffect_2.getId()));
            tower1DeathTrigger_2.setComponent(new TriggerSourceComponent(tower1.getId()));
            //Tower 2 death trigger / Tower 1 Vulnerability
            EntityWrapper tower2DeathTrigger_1 = entityWorld.getWrapped(entityWorld.createEntity());
            tower2DeathTrigger_1.setComponent(new DeathTriggerComponent());
            tower2DeathTrigger_1.setComponent(new CustomTargetComponent(tower1.getId()));
            EntityWrapper tower2DeathEffect_1 = entityWorld.getWrapped(entityWorld.createEntity());
            tower2DeathEffect_1.setComponent(new AddTargetabilityComponent());
            tower2DeathEffect_1.setComponent(new AddVulnerabilityComponent());
            tower2DeathTrigger_1.setComponent(new TriggeredEffectComponent(tower2DeathEffect_1.getId()));
            tower2DeathTrigger_1.setComponent(new TriggerSourceComponent(tower2.getId()));
            //Tower 2 death trigger / Remains
            EntityWrapper tower2DeathTrigger_2 = entityWorld.getWrapped(entityWorld.createEntity());
            tower2DeathTrigger_2.setComponent(new DeathTriggerComponent());
            int spawnTargetEntity_Tower2Remains = entityWorld.createEntity();
            entityWorld.setComponent(spawnTargetEntity_Tower2Remains, new PositionComponent(tower2Position));
            entityWorld.setComponent(spawnTargetEntity_Tower2Remains, new DirectionComponent(towerDirection));
            tower2DeathTrigger_2.setComponent(new CustomTargetComponent(spawnTargetEntity_Tower2Remains));
            EntityWrapper tower2DeathEffect_2 = entityWorld.getWrapped(entityWorld.createEntity());
            int spawnInformationEntity_Tower2Remains = entityWorld.createEntity();
            entityWorld.setComponent(spawnInformationEntity_Tower2Remains, new SpawnTemplateComponent("structures/tower_remains"));
            tower2DeathEffect_2.setComponent(new SpawnComponent(spawnInformationEntity_Tower2Remains));
            tower2DeathTrigger_2.setComponent(new TriggeredEffectComponent(tower2DeathEffect_2.getId()));
            tower2DeathTrigger_2.setComponent(new TriggerSourceComponent(tower2.getId()));
            //Waves
            int spawnCasterEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnCasterEntity, new PositionComponent(new Vector2f(nexiX[i] + (((i == 0)?-1:1) * 5), laneCenterY)));
            entityWorld.setComponent(spawnCasterEntity, new TeamComponent(i + 1));
            int spawnSourceEntity = entityWorld.createEntity();
            entityWorld.setComponent(spawnSourceEntity, new EffectCastSourceComponent(spawnCasterEntity));
            for(int r=0;r<6;r++){
                EntityWrapper spawnTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                spawnTrigger.setComponent(new RepeatingTriggerComponent(waveInterval));
                spawnTrigger.setComponent(new TimeSinceLastRepeatTriggerComponent(waveInterval - timeUntilWaveStart));
                spawnTrigger.setComponent(new CustomTargetComponent(nexi[(i == 0)?1:0].getId()));
                EntityWrapper spawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                String unitTemplate = ((r < 3)?"etherdesert_creep_melee":"etherdesert_creep_range");
                spawnInformation.setComponent(new SpawnTemplateComponent(unitTemplate + "," + spawnTrigger.getId()));
                spawnInformation.setComponent(new SpawnMoveToTargetComponent());
                spawnInformation.setComponent(new SpawnAttackMoveComponent());
                spawnEffect.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
                spawnTrigger.setComponent(new TriggeredEffectComponent(spawnEffect.getId()));
                spawnTrigger.setComponent(new TriggerSourceComponent(spawnSourceEntity));
                spawnTrigger.setComponent(new TriggerDelayComponent(1.25f * r));
            }
        }
        //Camps
        for(int i=0;i<2;i++){
            EntityWrapper camp = entityWorld.getWrapped(entityWorld.createEntity());
            camp.setComponent(new CampUnionAggroComponent());
            camp.setComponent(new CampMaximumAggroDistanceComponent(10));
            camp.setComponent(new CampHealthResetComponent());
            int[] campSpawnInformationEntities = new int[3];
            for(int r=0;r<2;r++){
                EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
                spawnInformation.setComponent(new SpawnTemplateComponent("units/pseudospider", "arama_camp_pseudospider," + i + "," + r));
                campSpawnInformationEntities[r] = spawnInformation.getId();
            }
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            spawnInformation.setComponent(new SpawnTemplateComponent("units/beetle_golem", "arama_camp_beetle_golem," + i));
            campSpawnInformationEntities[2] = spawnInformation.getId();
            camp.setComponent(new CampSpawnInformationComponent(campSpawnInformationEntities));
            camp.setComponent(new CampRespawnDurationComponent(40));
            camp.setComponent(new CampSpawnComponent());
        }
        //Boss
        EntityWrapper bossCamp = entityWorld.getWrapped(entityWorld.createEntity());
        bossCamp.setComponent(new CampMaximumAggroDistanceComponent(20));
        bossCamp.setComponent(new CampHealthResetComponent());
        EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
        spawnInformation.setComponent(new SpawnTemplateComponent("arama_boss", "arama_camp_boss"));
        bossCamp.setComponent(new CampSpawnInformationComponent(spawnInformation.getId()));
        bossCamp.setComponent(new CampRespawnDurationComponent(240));
        bossCamp.setComponent(new CampSpawnComponent());
        //GameObjective
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        EntityWrapper nexusObjective1 = entityWorld.getWrapped(entityWorld.createEntity());
        nexusObjective1.setComponent(new MissingEntitiesComponent(nexi[0].getId()));
        nexusObjective1.setComponent(new OpenObjectiveComponent());
        EntityWrapper nexusObjective2 = entityWorld.getWrapped(entityWorld.createEntity());
        nexusObjective2.setComponent(new MissingEntitiesComponent(nexi[1].getId()));
        nexusObjective2.setComponent(new OpenObjectiveComponent());
        gameObjective.setComponent(new OrObjectivesComponent(nexusObjective1.getId(), nexusObjective2.getId()));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(5, (1f / 60)));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void initializePlayer(EntityWorld entityWorld, int playerEntity){
        super.initializePlayer(entityWorld, playerEntity);
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        int teamEntity = ((playerIndex % 2) + 1);
        entityWorld.setComponent(characterEntity, new TeamComponent(teamEntity));
        spells[1].getMapSpells()[0] = new MapSpell("spells/backport/base(" + backportPositionEntities[teamEntity - 1] + ")");
        //Bounty
        EntityWrapper characterBounty = entityWorld.getWrapped(entityWorld.createEntity());
        characterBounty.setComponent(new BountyCharacterKillComponent());
        characterBounty.setComponent(new BountyGoldComponent(300));
        int bountyRulesEntity = entityWorld.createEntity();
        entityWorld.setComponent(bountyRulesEntity, new RequireCharacterComponent());
        entityWorld.setComponent(bountyRulesEntity, new AcceptEnemiesComponent());
        characterBounty.setComponent(new BountyRulesComponent(bountyRulesEntity));
        entityWorld.setComponent(characterEntity, new BountyComponent(characterBounty.getId()));
        //GoldPerTime
        int buffEntity = entityWorld.createEntity();
        int buffAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(buffAttributesEntity, new BonusFlatGoldPerSecondComponent(2.4f));
        entityWorld.setComponent(buffEntity, new ContinuousAttributesComponent(buffAttributesEntity));
        entityWorld.setComponent(buffEntity, new KeepOnDeathComponent());
        ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, buffEntity);
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity){
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        int playerTeamIndex = (playerIndex / 2);
        Vector2f position = new Vector2f(0, laneCenterY - (playerTeamIndex * 5));
        Vector2f direction = new Vector2f();
        int teamEntity = entityWorld.getComponent(characterEntity, TeamComponent.class).getTeamEntity();
        switch(teamEntity){
            case 1:
                position.setX(405);
                direction.setX(-1);
                break;
            
            case 2:
                position.setX(120);
                direction.setX(1);
                break;
        }
        entityWorld.setComponent(characterEntity, new PositionComponent(position));
        entityWorld.setComponent(characterEntity, new DirectionComponent(direction));
    }
}
