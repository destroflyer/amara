/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import java.util.Iterator;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.input.casts.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.movement.MovementSystem;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.targets.TargetUtil;

/**
 *
 * @author Carl
 */
public class ExecutePlayerCommandsSystem implements EntitySystem{
    
    public ExecutePlayerCommandsSystem(Queue<PlayerCommand> playerCommandsQueue){
        this.playerCommandsQueue = playerCommandsQueue;
    }
    private Queue<PlayerCommand> playerCommandsQueue;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        Iterator<PlayerCommand> playerCommandsIterator = playerCommandsQueue.getIterator();
        while(playerCommandsIterator.hasNext()){
            PlayerCommand playerCommand = playerCommandsIterator.next();
            Command command = playerCommand.getCommand();
            EntityWrapper player = entityWorld.getWrapped(getPlayerEntityID(entityWorld, playerCommand.getClientID()));
            int selectedUnit = player.getComponent(SelectedUnitComponent.class).getEntityID();
            if(entityWorld.hasComponent(selectedUnit, IsAliveComponent.class)){
                if(command instanceof MoveCommand){
                    MoveCommand moveCommand = (MoveCommand) command;
                    int targetPositionEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetPositionEntity, new PositionComponent(moveCommand.getPosition()));
                    boolean wasSuccessfull = move(entityWorld, selectedUnit, targetPositionEntity, -1);
                    if(!wasSuccessfull){
                        entityWorld.removeEntity(targetPositionEntity);
                    }
                }
                else if(command instanceof StopCommand){
                    StopCommand stopCommand = (StopCommand) command;
                    entityWorld.removeComponent(selectedUnit, MovementComponent.class);
                    entityWorld.removeComponent(selectedUnit, AutoAttackTargetComponent.class);
                }
                else if(command instanceof AutoAttackCommand){
                    AutoAttackCommand autoAttackCommand = (AutoAttackCommand) command;
                    if(entityWorld.hasComponent(selectedUnit, AutoAttackComponent.class)){
                        if(PerformAutoAttacksSystem.isAttackable(entityWorld, selectedUnit, autoAttackCommand.getTargetEntity())){
                            entityWorld.removeComponent(selectedUnit, MovementComponent.class);
                            entityWorld.setComponent(selectedUnit, new AutoAttackTargetComponent(autoAttackCommand.getTargetEntity()));
                        }
                    }
                }
                else if(command instanceof CastSelfcastSpellCommand){
                    CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                    int spellEntity = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntities()[castSelfcastSpellCommand.getSpellIndex()];
                    int castInformationEntity = entityWorld.createEntity();
                    entityWorld.setComponent(castInformationEntity, new TargetComponent(selectedUnit));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castInformationEntity));
                }
                else if(command instanceof CastSingleTargetSpellCommand){
                    CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                    int spellEntity = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntities()[castSingleTargetSpellCommand.getSpellIndex()];
                    int castInformationEntity = entityWorld.createEntity();
                    entityWorld.setComponent(castInformationEntity, new TargetComponent(castSingleTargetSpellCommand.getTargetEntityID()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castInformationEntity));
                }
                else if(command instanceof CastLinearSkillshotSpellCommand){
                    CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                    int spellEntity = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntities()[castLinearSkillshotSpellCommand.getSpellIndex()];
                    int castInformationEntity = entityWorld.createEntity();
                    entityWorld.setComponent(castInformationEntity, new DirectionComponent(castLinearSkillshotSpellCommand.getDirection().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castInformationEntity));
                }
                else if(command instanceof CastPositionalSkillshotSpellCommand){
                    CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                    int spellEntity = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntities()[castPositionalSkillshotSpellCommand.getSpellIndex()];
                    int castInformationEntity = entityWorld.createEntity();
                    entityWorld.setComponent(castInformationEntity, new PositionComponent(castPositionalSkillshotSpellCommand.getPosition().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castInformationEntity));
                }
            }
        }
        playerCommandsQueue.clear();
    }
    
    private int getPlayerEntityID(EntityWorld entityWorld, int clientID){
        for(int entity : entityWorld.getEntitiesWithAll(ClientComponent.class)){
            if(entityWorld.getComponent(entity, ClientComponent.class).getClientID() == clientID){
                return entity;
            }
        }
        return -1;
    }
    
    public static void castSpell(EntityWorld entityWorld, int casterEntity, CastSpellComponent castSpellComponent){
        if((!entityWorld.hasAnyComponent(casterEntity, IsSilencedComponent.class, IsStunnedComponent.class)) && (!entityWorld.hasComponent(castSpellComponent.getSpellEntity(), RemainingCooldownComponent.class))){
            int targetEntity = -1;
            TargetComponent targetComponent = entityWorld.getComponent(castSpellComponent.getCastInformationEntity(), TargetComponent.class);
            PositionComponent positionComponent = entityWorld.getComponent(castSpellComponent.getCastInformationEntity(), PositionComponent.class);
            if(targetComponent != null){
                targetEntity = targetComponent.getTargetEntity();
            }
            else if(positionComponent != null){
                targetEntity = entityWorld.createEntity();
                entityWorld.setComponent(targetEntity, new PositionComponent(positionComponent.getPosition().clone()));
            }
            boolean canBeCasted = true;
            if(targetEntity != -1){
                SpellTargetRulesComponent spellTargetRulesComponent = entityWorld.getComponent(castSpellComponent.getSpellEntity(), SpellTargetRulesComponent.class);
                if(spellTargetRulesComponent != null){
                    canBeCasted = TargetUtil.isValidTarget(entityWorld, casterEntity, targetEntity, spellTargetRulesComponent.getTargetRulesEntity());
                }
            }
            if(canBeCasted){
                boolean castInstant = true;
                RangeComponent rangeComponent = entityWorld.getComponent(castSpellComponent.getSpellEntity(), RangeComponent.class);
                if(rangeComponent != null){
                    float range = rangeComponent.getDistance();
                    Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
                    Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                    float distance = targetPosition.distance(casterPosition);
                    if(distance > range){
                        move(entityWorld, casterEntity, targetEntity, range);
                        EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                        effectTrigger.setComponent(new TargetReachedTriggerComponent());
                        effectTrigger.setComponent(new SourceTargetComponent());
                        EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                        effect.setComponent(new AddComponentsComponent(castSpellComponent));
                        effect.setComponent(new StopComponent());
                        effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                        effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                        effectTrigger.setComponent(new TriggerOnceComponent());
                        castInstant = false;
                    }
                }
                if(castInstant){
                    entityWorld.setComponent(casterEntity, castSpellComponent);
                }
            }
        }
    }
    
    private static boolean move(EntityWorld entityWorld, int selectedUnit, int targetEntity, float sufficientDistance){
        if(MovementSystem.canMove(entityWorld, selectedUnit)){
            boolean isAllowed = true;
            MovementComponent movementComponent = entityWorld.getComponent(selectedUnit, MovementComponent.class);
            if(movementComponent != null){
                isAllowed = entityWorld.hasComponent(movementComponent.getMovementEntity(), MovementIsCancelableComponent.class);
            }
            if(isAllowed){
                entityWorld.removeComponent(selectedUnit, AutoAttackTargetComponent.class);
                EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                movement.setComponent(new MovementTargetComponent(targetEntity));
                if(sufficientDistance != -1){
                    movement.setComponent(new MovementTargetSufficientDistanceComponent(sufficientDistance));
                }
                movement.setComponent(new MovementSpeedComponent(2.5f));
                movement.setComponent(new MovementIsCancelableComponent());
                WalkAnimationComponent walkAnimationComponent = entityWorld.getComponent(selectedUnit, WalkAnimationComponent.class);
                if(walkAnimationComponent != null){
                    movement.setComponent(new MovementAnimationComponent(walkAnimationComponent.getAnimationEntity()));
                }
                entityWorld.setComponent(selectedUnit, new MovementComponent(movement.getId()));
                return true;
            }
        }
        return false;
    }
}
