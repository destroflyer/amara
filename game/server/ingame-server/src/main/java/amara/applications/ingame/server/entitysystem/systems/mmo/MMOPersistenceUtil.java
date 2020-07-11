package amara.applications.ingame.server.entitysystem.systems.mmo;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.units.GoldComponent;
import amara.applications.ingame.server.entitysystem.systems.mmo.state.MMOItemState;
import amara.applications.ingame.server.entitysystem.systems.mmo.state.MMOPlayerState;
import amara.applications.ingame.server.entitysystem.systems.mmo.state.MMOVectorState2f;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;
import com.jme3.math.Vector2f;

public class MMOPersistenceUtil {

    public static MMOPlayerState getPlayerState(EntityWorld entityWorld, int playerEntity) {
        MMOPlayerState mmoPlayerState = new MMOPlayerState();
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        PositionComponent positionComponent = entityWorld.getComponent(characterEntity, PositionComponent.class);
        if (positionComponent != null) {
            mmoPlayerState.setPosition(getVector2f(positionComponent.getPosition()));
        }
        DirectionComponent directionComponent = entityWorld.getComponent(characterEntity, DirectionComponent.class);
        if (directionComponent != null) {
            mmoPlayerState.setDirection(getVector2f(directionComponent.getVector()));
        }
        InventoryComponent inventoryComponent = entityWorld.getComponent(characterEntity, InventoryComponent.class);
        if (inventoryComponent != null) {
            MMOItemState[] inventory = new MMOItemState[inventoryComponent.getItemEntities().length];
            for (int i = 0; i < inventory.length; i++) {
                inventory[i] = getItemState(entityWorld, inventoryComponent.getItemEntities()[i]);
            }
            mmoPlayerState.setInventory(inventory);
        }
        BagComponent bagComponent = entityWorld.getComponent(characterEntity, BagComponent.class);
        if (bagComponent != null) {
            MMOItemState[] bag = new MMOItemState[bagComponent.getItemEntities().length];
            for (int i = 0; i < bag.length; i++) {
                bag[i] = getItemState(entityWorld, bagComponent.getItemEntities()[i]);
            }
            mmoPlayerState.setBag(bag);
        }
        GoldComponent goldComponent = entityWorld.getComponent(characterEntity, GoldComponent.class);
        if (goldComponent != null) {
            mmoPlayerState.setGold(goldComponent.getGold());
        }
        return mmoPlayerState;
    }

    private static MMOVectorState2f getVector2f(Vector2f vector2f) {
        return new MMOVectorState2f(vector2f.getX(), vector2f.getY());
    }

