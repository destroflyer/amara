/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import java.util.Iterator;
import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.applications.ingame.client.appstates.SendPlayerCommandsAppState;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.targets.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.systems.movement.MovementSystem;
import amara.game.entitysystem.systems.spells.*;
import amara.game.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.game.entitysystem.systems.targets.TargetUtil;
import amara.game.entitysystem.systems.units.UnitUtil;

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
            int playerEntity = getPlayerEntity(entityWorld, playerCommand.getClientID());
            int selectedUnit = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntity();
            if(entityWorld.hasComponent(selectedUnit, IsAliveComponent.class)){
                if(command instanceof MoveCommand){
                    MoveCommand moveCommand = (MoveCommand) command;
                    int targetPositionEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetPositionEntity, new PositionComponent(moveCommand.getPosition()));
                    boolean wasSuccessfull = walk(entityWorld, selectedUnit, targetPositionEntity, -1);
                    if(!wasSuccessfull){
                        entityWorld.removeEntity(targetPositionEntity);
                    }
                }
                else if(command instanceof StopCommand){
                    StopCommand stopCommand = (StopCommand) command;
                    UnitUtil.tryCancelAction(entityWorld, selectedUnit);
                }
                else if(command instanceof AutoAttackCommand){
                    AutoAttackCommand autoAttackCommand = (AutoAttackCommand) command;
                    AutoAttackComponent autoAttackComponent = entityWorld.getComponent(selectedUnit, AutoAttackComponent.class);
                    if(autoAttackComponent != null){
                        if(PerformAutoAttacksSystem.isAttackable(entityWorld, selectedUnit, autoAttackCommand.getTargetEntity())){
                            if(UnitUtil.tryCancelAction(entityWorld, selectedUnit)){
                                entityWorld.setComponent(selectedUnit, new AggroTargetComponent(autoAttackCommand.getTargetEntity()));
                            }
                        }
                    }
                }
                else if(command instanceof CastSelfcastSpellCommand){
                    CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                    int spellEntity = SendPlayerCommandsAppState.getSpellEntity(entityWorld, selectedUnit, castSelfcastSpellCommand.getSpellIndex());
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, -1));
                }
                else if(command instanceof CastSingleTargetSpellCommand){
                    CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                    int spellEntity = SendPlayerCommandsAppState.getSpellEntity(entityWorld, selectedUnit, castSingleTargetSpellCommand.getSpellIndex());
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castSingleTargetSpellCommand.getTargetEntityID()));
                }
                else if(command instanceof CastLinearSkillshotSpellCommand){
                    CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                    int spellEntity = SendPlayerCommandsAppState.getSpellEntity(entityWorld, selectedUnit, castLinearSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new DirectionComponent(castLinearSkillshotSpellCommand.getDirection().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, targetEntity));
                }
                else if(command instanceof CastPositionalSkillshotSpellCommand){
                    CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                    int spellEntity = SendPlayerCommandsAppState.getSpellEntity(entityWorld, selectedUnit, castPositionalSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new PositionComponent(castPositionalSkillshotSpellCommand.getPosition().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, targetEntity));
                }
            }
        }
        playerCommandsQueue.clear();
    }
    
    private int getPlayerEntity(EntityWorld entityWorld, int clientID){
        for(int entity : entityWorld.getEntitiesWithAll(ClientComponent.class)){
            if(entityWorld.getComponent(entity, ClientComponent.class).getClientID() == clientID){
                return entity;
            }
        }
        return -1;
    }
    
    public static void castSpell(EntityWorld entityWorld, int casterEntity, CastSpellComponent castSpellComponent){
        AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
        boolean isOnCooldown = entityWorld.hasComponent(castSpellComponent.getSpellEntity(), RemainingCooldownComponent.class);
        if(CastSpellSystem.canCast(entityWorld, casterEntity, castSpellComponent.getSpellEntity()) && (!isOnCooldown)){
            int targetEntity = castSpellComponent.getTargetEntity();
            boolean canBeCasted = true;
            if(targetEntity != -1){
                SpellTargetRulesComponent spellTargetRulesComponent = entityWorld.getComponent(castSpellComponent.getSpellEntity(), SpellTargetRulesComponent.class);
                if(spellTargetRulesComponent != null){
                    canBeCasted = TargetUtil.isValidTarget(entityWorld, casterEntity, targetEntity, spellTargetRulesComponent.getTargetRulesEntity());
                }
            }
            if(canBeCasted){
                LinkedList<Object> componentsToAdd = new LinkedList<Object>();
                componentsToAdd.add(castSpellComponent);
                if((autoAttackComponent != null) && (castSpellComponent.getSpellEntity() == autoAttackComponent.getAutoAttackEntity())){
                    componentsToAdd.add(entityWorld.getComponent(casterEntity, AggroTargetComponent.class));
                }
                addSpellCastComponents(entityWorld, casterEntity, castSpellComponent.getSpellEntity(), targetEntity, componentsToAdd.toArray(new Object[0]));
            }
        }
    }
    
    public static void addSpellCastComponents(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity, Object[] componentsToAdd){
        boolean castInstant = true;
        RangeComponent rangeComponent = entityWorld.getComponent(spellEntity, RangeComponent.class);
        if(rangeComponent != null){
            float range = rangeComponent.getDistance();
            Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
            Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            float distance = targetPosition.distance(casterPosition);
            if(distance > range){
                if(walk(entityWorld, casterEntity, targetEntity, range)){
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new TriggerTemporaryComponent());
                    effectTrigger.setComponent(new TargetReachedTriggerComponent());
                    effectTrigger.setComponent(new SourceTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    effect.setComponent(new AddComponentsComponent(componentsToAdd));
                    effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                    effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                    effectTrigger.setComponent(new TriggerOnceComponent());
                }
                castInstant = false;
            }
        }
        if(castInstant){
            boolean isAllowed = true;
            if(entityWorld.hasComponent(spellEntity, CastCancelActionComponent.class)){
                isAllowed = UnitUtil.tryCancelAction(entityWorld, casterEntity);
            }
            if(isAllowed){
                for(Object componentToAdd : componentsToAdd){
                    entityWorld.setComponent(casterEntity, componentToAdd);
                }
            }
        }
    }
    
    public static boolean walk(EntityWorld entityWorld, int selectedUnit, int targetEntity, float sufficientDistance){
        if(MovementSystem.canMove(entityWorld, selectedUnit)){
            boolean isAllowed = true;
            MovementComponent movementComponent = entityWorld.getComponent(selectedUnit, MovementComponent.class);
            if(movementComponent != null){
                isAllowed = entityWorld.hasComponent(movementComponent.getMovementEntity(), MovementIsCancelableComponent.class);
            }
            if(isAllowed){
                if(UnitUtil.tryCancelAction(entityWorld, selectedUnit)){
                    EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
                    movement.setComponent(new MovementTargetComponent(targetEntity));
                    if(sufficientDistance != -1){
                        movement.setComponent(new MovementTargetSufficientDistanceComponent(sufficientDistance));
                    }
                    movement.setComponent(new WalkMovementComponent());
                    movement.setComponent(new MovementIsCancelableComponent());
                    WalkAnimationComponent walkAnimationComponent = entityWorld.getComponent(selectedUnit, WalkAnimationComponent.class);
                    if(walkAnimationComponent != null){
                        movement.setComponent(new MovementAnimationComponent(walkAnimationComponent.getAnimationEntity()));
                    }
                    entityWorld.setComponent(selectedUnit, new MovementComponent(movement.getId()));
                    return true;
                }
            }
        }
        return false;
    }
}
