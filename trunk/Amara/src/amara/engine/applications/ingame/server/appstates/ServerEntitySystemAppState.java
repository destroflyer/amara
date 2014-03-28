/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.appstates;

import amara.engine.applications.*;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.network.backends.*;
import amara.engine.applications.masterserver.server.appstates.DatabaseAppState;
import amara.engine.appstates.*;
import amara.engine.network.NetworkServer;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.aggro.*;
import amara.game.entitysystem.systems.attributes.*;
import amara.game.entitysystem.systems.buffs.*;
import amara.game.entitysystem.systems.commands.*;
import amara.game.entitysystem.systems.effects.*;
import amara.game.entitysystem.systems.effects.buffs.*;
import amara.game.entitysystem.systems.effects.crowdcontrol.*;
import amara.game.entitysystem.systems.effects.damage.*;
import amara.game.entitysystem.systems.effects.heal.*;
import amara.game.entitysystem.systems.effects.movement.*;
import amara.game.entitysystem.systems.effects.spells.*;
import amara.game.entitysystem.systems.effects.triggers.*;
import amara.game.entitysystem.systems.general.*;
import amara.game.entitysystem.systems.movement.*;
import amara.game.entitysystem.systems.network.*;
import amara.game.entitysystem.systems.objectives.*;
import amara.game.entitysystem.systems.physics.*;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.*;
import amara.game.entitysystem.systems.visuals.*;
import amara.game.games.GamePlayer;
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
        addEntitySystem(new SendEntityChangesSystem(networkServer));
        addEntitySystem(new UpdateAttributesSystem());
        addEntitySystem(new CountdownLifetimeSystem());
        addEntitySystem(new CountdownBuffsSystem());
        addEntitySystem(new CountdownCooldownSystem());
        addEntitySystem(new CountdownBindingSystem());
        addEntitySystem(new CountdownBindingImmuneSystem());
        addEntitySystem(new CountdownSilenceSystem());
        addEntitySystem(new CountdownSilenceImmuneSystem());
        addEntitySystem(new CountdownStunSystem());
        addEntitySystem(new CountdownStunImmuneSystem());
        addEntitySystem(new CountdownAnimationLoopsSystem());
        addEntitySystem(new StopAnimationsSystem());
        addEntitySystem(new CheckOpenObjectivesSystem());
        addEntitySystem(new ExecutePlayerCommandsSystem(getAppState(ReceiveCommandsAppState.class).getPlayerCommandsQueue()));
        addEntitySystem(new AttackAggroedTargetsSystem());
        addEntitySystem(new PerformAutoAttacksSystem());
        addEntitySystem(new SetCooldownOnCastingSystem());
        addEntitySystem(new PlayCastAnimationSystem());
        addEntitySystem(new CastSelfcastSpellSystem());
        addEntitySystem(new CastSingleTargetSpellSystem());
        addEntitySystem(new CastLinearSkillshotSpellSystem());
        addEntitySystem(new CastPositionalSkillshotSpellSystem());
        addEntitySystem(new RemoveBuffsSystem());
        addEntitySystem(new RepeatingBuffEffectsSystem());
        addEntitySystem(new CalculateEffectImpactSystem());
        addEntitySystem(new ApplyAddBuffsSystem());
        addEntitySystem(new ApplyRemoveBuffsSystem());
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
        addEntitySystem(new ApplyMoveToEntityPositionSystem());
        addEntitySystem(new ApplyReplaceSpellsWithExistingSpellsSystem());
        addEntitySystem(new ApplyReplaceSpellsWithNewSpellsSystem());
        addEntitySystem(new RemoveAppliedEffectsSystem());
        addEntitySystem(new DeathSystem());
        addEntitySystem(new MaximumHealthSystem());
        IntersectionSystem intersectionSystem = new IntersectionSystem();
        addEntitySystem(new IntersectionAntiGhostSystem(intersectionSystem));
        addEntitySystem(new MovementSystem());
        addEntitySystem(new TargetedMovementSystem(intersectionSystem));
        addEntitySystem(new TransformUpdateSystem());
        addEntitySystem(new TriggerCollisionEffectSystem(intersectionSystem));
        addEntitySystem(new TriggerTargetReachedEffectSystem());
        addEntitySystem(intersectionSystem);
        addEntitySystem(new AggroSystem());
        
        Map map = mainApplication.getGame().getMap();
        map.load(entityWorld);
        DatabaseAppState databaseAppState = mainApplication.getMasterServer().getStateManager().getState(DatabaseAppState.class);
        for(int i=0;i<mainApplication.getGame().getPlayers().length;i++){
            GamePlayer player = mainApplication.getGame().getPlayers()[i];
            EntityWrapper playerEntity = entityWorld.getWrapped(entityWorld.createEntity());
            String login = databaseAppState.getString("SELECT login FROM users WHERE id = " + player.getPlayerData().getID() + " LIMIT 1");
            playerEntity.setComponent(new NameComponent(login));
            EntityWrapper unit = EntityTemplate.createFromTemplate(entityWorld, player.getPlayerData().getUnitTemplate());
            unit.setComponent(new TitleComponent(login));
            map.spawn(entityWorld, i, unit.getId());
            playerEntity.setComponent(new SelectedUnitComponent(unit.getId()));
            player.setEntityID(playerEntity.getId());
        }
        MapPhysicsInformation mapPhysicsInformation = map.getPhysicsInformation();
        addEntitySystem(new MapIntersectionSystem(mapPhysicsInformation.getWidth(), mapPhysicsInformation.getHeight(), mapPhysicsInformation.getObstacles()));
        addEntitySystem(new CheckMapObjectiveSystem(map, getAppState(GameRunningAppState.class)));
    }

    @Override
    public void update(float lastTimePerFrame){
        if(mainApplication.getGame().isStarted()){
            super.update(lastTimePerFrame);
        }
    }
}