    private static MMOItemState getItemState(EntityWorld entityWorld, int itemEntity) {
        if (itemEntity != -1) {
            MMOItemState mmoItemState = new MMOItemState();
            ItemIDComponent itemIDComponent = entityWorld.getComponent(itemEntity, ItemIDComponent.class);
            if (itemIDComponent != null) {
                mmoItemState.setId(itemIDComponent.getID());
            } else {
                NameComponent nameComponent = entityWorld.getComponent(itemEntity, NameComponent.class);
                if (nameComponent != null) {
                    mmoItemState.setName(nameComponent.getName());
                }
                ItemVisualisationComponent itemVisualisationComponent = entityWorld.getComponent(itemEntity, ItemVisualisationComponent.class);
                if (itemVisualisationComponent != null) {
                    mmoItemState.setVisualisation(itemVisualisationComponent.getName());
                }
                BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = entityWorld.getComponent(itemEntity, BonusFlatAbilityPowerComponent.class);
                if (bonusFlatAbilityPowerComponent != null) {
                    mmoItemState.setFlatAbilityPower(bonusFlatAbilityPowerComponent.getValue());
                }
                BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = entityWorld.getComponent(itemEntity, BonusFlatAttackDamageComponent.class);
                if (bonusFlatAttackDamageComponent != null) {
                    mmoItemState.setFlatAttackDamage(bonusFlatAttackDamageComponent.getValue());
                }
                BonusFlatArmorComponent bonusFlatArmorComponent = entityWorld.getComponent(itemEntity, BonusFlatArmorComponent.class);
                if (bonusFlatArmorComponent != null) {
                    mmoItemState.setFlatArmor(bonusFlatArmorComponent.getValue());
                }
                BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = entityWorld.getComponent(itemEntity, BonusFlatMagicResistanceComponent.class);
                if (bonusFlatMagicResistanceComponent != null) {
                    mmoItemState.setFlatMagicResistance(bonusFlatMagicResistanceComponent.getValue());
                }
                BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = entityWorld.getComponent(itemEntity, BonusFlatMaximumHealthComponent.class);
                if (bonusFlatMaximumHealthComponent != null) {
                    mmoItemState.setFlatMaximumHealth(bonusFlatMaximumHealthComponent.getValue());
                }
                BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageAttackSpeedComponent.class);
                if (bonusPercentageAttackSpeedComponent != null) {
                    mmoItemState.setPercentageAttackSpeed(bonusPercentageAttackSpeedComponent.getValue());
                }
                BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageCooldownSpeedComponent.class);
                if (bonusPercentageCooldownSpeedComponent != null) {
                    mmoItemState.setPercentageCooldownSpeed(bonusPercentageCooldownSpeedComponent.getValue());
                }
                BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = entityWorld.getComponent(itemEntity, BonusPercentageCriticalChanceComponent.class);
                if (bonusPercentageCriticalChanceComponent != null) {
                    mmoItemState.setPercentageCriticalChance(bonusPercentageCriticalChanceComponent.getValue());
                }
                BonusPercentageLifestealComponent bonusPercentageLifestealComponent = entityWorld.getComponent(itemEntity, BonusPercentageLifestealComponent.class);
                if (bonusPercentageLifestealComponent != null) {
                    mmoItemState.setPercentageLifesteal(bonusPercentageLifestealComponent.getValue());
                }
                BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageWalkSpeedComponent.class);
                if (bonusPercentageWalkSpeedComponent != null) {
                    mmoItemState.setPercentageWalkSpeed(bonusPercentageWalkSpeedComponent.getValue());
                }
                IsSellableComponent isSellableComponent = entityWorld.getComponent(itemEntity, IsSellableComponent.class);
                if (isSellableComponent != null) {
                    mmoItemState.setSellableGold(isSellableComponent.getGold());
                }
            }
            return mmoItemState;
        }
        return null;
    }

    public static void loadPlayerState(EntityWorld entityWorld, int playerEntity, MMOPlayerState mmoPlayerState) {
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        MMOVectorState2f position = mmoPlayerState.getPosition();
        if (position != null) {
            entityWorld.setComponent(characterEntity, new PositionComponent(getVector2f(position)));
        }
        MMOVectorState2f direction = mmoPlayerState.getDirection();
        if (direction != null) {
            entityWorld.setComponent(characterEntity, new DirectionComponent(getVector2f(direction)));
        }
        MMOItemState[] inventory = mmoPlayerState.getInventory();
        if (inventory != null) {
            int[] itemEntities = loadItemStates(entityWorld, inventory);
            entityWorld.setComponent(characterEntity, new InventoryComponent(itemEntities));
        }
        MMOItemState[] bag = mmoPlayerState.getBag();
        if (bag != null) {
            int[] itemEntities = loadItemStates(entityWorld, bag);
            entityWorld.setComponent(characterEntity, new BagComponent(itemEntities));
        }
        Float gold = mmoPlayerState.getGold();
        if (gold != null) {
            entityWorld.setComponent(characterEntity, new GoldComponent(gold));
        }
    }

    private static Vector2f getVector2f(MMOVectorState2f mmoVectorState2f) {
        return new Vector2f(mmoVectorState2f.getX(), mmoVectorState2f.getY());
    }

    private static int[] loadItemStates(EntityWorld entityWorld, MMOItemState[] mmoItemStates) {
        int[] itemEntities = new int[mmoItemStates.length];
        for (int i = 0; i < mmoItemStates.length; i++) {
            itemEntities[i] = ((mmoItemStates[i] != null) ? loadItemState(entityWorld, mmoItemStates[i]) : -1);
        }
        return itemEntities;
    }

    private static int loadItemState(EntityWorld entityWorld, MMOItemState mmoItemState) {
        int itemEntity = entityWorld.createEntity();
        String id = mmoItemState.getId();
        if (id != null) {
            EntityTemplate.loadTemplate(entityWorld, itemEntity, "items/" + id);
        } else {
            String name = mmoItemState.getName();
            if (name != null) {
                entityWorld.setComponent(itemEntity, new NameComponent(name));
            }
            String visualisation = mmoItemState.getVisualisation();
            if (visualisation != null) {
                entityWorld.setComponent(itemEntity, new ItemVisualisationComponent(visualisation));
            }
            Float flatAbilityPower = mmoItemState.getFlatAbilityPower();
            if (flatAbilityPower != null) {
                entityWorld.setComponent(itemEntity, new BonusFlatAbilityPowerComponent(flatAbilityPower));
            }
            Float flatAttackDamage = mmoItemState.getFlatAttackDamage();
            if (flatAttackDamage != null) {
                entityWorld.setComponent(itemEntity, new BonusFlatAttackDamageComponent(flatAttackDamage));
            }
            Float flatArmor = mmoItemState.getFlatArmor();
            if (flatArmor != null) {
                entityWorld.setComponent(itemEntity, new BonusFlatArmorComponent(flatArmor));
            }
            Float flatMagicResistance = mmoItemState.getFlatMagicResistance();
            if (flatMagicResistance != null) {
                entityWorld.setComponent(itemEntity, new BonusFlatMagicResistanceComponent(flatMagicResistance));
            }
            Float flatMaximumHealth = mmoItemState.getFlatMaximumHealth();
            if (flatMaximumHealth != null) {
                entityWorld.setComponent(itemEntity, new BonusFlatMaximumHealthComponent(flatMaximumHealth));
            }
            Float percentageAttackSpeed = mmoItemState.getPercentageAttackSpeed();
            if (percentageAttackSpeed != null) {
                entityWorld.setComponent(itemEntity, new BonusPercentageAttackSpeedComponent(percentageAttackSpeed));
            }
            Float percentageCooldownSpeed = mmoItemState.getPercentageCooldownSpeed();
            if (percentageCooldownSpeed != null) {
                entityWorld.setComponent(itemEntity, new BonusPercentageCooldownSpeedComponent(percentageCooldownSpeed));
            }
            Float percentageCriticalChance = mmoItemState.getPercentageCriticalChance();
            if (percentageCriticalChance != null) {
                entityWorld.setComponent(itemEntity, new BonusPercentageCriticalChanceComponent(percentageCriticalChance));
            }
            Float percentageLifesteal = mmoItemState.getPercentageLifesteal();
            if (percentageLifesteal != null) {
                entityWorld.setComponent(itemEntity, new BonusPercentageLifestealComponent(percentageLifesteal));
            }
            Float percentageWalkSpeed = mmoItemState.getPercentageWalkSpeed();
            if (percentageWalkSpeed != null) {
                entityWorld.setComponent(itemEntity, new BonusPercentageWalkSpeedComponent(percentageWalkSpeed));
            }
            Float sellableGold = mmoItemState.getSellableGold();
            if (sellableGold != null) {
                entityWorld.setComponent(itemEntity, new IsSellableComponent(sellableGold));
            }
        }
        return itemEntity;
    }
}
