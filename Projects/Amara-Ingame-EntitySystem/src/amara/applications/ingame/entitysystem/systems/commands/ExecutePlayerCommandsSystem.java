/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.commands;

import java.util.Iterator;
import com.jme3.math.Vector2f;
import amara.core.Queue;
import amara.applications.ingame.entitysystem.components.effects.general.*;
import amara.applications.ingame.entitysystem.components.input.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.targets.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.applications.ingame.entitysystem.systems.aggro.CheckAggroTargetAttackibilitySystem;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellSystem;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.applications.ingame.network.messages.objects.commands.*;
import amara.applications.ingame.network.messages.objects.commands.casting.*;
import amara.libraries.entitysystem.*;

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
            boolean isUnitAlive = entityWorld.hasComponent(selectedUnit, IsAliveComponent.class);
            if(command instanceof BuyItemCommand){
                BuyItemCommand buyItemCommand = (BuyItemCommand) command;
                if((!isUnitAlive) || ShopUtil.isInShopRange(entityWorld, selectedUnit)){
                    ShopUtil.buy(entityWorld, selectedUnit, buyItemCommand.getItemID());
                }
            }
            else if(command instanceof SellItemCommand){
                SellItemCommand sellItemCommand = (SellItemCommand) command;
                if((!isUnitAlive) || ShopUtil.isInShopRange(entityWorld, selectedUnit)){
                    ShopUtil.sell(entityWorld, selectedUnit, sellItemCommand.getInventoryIndex());
                }
            }
            else if(command instanceof LearnSpellCommand){
                LearnSpellCommand learnSpellCommand = (LearnSpellCommand) command;
                SpellUtil.learnSpell(entityWorld, selectedUnit, learnSpellCommand.getSpellIndex());
            }
            else if(command instanceof UpgradeSpellCommand){
                UpgradeSpellCommand upgradeSpellCommand = (UpgradeSpellCommand) command;
                SpellUtil.upgradeSpell(entityWorld, selectedUnit, upgradeSpellCommand.getSpellIndex(), upgradeSpellCommand.getUpgradeIndex());
            }
            else if(isUnitAlive){
                if(command instanceof MoveCommand){
                    MoveCommand moveCommand = (MoveCommand) command;
                    int targetPositionEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetPositionEntity, new PositionComponent(moveCommand.getPosition()));
                    boolean wasSuccessfull = tryWalk(entityWorld, selectedUnit, targetPositionEntity, -1);
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
                        AggroTargetComponent aggroTargetComponent = entityWorld.getComponent(selectedUnit, AggroTargetComponent.class);
                        if((aggroTargetComponent == null) || (autoAttackCommand.getTargetEntity() != aggroTargetComponent.getTargetEntity())){
                            if(CheckAggroTargetAttackibilitySystem.isAttackable(entityWorld, selectedUnit, autoAttackCommand.getTargetEntity())){
                                if(UnitUtil.tryCancelAction(entityWorld, selectedUnit)){
                                    entityWorld.setComponent(selectedUnit, new AggroTargetComponent(autoAttackCommand.getTargetEntity()));
                                }
                            }
                        }
                    }
                }
                else if(command instanceof CastSelfcastSpellCommand){
                    CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, selectedUnit, castSelfcastSpellCommand.getSpellIndex());
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, -1));
                }
                else if(command instanceof CastSingleTargetSpellCommand){
                    CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, selectedUnit, castSingleTargetSpellCommand.getSpellIndex());
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, castSingleTargetSpellCommand.getTargetEntityID()));
                }
                else if(command instanceof CastLinearSkillshotSpellCommand){
                    CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, selectedUnit, castLinearSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new DirectionComponent(castLinearSkillshotSpellCommand.getDirection().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, targetEntity));
                }
                else if(command instanceof CastPositionalSkillshotSpellCommand){
                    CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, selectedUnit, castPositionalSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new PositionComponent(castPositionalSkillshotSpellCommand.getPosition().clone()));
                    castSpell(entityWorld, selectedUnit, new CastSpellComponent(spellEntity, targetEntity));
                }
                else if(command instanceof ShowReactionCommand){
                    ShowReactionCommand showReactionCommand = (ShowReactionCommand) command;
                    entityWorld.setComponent(selectedUnit, new ReactionComponent(showReactionCommand.getReaction(), 2));
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
    
    public static int getSpellEntity(EntityWorld entityWorld, int casterEntity, SpellIndex spellIndex){
        switch(spellIndex.getSpellSet()){
            case SPELLS:
                SpellsComponent spellsComponent = entityWorld.getComponent(casterEntity, SpellsComponent.class);
                if(spellsComponent != null){
                    int[] spells = spellsComponent.getSpellsEntities();
                    if(spellIndex.getIndex() < spells.length){
                        return spells[spellIndex.getIndex()];
                    }
                }
                break;
            
            case ITEMS:
                InventoryComponent inventoryComponent = entityWorld.getComponent(casterEntity, InventoryComponent.class);
                if(inventoryComponent != null){
                    int[] items = inventoryComponent.getItemEntities();
                    if(spellIndex.getIndex() < items.length){
                        ItemActiveComponent itemActiveComponent = entityWorld.getComponent(items[spellIndex.getIndex()], ItemActiveComponent.class);
                        if(itemActiveComponent != null){
                            return itemActiveComponent.getSpellEntity();
                        }
                    }
                }
                break;
        }
        return -1;
    }
    
    public static void castSpell(EntityWorld entityWorld, int casterEntity, CastSpellComponent castSpellComponent){
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
                addSpellCastComponents(entityWorld, casterEntity, castSpellComponent.getSpellEntity(), targetEntity, castSpellComponent);
            }
        }
    }
    
    private static void addSpellCastComponents(EntityWorld entityWorld, int casterEntity, int spellEntity, int targetEntity, CastSpellComponent castSpellComponent){
        AutoAttackComponent autoAttackComponent = entityWorld.getComponent(casterEntity, AutoAttackComponent.class);
        boolean isAutoAttack = ((autoAttackComponent != null) && (spellEntity == autoAttackComponent.getAutoAttackEntity()));
        boolean castInstant = true;
        if(entityWorld.hasComponent(spellEntity, RangeComponent.class)){
            float minimumCastRange = CastSpellSystem.getMinimumCastRange(entityWorld, casterEntity, spellEntity, targetEntity);
            Vector2f casterPosition = entityWorld.getComponent(casterEntity, PositionComponent.class).getPosition();
            Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            float distanceSquared = targetPosition.distanceSquared(casterPosition);
            if(distanceSquared > (minimumCastRange * minimumCastRange)){
                if(tryWalk(entityWorld, casterEntity, targetEntity, minimumCastRange)){
                    if(isAutoAttack){
                        entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                        entityWorld.setComponent(casterEntity, new IsWalkingToAggroTargetComponent());
                        EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                        effectTrigger.setComponent(new TriggerTemporaryComponent());
                        effectTrigger.setComponent(new TargetReachedTriggerComponent());
                        effectTrigger.setComponent(new SourceTargetComponent());
                        EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                        effect.setComponent(new RemoveComponentsComponent(IsWalkingToAggroTargetComponent.class));
                        effectTrigger.setComponent(new TriggeredEffectComponent(effect.getId()));
                        effectTrigger.setComponent(new TriggerSourceComponent(casterEntity));
                        effectTrigger.setComponent(new TriggerOnCancelComponent());
                    }
                    //Cast Spell
                    EntityWrapper effectTrigger = entityWorld.getWrapped(entityWorld.createEntity());
                    effectTrigger.setComponent(new TriggerTemporaryComponent());
                    effectTrigger.setComponent(new TargetReachedTriggerComponent());
                    effectTrigger.setComponent(new SourceTargetComponent());
                    EntityWrapper effect = entityWorld.getWrapped(entityWorld.createEntity());
                    effect.setComponent(new AddComponentsComponent(castSpellComponent));
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
                if(isAutoAttack){
                    entityWorld.setComponent(casterEntity, new AggroTargetComponent(targetEntity));
                }
                entityWorld.setComponent(casterEntity, castSpellComponent);
            }
        }
    }
    
    public static boolean tryWalk(EntityWorld entityWorld, int selectedUnit, int targetEntity, float sufficientDistance){
        if(MovementSystem.canMove(entityWorld, selectedUnit)){
            boolean isAllowed = true;
            MovementComponent movementComponent = entityWorld.getComponent(selectedUnit, MovementComponent.class);
            if(movementComponent != null){
                isAllowed = entityWorld.hasComponent(movementComponent.getMovementEntity(), MovementIsCancelableComponent.class);
            }
            if(isAllowed){
                return walk(entityWorld, selectedUnit, targetEntity, sufficientDistance);
            }
        }
        return false;
    }
    
    public static boolean walk(EntityWorld entityWorld, int selectedUnit, int targetEntity, float sufficientDistance){
        if(UnitUtil.tryCancelAction(entityWorld, selectedUnit)){
            EntityWrapper movement = entityWorld.getWrapped(entityWorld.createEntity());
            movement.setComponent(new MovementTargetComponent(targetEntity));
            if(sufficientDistance != -1){
                movement.setComponent(new MovementTargetSufficientDistanceComponent(sufficientDistance));
            }
            movement.setComponent(new MovementPathfindingComponent());
            if(entityWorld.hasComponent(selectedUnit, LocalAvoidanceWalkComponent.class)){
                movement.setComponent(new MovementLocalAvoidanceComponent());
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
        return false;
    }
}
