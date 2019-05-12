/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Chickens extends ChatCommand{

    public static final String NAME = "Chicken";
    private static final int MINIMUM = 1;
    private static final int MAXIMUM = 10000;
            
    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer){
        try{
            int count = Integer.parseInt(optionString);
            if((count >= MINIMUM) && (count <= MAXIMUM)){
                int animationEntity = entityWorld.createEntity();
                entityWorld.setComponent(animationEntity, new NameComponent("idle1"));
                entityWorld.setComponent(animationEntity, new LoopDurationComponent(45.6f));
                for(int i=0;i<count;i++){
                    int chickenEntity = entityWorld.createEntity();
                    entityWorld.setComponent(chickenEntity, new NameComponent(NAME));
                    entityWorld.setComponent(chickenEntity, new ModelComponent("Models/chicken/skin.xml"));
                    float x = (float) (Math.random() * game.getMap().getPhysicsInformation().getWidth());
                    float y = (float) (Math.random() * game.getMap().getPhysicsInformation().getHeight());
                    entityWorld.setComponent(chickenEntity, new PositionComponent(new Vector2f(x, y)));
                    entityWorld.setComponent(chickenEntity, new DirectionComponent((float) (Math.random() * (2 * Math.PI))));
                    entityWorld.setComponent(chickenEntity, new AnimationComponent(animationEntity));
                }
            }
            else{
                setResponseMessage("Chicken count has to be between " + MINIMUM + " and " + MAXIMUM);
            }
        }catch(NumberFormatException ex){
        }
    }
}
