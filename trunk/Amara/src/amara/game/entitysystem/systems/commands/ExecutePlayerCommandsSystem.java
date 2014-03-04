/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import amara.game.entitysystem.components.visuals.StopPlayingAnimationComponent;
import java.util.Iterator;
import amara.Queue;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;

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
            EntityWrapper player = entityWorld.getWrapped(getPlayerEntityID(entityWorld, playerCommand.getClientID()));
            int selectedUnit = player.getComponent(SelectedUnitComponent.class).getEntityID();
            if(command instanceof MoveCommand){
                MoveCommand moveCommand = (MoveCommand) command;
                entityWorld.removeComponent(selectedUnit, AutoAttackTargetComponent.class);
                entityWorld.setComponent(selectedUnit, new MovementTargetComponent(moveCommand.getPosition()));
            }
            else if(command instanceof StopCommand){
                StopCommand stopCommand = (StopCommand) command;
                entityWorld.removeComponent(selectedUnit, MovementTargetComponent.class);
                entityWorld.removeComponent(selectedUnit, MovementSpeedComponent.class);
                entityWorld.removeComponent(selectedUnit, AutoAttackTargetComponent.class);
                entityWorld.setComponent(selectedUnit, new StopPlayingAnimationComponent());
            }
            else if(command instanceof AutoAttackCommand){
                AutoAttackCommand autoAttackCommand = (AutoAttackCommand) command;
                if(entityWorld.hasComponent(selectedUnit, AutoAttackComponent.class)){
                    if(entityWorld.hasComponent(autoAttackCommand.getTargetEntityID(), IsTargetableComponent.class)){
                        entityWorld.removeComponent(selectedUnit, MovementTargetComponent.class);
                        entityWorld.removeComponent(selectedUnit, MovementSpeedComponent.class);
                        entityWorld.setComponent(selectedUnit, new AutoAttackTargetComponent(autoAttackCommand.getTargetEntityID()));
                    }
                }
            }
            else if(command instanceof CastSelfcastSpellCommand){
                CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                int spell = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntitiesIDs()[castSelfcastSpellCommand.getSpellIndex()];
                if(!entityWorld.hasAnyComponent(spell, RemainingCooldownComponent.class, IsSilencedComponent.class, IsStunnedComponent.class)){
                    entityWorld.setComponent(selectedUnit, new CastSelfcastSpellComponent(spell));
                }
            }
            else if(command instanceof CastSingleTargetSpellCommand){
                CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                if(entityWorld.hasComponent(castSingleTargetSpellCommand.getTargetEntityID(), IsTargetableComponent.class)){
                    int spell = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntitiesIDs()[castSingleTargetSpellCommand.getSpellIndex()];
                    if(!entityWorld.hasAnyComponent(spell, RemainingCooldownComponent.class, IsSilencedComponent.class, IsStunnedComponent.class)){
                        entityWorld.setComponent(selectedUnit, new CastSingleTargetSpellComponent(spell, castSingleTargetSpellCommand.getTargetEntityID()));
                    }
                }
            }
            else if(command instanceof CastLinearSkillshotSpellCommand){
                CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                int spell = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntitiesIDs()[castLinearSkillshotSpellCommand.getSpellIndex()];
                if(!entityWorld.hasAnyComponent(spell, RemainingCooldownComponent.class, IsSilencedComponent.class, IsStunnedComponent.class)){
                    entityWorld.setComponent(selectedUnit, new CastLinearSkillshotSpellComponent(spell, castLinearSkillshotSpellCommand.getDirection()));
                }
            }
            else if(command instanceof CastPositionalSkillshotSpellCommand){
                CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                int spell = entityWorld.getComponent(selectedUnit, SpellsComponent.class).getSpellsEntitiesIDs()[castPositionalSkillshotSpellCommand.getSpellIndex()];
                if(!entityWorld.hasAnyComponent(spell, RemainingCooldownComponent.class, IsSilencedComponent.class, IsStunnedComponent.class)){
                    entityWorld.setComponent(selectedUnit, new CastPositionalSkillshotSpellComponent(spell, castPositionalSkillshotSpellCommand.getPosition()));
                }
            }
        }
        playerCommandsQueue.clear();
    }
    
    private int getPlayerEntityID(EntityWorld entityWorld, int clientID){
        for(int entity : entityWorld.getEntitiesWithAll(ClientComponent.class)){
            if(entityWorld.getComponent(entity, ClientComponent.class).getClientID() == clientID){
                return entity;
            }
        }
        return -1;
    }
}
