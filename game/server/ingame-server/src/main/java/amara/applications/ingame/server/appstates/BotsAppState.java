package amara.applications.ingame.server.appstates;

import java.util.HashMap;

import amara.applications.ingame.entitysystem.ai.bots.Bot;
import amara.applications.ingame.entitysystem.ai.bots.EasyBot;
import amara.applications.ingame.entitysystem.ai.bots.EasyVegasBot;
import amara.applications.master.network.messages.objects.BotType;

public class BotsAppState extends ServerBaseAppState {

    private HashMap<Integer, Bot> entityBots = new HashMap<>();

    public void createAndRegisterBot(int playerEntity, BotType botType) {
        Bot bot = createBot(botType);
        entityBots.put(playerEntity, bot);
    }

    private Bot createBot(BotType botType) {
        switch (botType){
            case EASY:
                return new EasyBot();
            case EASY_VEGAS:
                return new EasyVegasBot();
        }
        return null;
    }

    public Bot getBot(int playerEntity) {
        return entityBots.get(playerEntity);
    }
}
