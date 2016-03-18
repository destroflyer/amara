/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.maps;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.spawns.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.objectives.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.spawns.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class Map_Etherdesert extends Map{

    public Map_Etherdesert(){
        
    }

    @Override
    public void load(EntityWorld entityWorld){
        EntityWrapper audioBackgroundMusic = entityWorld.getWrapped(entityWorld.createEntity());
        audioBackgroundMusic.setComponent(new AudioComponent("Sounds/music/desert_village.ogg"));
        audioBackgroundMusic.setComponent(new AudioVolumeComponent(1.75f));
        audioBackgroundMusic.setComponent(new AudioLoopComponent());
        audioBackgroundMusic.setComponent(new AudioGlobalComponent());
        audioBackgroundMusic.setComponent(new StartPlayingAudioComponent());
        //Nexus
        EntityWrapper nexus = entityWorld.getWrapped(entityWorld.createEntity());
        nexus.setComponent(new IsStructureComponent());
        nexus.setComponent(new NameComponent("Nexus"));
        nexus.setComponent(new ModelComponent("Models/column/skin_nexus.xml"));
        nexus.setComponent(new PositionComponent(new Vector2f(96, 87.75f)));
        nexus.setComponent(new DirectionComponent(new Vector2f(0, -1)));
        nexus.setComponent(new IsAliveComponent());
        int nexusBaseAttributesEntity = entityWorld.createEntity();
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatMaximumHealthComponent(1000));
        entityWorld.setComponent(nexusBaseAttributesEntity, new BonusFlatHealthRegenerationComponent(2));
        nexus.setComponent(new BaseAttributesComponent(nexusBaseAttributesEntity));
        nexus.setComponent(new RequestUpdateAttributesComponent());
        nexus.setComponent(new IsTargetableComponent());
        nexus.setComponent(new IsVulnerableComponent());
        nexus.setComponent(new TeamComponent(1));
        //Waves
        int spawnCasterEntity = entityWorld.createEntity();
        entityWorld.setComponent(spawnCasterEntity, new PositionComponent(new Vector2f(160, 150)));
        entityWorld.setComponent(spawnCasterEntity, new TeamComponent(0));
        int spawnSourceEntity = entityWorld.createEntity();
        entityWorld.setComponent(spawnSourceEntity, new EffectCastSourceComponent(spawnCasterEntity));
        for(int i=0;i<6;i++){
            EntityWrapper spawnTrigger = entityWorld.getWrapped(entityWorld.createEntity());
            spawnTrigger.setComponent(new RepeatingTriggerComponent(25));
            spawnTrigger.setComponent(new TimeSinceLastRepeatTriggerComponent(22));
            spawnTrigger.setComponent(new CustomTargetComponent(nexus.getId()));
            EntityWrapper spawnEffect = entityWorld.getWrapped(entityWorld.createEntity());
            EntityWrapper spawnInformation = entityWorld.getWrapped(entityWorld.createEntity());
            String unitTemplate = ((i < 3)?"etherdesert_creep_melee":"etherdesert_creep_range");
            spawnInformation.setComponent(new SpawnTemplateComponent(unitTemplate + "," + spawnTrigger.getId()));
            spawnInformation.setComponent(new SpawnMoveToTargetComponent());
            spawnInformation.setComponent(new SpawnAttackMoveComponent());
            spawnEffect.setComponent(new SpawnComponent(new int[]{spawnInformation.getId()}));
            spawnTrigger.setComponent(new TriggeredEffectComponent(spawnEffect.getId()));
            spawnTrigger.setComponent(new TriggerSourceComponent(spawnSourceEntity));
            spawnTrigger.setComponent(new TriggerDelayComponent(1.25f * i));
        }
        EntityWrapper gameObjective = entityWorld.getWrapped(entityWorld.createEntity());
        gameObjective.setComponent(new MissingEntitiesComponent(new int[]{nexus.getId()}));
        gameObjective.setComponent(new OpenObjectiveComponent());
        entityWorld.setComponent(entity, new MapObjectiveComponent(gameObjective.getId()));
        EntityWrapper playerDeathRules = entityWorld.getWrapped(entityWorld.createEntity());
        playerDeathRules.setComponent(new RespawnPlayersComponent());
        playerDeathRules.setComponent(new RespawnTimerComponent(3, 0));
        entityWorld.setComponent(entity, new PlayerDeathRulesComponent(playerDeathRules.getId()));
    }

    @Override
    public void spawnPlayer(EntityWorld entityWorld, int playerEntity){
        Vector2f position = new Vector2f();
        Vector2f direction = new Vector2f();
        int playerIndex = entityWorld.getComponent(playerEntity, PlayerIndexComponent.class).getIndex();
        switch(playerIndex){
            case 0:
                position = new Vector2f(125, 80);
                direction = new Vector2f(0, -1);
                break;
            
            case 1:
                position = new Vector2f(130, 80);
                direction = new Vector2f(0, -1);
                break;
            
            case 2:
                position = new Vector2f(135, 80);
                direction = new Vector2f(0, -1);
                break;
            
            case 3:
                position = new Vector2f(140, 80);
                direction = new Vector2f(0, -1);
                break;
        }
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        entityWorld.setComponent(characterEntity, new PositionComponent(position));
        entityWorld.setComponent(characterEntity, new DirectionComponent(direction));
        entityWorld.setComponent(characterEntity, new TeamComponent(1));
    }
}
