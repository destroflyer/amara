/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import java.util.HashMap;
import amara.applications.ingame.entitysystem.ai.bots.*;
import amara.applications.master.network.messages.objects.BotType;

/**
 *
 * @author Carl
 */
public class BotsAppState extends ServerBaseAppState{

    public BotsAppState(){
        
    }
    private HashMap<Integer, Bot> entityBots = new HashMap<>();
    
    public void createAndRegisterBot(int playerEntity, BotType botType){
        Bot bot = createBot(botType);
        entityBots.put(playerEntity, bot);
    }
    
    private Bot createBot(BotType botType){
        switch (botType){
            case EASY:
                return new EasyBot();
        }
        return null;
    }
    
    public Bot getBot(int playerEntity){
        return entityBots.get(playerEntity);
    }
}
