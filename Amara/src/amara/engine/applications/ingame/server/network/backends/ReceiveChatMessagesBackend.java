/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.game.games.*;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class ReceiveChatMessagesBackend implements MessageBackend{

    public ReceiveChatMessagesBackend(Game game, EntityWorld entityWorld){
        this.game = game;
        this.entityWorld = entityWorld;
    }
    private Game game;
    private EntityWorld entityWorld;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_SendChatMessage){
            Message_SendChatMessage message = (Message_SendChatMessage) receivedMessage;
            GamePlayer gamePlayer = game.getPlayer(messageResponse.getClientID());
            if(gamePlayer != null){
                messageResponse.addBroadcastMessage(new Message_ChatMessage(gamePlayer.getLobbyPlayer().getID(), message.getText()));
                int selectedUnit = entityWorld.getComponent(gamePlayer.getEntityID(), SelectedUnitComponent.class).getEntity();
                if(message.getText().equals("such chat")){
                    messageResponse.addAnswerMessage(new Message_ChatMessage("very responsive, wow"));
                }
                else if(message.getText().startsWith("/speed ")){
                    try{
                        float speed = Float.parseFloat(message.getText().substring(7));
                        entityWorld.setComponent(Game.ENTITY, new GameSpeedComponent(speed));
                    }catch(NumberFormatException ex){
                    }
                }
                else if(message.getText().startsWith("/cinematic ")){
                    try{
                        int cinematicIndex = Integer.parseInt(message.getText().substring(11));
                        switch(cinematicIndex){
                            case 0:
                                entityWorld.setComponent(Game.ENTITY, new CinematicComponent(TestMap_TestCinematic.class.getName()));
                                break;
                            
                            case 1:
                                entityWorld.setComponent(Game.ENTITY, new CinematicComponent(Map_Destroforest_CinematicIntro.class.getName()));
                                break;
                        }
                    }catch(NumberFormatException ex){
                    }
                }
                else if(message.getText().startsWith("/gold ")){
                    try{
                        int gold = Integer.parseInt(message.getText().substring(6));
                        if(gold >= 0){
                            entityWorld.setComponent(selectedUnit, new GoldComponent(gold));
                        }
                        else{
                            messageResponse.addAnswerMessage(new Message_ChatMessage("No negative gold values allowed"));
                        }
                    }catch(NumberFormatException ex){
                    }
                }
                else if(message.getText().startsWith("/deathtimer ")){
                    try{
                        int initialDuration = Integer.parseInt(message.getText().substring(12));
                        if(initialDuration >= 0){
                            int playerDeathRulesEntity = entityWorld.getComponent(game.getMap().getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
                            entityWorld.setComponent(playerDeathRulesEntity, new RespawnTimerComponent(initialDuration, 0));
                        }
                        else{
                            messageResponse.addAnswerMessage(new Message_ChatMessage("No negative death timers allowed"));
                        }
                    }catch(NumberFormatException ex){
                    }
                }
                else if(message.getText().startsWith("/urf ")){
                    try{
                        float cooldownSpeed = Float.parseFloat(message.getText().substring(5));
                        EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
                        EntityWrapper buffEffect = entityWorld.getWrapped(entityWorld.createEntity());
                        buffEffect.setComponent(new BonusPercentageCooldownSpeedComponent(cooldownSpeed));
                        buff.setComponent(new ContinuousEffectComponent(buffEffect.getId()));
                        ApplyAddBuffsSystem.addBuff(entityWorld, selectedUnit, buff.getId());
                    }catch(NumberFormatException ex){
                    }
                }
            }
        }
    }
}
