package amara.applications.ingame.entitysystem.systems.commands;

import java.util.Iterator;

import amara.applications.ingame.shared.maps.Map;
import amara.core.Queue;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.aggro.AggroUtil;
import amara.applications.ingame.entitysystem.systems.items.ItemUtil;
import amara.applications.ingame.entitysystem.systems.movement.MovementSystem;
import amara.applications.ingame.entitysystem.systems.movement.WalkUtil;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.applications.ingame.entitysystem.systems.spells.SpellUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.applications.ingame.network.messages.objects.commands.*;
import amara.applications.ingame.network.messages.objects.commands.casting.*;
import amara.libraries.entitysystem.*;
import com.jme3.math.Vector2f;

public class ExecutePlayerCommandsSystem implements EntitySystem {

    public ExecutePlayerCommandsSystem(Queue<PlayerCommand> playerCommandsQueue, CastSpellQueueSystem castSpellQueueSystem, Map map) {
        this.playerCommandsQueue = playerCommandsQueue;
        this.castSpellQueueSystem = castSpellQueueSystem;
        this.map = map;
    }
    private Queue<PlayerCommand> playerCommandsQueue;
    private CastSpellQueueSystem castSpellQueueSystem;
    private Map map;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        Iterator<PlayerCommand> playerCommandsIterator = playerCommandsQueue.getIterator();
        while (playerCommandsIterator.hasNext()) {
            PlayerCommand playerCommand = playerCommandsIterator.next();
            Command command = playerCommand.getCommand();
            int playerEntity = getPlayerEntity(entityWorld, playerCommand.getClientID());
            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
            boolean isUnitAlive = entityWorld.hasComponent(characterEntity, IsAliveComponent.class);
            if (command instanceof BuyItemCommand) {
                BuyItemCommand buyItemCommand = (BuyItemCommand) command;
                if ((!isUnitAlive) || ShopUtil.isInShopRange(entityWorld, characterEntity)) {
                    ShopUtil.buy(entityWorld, characterEntity, buyItemCommand.getItemID(), map);
                }
            } else if (command instanceof SellItemCommand) {
                SellItemCommand sellItemCommand = (SellItemCommand) command;
                if ((!isUnitAlive) || ShopUtil.isInShopRange(entityWorld, characterEntity)){
                    ShopUtil.sell(entityWorld, characterEntity, sellItemCommand.getItemIndex());
                }
            } else if (command instanceof LearnSpellCommand) {
                LearnSpellCommand learnSpellCommand = (LearnSpellCommand) command;
                SpellUtil.learnSpell(entityWorld, characterEntity, learnSpellCommand.getSpellIndex());
            } else if (command instanceof UpgradeSpellCommand) {
                UpgradeSpellCommand upgradeSpellCommand = (UpgradeSpellCommand) command;
                SpellUtil.upgradeSpell(entityWorld, characterEntity, upgradeSpellCommand.getSpellIndex(), upgradeSpellCommand.getUpgradeIndex());
            } else if (command instanceof SwapItemsCommand) {
                SwapItemsCommand swapItemsCommand = (SwapItemsCommand) command;
                ItemUtil.swapItems(entityWorld, characterEntity, swapItemsCommand.getItemIndex1(), swapItemsCommand.getItemIndex2());
            } else if (isUnitAlive) {
                if (command instanceof WalkToTargetCommand) {
                    WalkToTargetCommand walkToTargetCommand = (WalkToTargetCommand) command;
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new TemporaryComponent());
                    entityWorld.setComponent(targetEntity, new PositionComponent(walkToTargetCommand.getPosition()));
                    boolean wasSuccessful = tryWalk(entityWorld, characterEntity, targetEntity, -1);
                    if (!wasSuccessful) {
                        entityWorld.removeEntity(targetEntity);
                    }
                } else if (command instanceof WalkInDirectionCommand) {
                    WalkInDirectionCommand walkInDirectionCommand = (WalkInDirectionCommand) command;
                    Vector2f direction = walkInDirectionCommand.getDirection();
                    // If a client thinks it can cheat
                    if (direction.length() > 1) {
                        direction = direction.normalize();
                    }
                    entityWorld.setComponent(characterEntity, new InnateWalkDirectionComponent(direction));
                    MovementComponent movementComponent = entityWorld.getComponent(characterEntity, MovementComponent.class);
                    if (movementComponent != null) {
                        int movementEntity = movementComponent.getMovementEntity();
                        if (entityWorld.hasComponent(movementEntity, WalkMovementComponent.class)) {
                            entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                        }
                    }
                } else if (command instanceof StopWalkInDirectionCommand) {
                    entityWorld.removeComponent(characterEntity, InnateWalkDirectionComponent.class);
                    UnitUtil.tryStopMovement(entityWorld, characterEntity);
                } else if (command instanceof StopCommand) {
                    UnitUtil.tryCancelAction(entityWorld, characterEntity);
                } else if (command instanceof AutoAttackCommand) {
                    AutoAttackCommand autoAttackCommand = (AutoAttackCommand) command;
                    tryAutoAttack(entityWorld, characterEntity, autoAttackCommand.getTargetEntity());
                } else if (command instanceof CastSelfcastSpellCommand) {
                    CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, characterEntity, castSelfcastSpellCommand.getSpellIndex());
                    castSpellQueueSystem.enqueueSpellCast(characterEntity, spellEntity, -1);
                } else if (command instanceof CastSingleTargetSpellCommand) {
                    CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, characterEntity, castSingleTargetSpellCommand.getSpellIndex());
                    castSpellQueueSystem.enqueueSpellCast(characterEntity, spellEntity, castSingleTargetSpellCommand.getTargetEntityID());
                } else if (command instanceof CastLinearSkillshotSpellCommand) {
                    CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, characterEntity, castLinearSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new TemporaryComponent());
                    entityWorld.setComponent(targetEntity, new DirectionComponent(castLinearSkillshotSpellCommand.getDirection().clone()));
                    castSpellQueueSystem.enqueueSpellCast(characterEntity, spellEntity, targetEntity);
                } else if (command instanceof CastPositionalSkillshotSpellCommand) {
                    CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                    int spellEntity = getSpellEntity(entityWorld, characterEntity, castPositionalSkillshotSpellCommand.getSpellIndex());
                    int targetEntity = entityWorld.createEntity();
                    entityWorld.setComponent(targetEntity, new TemporaryComponent());
                    entityWorld.setComponent(targetEntity, new PositionComponent(castPositionalSkillshotSpellCommand.getPosition().clone()));
                    castSpellQueueSystem.enqueueSpellCast(characterEntity, spellEntity, targetEntity);
                } else if (command instanceof ShowReactionCommand) {
                    ShowReactionCommand showReactionCommand = (ShowReactionCommand) command;
                    entityWorld.setComponent(characterEntity, new ReactionComponent(showReactionCommand.getReaction(), 2));
                }
            }
        }
        playerCommandsQueue.clear();
    }

    private int getPlayerEntity(EntityWorld entityWorld, int clientId) {
        for (int entity : entityWorld.getEntitiesWithAny(ClientComponent.class)) {
            if (entityWorld.getComponent(entity, ClientComponent.class).getClientId() == clientId) {
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
            
            case MAP:
                MapSpellsComponent mapSpellsComponent = entityWorld.getComponent(casterEntity, MapSpellsComponent.class);
                if(mapSpellsComponent != null){
                    int[] mapSpells = mapSpellsComponent.getSpellsEntities();
                    if(spellIndex.getIndex() < mapSpells.length){
                        return mapSpells[spellIndex.getIndex()];
                    }
                }
                break;
        }
        return -1;
    }

    public static boolean tryAutoAttack(EntityWorld entityWorld, int unitEntity, int targetEntity) {
        AggroTargetComponent aggroTargetComponent = entityWorld.getComponent(unitEntity, AggroTargetComponent.class);
        if ((aggroTargetComponent == null) || (targetEntity != aggroTargetComponent.getTargetEntity())) {
            return AggroUtil.tryCancelActionAndSetAggro(entityWorld, unitEntity, targetEntity);
        }
        return false;
    }
    
    public static boolean tryWalk(EntityWorld entityWorld, int unitEntity, int targetEntity, float sufficientDistance){
        if(MovementSystem.canMove(entityWorld, unitEntity)){
            return walk(entityWorld, unitEntity, targetEntity, sufficientDistance);
        }
        return false;
    }

    public static boolean walk(EntityWorld entityWorld, int unitEntity, int targetEntity, float sufficientDistance) {
        if (UnitUtil.tryCancelAction(entityWorld, unitEntity)) {
            int movementEntity = WalkUtil.createWalkMovementEntity(entityWorld, unitEntity);
            entityWorld.setComponent(movementEntity, new MovementTargetComponent(targetEntity));
            if (sufficientDistance != -1) {
                entityWorld.setComponent(movementEntity, new MovementTargetSufficientDistanceComponent(sufficientDistance));
            }
            entityWorld.setComponent(movementEntity, new MovementPathfindingComponent());
            if (entityWorld.hasComponent(unitEntity, LocalAvoidanceWalkComponent.class)) {
                entityWorld.setComponent(movementEntity, new MovementLocalAvoidanceComponent());
            }
            entityWorld.setComponent(unitEntity, new MovementComponent(movementEntity));
            return true;
        }
        return false;
    }
}
