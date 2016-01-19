/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.network.backends;

import com.jme3.math.Vector2f;
import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.engine.applications.ingame.server.IngameServerApplication;
import amara.engine.applications.ingame.server.appstates.*;
import amara.engine.network.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.game.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;
import amara.game.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.game.games.*;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ReceiveChatMessagesBackend implements MessageBackend{

    public ReceiveChatMessagesBackend(IngameServerApplication ingameServerApplication){
        this.ingameServerApplication = ingameServerApplication;
    }
    private IngameServerApplication ingameServerApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, final MessageResponse messageResponse){
        if(receivedMessage instanceof Message_SendChatMessage){
            final Message_SendChatMessage message = (Message_SendChatMessage) receivedMessage;
            final Game game = ingameServerApplication.getGame();
            final GamePlayer gamePlayer = game.getPlayer(messageResponse.getClientID());
            if(gamePlayer != null){
                messageResponse.addBroadcastMessage(new Message_ChatMessage(gamePlayer.getLobbyPlayer().getID(), message.getText()));
                ingameServerApplication.enqueueTask(new Runnable(){

                    @Override
                    public void run(){
                        MessageResponse chatCommandResponse = new MessageResponse(messageResponse.getClientID());
                        EntityWorld entityWorld = ingameServerApplication.getStateManager().getState(ServerEntitySystemAppState.class).getEntityWorld();
                        int selectedUnit = entityWorld.getComponent(gamePlayer.getEntityID(), SelectedUnitComponent.class).getEntity();
                        if(message.getText().equals("such chat")){
                            chatCommandResponse.addAnswerMessage(new Message_ChatMessage("very responsive, wow"));
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
                                        entityWorld.setComponent(Game.ENTITY, new CinematicComponent("amara.game.maps.TestMap_TestCinematic"));
                                        break;

                                    case 1:
                                        entityWorld.setComponent(Game.ENTITY, new CinematicComponent("amara.game.maps.Map_Destroforest_CinematicIntro"));
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
                                    chatCommandResponse.addAnswerMessage(new Message_ChatMessage("No negative gold values allowed"));
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
                                    chatCommandResponse.addAnswerMessage(new Message_ChatMessage("No negative death timers allowed"));
                                }
                            }catch(NumberFormatException ex){
                            }
                        }
                        else if(message.getText().startsWith("/urf ")){
                            try{
                                float cooldownSpeed = Float.parseFloat(message.getText().substring(5));
                                EntityWrapper buff = entityWorld.getWrapped(entityWorld.createEntity());
                                EntityWrapper buffAttributes = entityWorld.getWrapped(entityWorld.createEntity());
                                buffAttributes.setComponent(new BonusPercentageCooldownSpeedComponent(cooldownSpeed));
                                buff.setComponent(new ContinuousAttributesComponent(buffAttributes.getId()));
                                buff.setComponent(new KeepOnDeathComponent());
                                ApplyAddBuffsSystem.addBuff(entityWorld, selectedUnit, buff.getId());
                            }catch(NumberFormatException ex){
                            }
                        }
                        else if(message.getText().startsWith("/chickens ")){
                            try{
                                int chickenCount = Integer.parseInt(message.getText().substring(10));
                                final int chickensMinimum = 1;
                                final int chickensMaximum = 10000;
                                if((chickenCount >= chickensMinimum) && (chickenCount <= chickensMaximum)){
                                    EntityWrapper animation = entityWorld.getWrapped(entityWorld.createEntity());
                                    animation.setComponent(new NameComponent("idle1"));
                                    animation.setComponent(new LoopDurationComponent(45.6f));
                                    for(int i=0;i<chickenCount;i++){
                                        EntityWrapper chicken = entityWorld.getWrapped(entityWorld.createEntity());
                                        chicken.setComponent(new NameComponent("Chicken"));
                                        chicken.setComponent(new ModelComponent("Models/chicken/skin.xml"));
                                        float x = (float) (Math.random() * game.getMap().getPhysicsInformation().getWidth());
                                        float y = (float) (Math.random() * game.getMap().getPhysicsInformation().getHeight());
                                        chicken.setComponent(new PositionComponent(new Vector2f(x, y)));
                                        chicken.setComponent(new DirectionComponent((float) (Math.random() * (2 * Math.PI))));
                                        chicken.setComponent(new AnimationComponent(animation.getId()));
                                    }
                                }
                                else{
                                    chatCommandResponse.addAnswerMessage(new Message_ChatMessage("Chicken count has to be between " + chickensMinimum + " and " + chickensMaximum));
                                }
                            }catch(NumberFormatException ex){
                            }
                        }
                        else if(message.getText().startsWith("/nochickens")){
                            for(int entity : entityWorld.getEntitiesWithAll(NameComponent.class, ModelComponent.class)){
                                String name = entityWorld.getComponent(entity, NameComponent.class).getName();
                                if(name.equals("Chicken")){
                                    entityWorld.removeEntity(entityWorld.getComponent(entity, AnimationComponent.class).getAnimationEntity());
                                    entityWorld.removeEntity(entity);
                                }
                            }
                        }
                        NetworkServer networkServer = ingameServerApplication.getStateManager().getState(NetworkServerAppState.class).getNetworkServer();
                        networkServer.sendMessageResponse(chatCommandResponse);
                    }
                });
            }
        }
    }
}
