package amara.applications.ingame.entitysystem.systems.ai;

import java.util.HashMap;
import java.util.LinkedList;

import amara.applications.ingame.entitysystem.ai.actions.*;
import amara.applications.ingame.entitysystem.ai.bots.Bot;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.systems.commands.ExecutePlayerCommandsSystem;
import amara.applications.ingame.entitysystem.systems.shop.ShopUtil;
import amara.applications.ingame.entitysystem.systems.spells.casting.CastSpellQueueSystem;
import amara.applications.ingame.shared.maps.Map;
import amara.ingame.ai.Action;
import amara.libraries.entitysystem.*;

public class ExecuteAIActionsSystem implements EntitySystem {

    public ExecuteAIActionsSystem(EntityBotsMap entityBotsMap, CastSpellQueueSystem castSpellQueueSystem, Map map) {
        this.entityBotsMap = entityBotsMap;
        this.castSpellQueueSystem = castSpellQueueSystem;
        this.map = map;
    }
    private EntityBotsMap entityBotsMap;
    private CastSpellQueueSystem castSpellQueueSystem;
    private Map map;
    private HashMap<Bot, Float> timesSinceLastDecision = new HashMap<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int playerEntity : entityWorld.getEntitiesWithAny(IsBotComponent.class)) {
            Bot bot = entityBotsMap.getBot(playerEntity);
            if (checkBotDecisionInterval(bot, deltaSeconds)) {
                int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
                executeBotActions(entityWorld, bot, characterEntity);
            }
        }
    }

    private boolean checkBotDecisionInterval(Bot bot, float deltaSeconds) {
        Float timeSinceLastDecision = timesSinceLastDecision.get(bot);
        if (timeSinceLastDecision == null) {
            timeSinceLastDecision = 0f;
        }
        timeSinceLastDecision += deltaSeconds;
        timesSinceLastDecision.put(bot, timeSinceLastDecision);
        return (timeSinceLastDecision > bot.getDecisionInterval());
    }

    private void executeBotActions(EntityWorld entityWorld, Bot bot, int characterEntity) {
        LinkedList<Action> actions = bot.getDecisionMaking().evaluateActions(entityWorld, characterEntity);
        for (Action action : actions) {
            if (action instanceof WalkAction) {
                WalkAction walkAction = (WalkAction) action;
                int targetEntity = entityWorld.createEntity();
                entityWorld.setComponent(targetEntity, new TemporaryComponent());
                entityWorld.setComponent(targetEntity, new PositionComponent(walkAction.getPosition()));
                boolean wasSuccessful = ExecutePlayerCommandsSystem.tryWalk(entityWorld, characterEntity, targetEntity, -1);
                if (!wasSuccessful) {
                    entityWorld.removeEntity(targetEntity);
                }
            } else if (action instanceof AutoAttackAction) {
                AutoAttackAction autoAttackAction = (AutoAttackAction) action;
                ExecutePlayerCommandsSystem.tryAutoAttack(entityWorld, characterEntity, autoAttackAction.getTargetEntity());
            } else if (action instanceof CastSpellAction) {
                CastSpellAction castSpellAction = (CastSpellAction) action;
                castSpellQueueSystem.enqueueSpellCast(characterEntity, castSpellAction.getSpellEntity(), castSpellAction.getTargetEntity());
            } else if (action instanceof BuyItemAction) {
                BuyItemAction buyItemAction = (BuyItemAction) action;
                ShopUtil.buy(entityWorld, characterEntity, buyItemAction.getItemId(), map);
            }
        }
    }

    public interface EntityBotsMap {
        Bot getBot(int playerEntity);
    }
}
