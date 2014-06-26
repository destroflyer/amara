/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.*;
import amara.engine.network.NetworkServer;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.aggro.*;
import amara.game.entitysystem.systems.attributes.*;
import amara.game.entitysystem.systems.buffs.*;
import amara.game.entitysystem.systems.buffs.areas.*;
import amara.game.entitysystem.systems.camps.*;
import amara.game.entitysystem.systems.commands.*;
import amara.game.entitysystem.systems.effects.*;
import amara.game.entitysystem.systems.effects.audio.*;
import amara.game.entitysystem.systems.effects.buffs.*;
import amara.game.entitysystem.systems.effects.buffs.areas.*;
import amara.game.entitysystem.systems.effects.crowdcontrol.*;
import amara.game.entitysystem.systems.effects.damage.*;
import amara.game.entitysystem.systems.effects.general.*;
import amara.game.entitysystem.systems.effects.heal.*;
import amara.game.entitysystem.systems.effects.movement.*;
import amara.game.entitysystem.systems.effects.physics.*;
import amara.game.entitysystem.systems.effects.spawns.*;
import amara.game.entitysystem.systems.effects.spells.*;
import amara.game.entitysystem.systems.effects.triggers.*;
import amara.game.entitysystem.systems.general.*;
import amara.game.entitysystem.systems.movement.*;
import amara.game.entitysystem.systems.network.*;
import amara.game.entitysystem.systems.objectives.*;
import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.entitysystem.systems.players.*;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.*;
import amara.game.entitysystem.systems.units.*;
import amara.game.entitysystem.systems.visuals.*;
import amara.game.games.*;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class ServerEntitySystemAppState extends EntitySystemHeadlessAppState<IngameServerApplication>{

    public ServerEntitySystemAppState(){
        
    }

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
        networkServer.addMessageBackend(new AuthentificateClientsBackend(mainApplication.getGame(), entityWorld));
        networkServer.addMessageBackend(new UpdateNewClientBackend(entityWorld));
        networkServer.addMessageBackend(new InitializeClientBackend(mainApplication.getGame(), getAppState(GameRunningAppState.class)));
        IntersectionObserver intersectionObserver = new IntersectionObserver();
        addEntitySystem(new SetAutoAttacksCastAnimationsSystem());
        addEntitySystem(new SetSpellsCastersSystem());
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new CountdownPlayerRespawnSystem());
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCastingSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownEffectDelaySystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownBindingImmuneSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownSilenceImmuneSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new CountdownStunImmuneSystem());
        addEntitySystem(new CountdownAnimationLoopsSystem());
        addEntitySystem(new CheckOpenObjectivesSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue()));
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new CastSpellOnCooldownWhileAttackingSystem());
        addEntitySystem(new SetCastDurationOnCastingSystem());
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new PlayCastAnimationSystem());
        addEntitySystem(new CastSpellSystem());
        addEntitySystem(new UpdateAreaPositionsSystem());
        addEntitySystem(new CheckAreaBuffsSystem(intersectionObserver));
        addEntitySystem(new RemoveBuffsSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyPauseAudioSystem());
        addEntitySystem(new ApplyPlayAudioSystem());
        addEntitySystem(new ApplyStopAudioSystem());
        addEntitySystem(new ApplyAddBuffsSystem());
        addEntitySystem(new ApplyRemoveBuffsSystem());
        addEntitySystem(new ApplyAddBuffAreasSystem());
        addEntitySystem(new ApplyRemoveBuffAreasSystem());
        addEntitySystem(new ApplyAddBindingImmuneSystem());
        addEntitySystem(new ApplyAddBindingSystem());
        addEntitySystem(new ApplyRemoveBindingSystem());
        addEntitySystem(new ApplyAddSilenceImmuneSystem());
        addEntitySystem(new ApplyAddSilenceSystem());
        addEntitySystem(new ApplyRemoveSilenceSystem());
        addEntitySystem(new ApplyAddStunImmuneSystem());
        addEntitySystem(new ApplyAddStunSystem());
        addEntitySystem(new ApplyRemoveStunSystem());
        addEntitySystem(new ApplyAddTargetabilitySystem());
        addEntitySystem(new ApplyRemoveTargetabilitySystem());
        addEntitySystem(new ApplyAddVulnerabilitySystem());
        addEntitySystem(new ApplyRemoveVulnerabilitySystem());
        addEntitySystem(new ApplyPhysicalDamageSystem());
        addEntitySystem(new ApplyMagicDamageSystem());
        addEntitySystem(new ApplyHealSystem());
        addEntitySystem(new ApplyStopSystem());
        addEntitySystem(new ApplyMoveSystem());
        addEntitySystem(new ApplyActivateHitboxSystem());
        addEntitySystem(new ApplyDeactivateHitboxSystem());
        addEntitySystem(new ApplySpawnsSystems());
        addEntitySystem(new ApplyAddAutoAttackSpellEffectsSystem());
        addEntitySystem(new ApplyRemoveSpellEffectsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithExistingSpellsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithNewSpellsSystem());
        addEntitySystem(new ApplyTriggerSpellEffectsSystem());
        addEntitySystem(new ApplyAddComponentsSystem());
        addEntitySystem(new ApplyAddEffectTriggersSystem());
        addEntitySystem(new ApplyRemoveEntitySystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new HealthRegenerationSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new MaximumHealthSystem());
        addEntitySystem(new RemoveDeadUnitsSystem());
        addEntitySystem(new RemoveUnusedTriggersSystem());
        addEntitySystem(new IntersectionPushSystem(intersectionObserver));
        addEntitySystem(new RemoveCancelledMovementsEffectTriggersSystem());
        addEntitySystem(new PlayMovementAnimationsSystem());
        addEntitySystem(new UpdateWalkMovementsSystem());
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TargetedMovementSystem(intersectionObserver));
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(new RemoveFinishedMovementsSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionObserver));
        addEntitySystem(new TriggerCastingFinishedEffectSystem());
        addEntitySystem(new AggroSystem());
        addEntitySystem(new CheckCampAggroTargetSystem());
        addEntitySystem(new CheckCampMaximumAggroDistanceSystem());
        addEntitySystem(new CampResetSystem());
        addEntitySystem(new SetIdleAnimationsSystem());
        
        Game game = mainApplication.getGame();
        Map map = game.getMap();
        EntityWrapper mapEntity = entityWorld.getWrapped(entityWorld.createEntity());
        map.setEntity(mapEntity.getId());
        map.load(entityWorld);
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getStateManager().getState(DatabaseAppState.class);
        for(int i=0;i<mainApplication.getGame().getPlayers().length;i++){
            GamePlayer player = mainApplication.getGame().getPlayers()[i];
            EntityWrapper playerEntity = entityWorld.getWrapped(entityWorld.createEntity());
            playerEntity.setComponent(new PlayerIndexComponent(i));
            String login = databaseAppState.getString("SELECT login FROM users WHERE id = " + player.getLobbyPlayer().getID() + " LIMIT 1");
            playerEntity.setComponent(new NameComponent(login));
            LobbyPlayerData lobbyPlayerData = player.getLobbyPlayer().getPlayerData();
            String characterName = databaseAppState.getString("SELECT name FROM characters WHERE id = " + lobbyPlayerData.getCharacterID());
            EntityWrapper unit = EntityTemplate.createFromTemplate(entityWorld, characterName);
            unit.setComponent(new TitleComponent(login));
            int skinID = databaseAppState.getInteger("SELECT skinid FROM users_characters WHERE (userid = " + player.getLobbyPlayer().getID() + ") AND (characterid = " + lobbyPlayerData.getCharacterID() + ")");
            String skinName = "default";
            if(skinID != 0){
                skinName = databaseAppState.getString("SELECT name FROM characters_skins WHERE id = " + skinID);
            }
            unit.setComponent(new ModelComponent("Models/" + characterName + "/skin_" + skinName + ".xml"));
            playerEntity.setComponent(new SelectedUnitComponent(unit.getId()));
            map.spawn(entityWorld, playerEntity.getId());
            player.setEntityID(playerEntity.getId());
        }
        MapPhysicsInformation mapPhysicsInformation = map.getPhysicsInformation();
        
        System.out.println("Calculating navigation meshes...");
        PolyMapManager mapManager = new PolyMapManager(PolyHelper.fromShapes(mapPhysicsInformation.getObstacles()), mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight());
//        mapManager.calcNavigationMap(0.5);
//        mapManager.calcNavigationMap(0.75);
        mapManager.calcNavigationMap(1);
////        mapManager.calcNavigationMap(1.25); error, do not enable
//        mapManager.calcNavigationMap(1.5);
//        mapManager.calcNavigationMap(2);
//        mapManager.calcNavigationMap(3);
        System.out.println("Finished calculating navigation meshes.");
        
        addEntitySystem(new MapIntersectionSystem(mapManager));
        addEntitySystem(new CheckMapObjectiveSystem(map, getAppState(GameRunningAppState.class)));
        addEntitySystem(new PlayerDeathSystem(map));
        addEntitySystem(new PlayerRespawnSystem(game));
        
        addEntitySystem(new SendEntityChangesSystem(networkServer));
    }

    @Override
    public void update(float lastTimePerFrame){
        if(mainApplication.getGame().isStarted()){
            super.update(lastTimePerFrame);
        }
    }
}
