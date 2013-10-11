/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import java.util.Iterator;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.engine.client.commands.casting.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.selection.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class ExecutePlayerCommandsSystem implements EntitySystem{
    
    public ExecutePlayerCommandsSystem(Queue<Command> commandsQueue){
        this.commandsQueue = commandsQueue;
    }
    private Queue<Command> commandsQueue;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        Iterator<Command> commandsIterator = commandsQueue.getIterator();
        while(commandsIterator.hasNext()){
            Command command = commandsIterator.next();
            if(command instanceof SelectionCommand){
                SelectionCommand selectionCommand = (SelectionCommand) command;
                deselectAllEntites(entityWorld);
                int entity = selectionCommand.getEntity();
                if(entityWorld.hasAllComponents(entity, IsSelectableComponent.class)){
                    entityWorld.setComponent(entity, new IsSelectedComponent());
                }
            }
            else if(command instanceof DeselectionCommand){
                DeselectionCommand deselectionCommand = (DeselectionCommand) command;
                deselectAllEntites(entityWorld);
            }
            else if(command instanceof MoveCommand){
                MoveCommand moveCommand = (MoveCommand) command;
                for(int entity : entityWorld.getEntitiesWithAll(IsSelectedComponent.class))
                {
                    entityWorld.removeComponent(entity, AutoAttackTargetComponent.class);
                    entityWorld.setComponent(entity, new MovementTargetComponent(moveCommand.getPosition()));
                }
            }
            else if(command instanceof StopCommand){
                StopCommand stopCommand = (StopCommand) command;
                for(int entity : entityWorld.getEntitiesWithAll(IsSelectedComponent.class))
                {
                    entityWorld.removeComponent(entity, MovementTargetComponent.class);
                    entityWorld.removeComponent(entity, MovementSpeedComponent.class);
                    entityWorld.removeComponent(entity, AutoAttackTargetComponent.class);
                    entityWorld.removeComponent(entity, AnimationComponent.class);
                }
            }
            else if(command instanceof AutoAttackCommand){
                AutoAttackCommand autoAttackCommand = (AutoAttackCommand) command;
                if(entityWorld.hasComponent(autoAttackCommand.getEntity(), AutoAttackComponent.class)){
                    if(entityWorld.hasComponent(autoAttackCommand.getTargetEntityID(), IsTargetableComponent.class)){
                        entityWorld.removeComponent(autoAttackCommand.getEntity(), MovementTargetComponent.class);
                        entityWorld.removeComponent(autoAttackCommand.getEntity(), MovementSpeedComponent.class);
                        entityWorld.setComponent(autoAttackCommand.getEntity(), new AutoAttackTargetComponent(autoAttackCommand.getTargetEntityID()));
                    }
                }
            }
            else if(command instanceof CastSelfcastSpellCommand){
                CastSelfcastSpellCommand castSelfcastSpellCommand = (CastSelfcastSpellCommand) command;
                int spell = entityWorld.getComponent(castSelfcastSpellCommand.getEntity(), SpellsComponent.class).getSpellsEntitiesIDs()[castSelfcastSpellCommand.getSpellIndex()];
                if(!entityWorld.hasComponent(spell, RemainingCooldownComponent.class)){
                    entityWorld.setComponent(castSelfcastSpellCommand.getEntity(), new CastSelfcastSpellComponent(spell));
                }
            }
            else if(command instanceof CastSingleTargetSpellCommand){
                CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                if(entityWorld.hasComponent(castSingleTargetSpellCommand.getTargetEntityID(), IsTargetableComponent.class)){
                    int spell = entityWorld.getComponent(castSingleTargetSpellCommand.getEntity(), SpellsComponent.class).getSpellsEntitiesIDs()[castSingleTargetSpellCommand.getSpellIndex()];
                    if(!entityWorld.hasComponent(spell, RemainingCooldownComponent.class)){
                        entityWorld.setComponent(castSingleTargetSpellCommand.getEntity(), new CastSingleTargetSpellComponent(spell, castSingleTargetSpellCommand.getTargetEntityID()));
                    }
                }
            }
            else if(command instanceof CastLinearSkillshotSpellCommand){
                CastLinearSkillshotSpellCommand castLinearSkillshotSpellCommand = (CastLinearSkillshotSpellCommand) command;
                int spell = entityWorld.getComponent(castLinearSkillshotSpellCommand.getEntity(), SpellsComponent.class).getSpellsEntitiesIDs()[castLinearSkillshotSpellCommand.getSpellIndex()];
                if(!entityWorld.hasComponent(spell, RemainingCooldownComponent.class)){
                    entityWorld.setComponent(castLinearSkillshotSpellCommand.getEntity(), new CastLinearSkillshotSpellComponent(spell, castLinearSkillshotSpellCommand.getDirection()));
                }
            }
            else if(command instanceof CastPositionalSkillshotSpellCommand){
                CastPositionalSkillshotSpellCommand castPositionalSkillshotSpellCommand = (CastPositionalSkillshotSpellCommand) command;
                int spell = entityWorld.getComponent(castPositionalSkillshotSpellCommand.getEntity(), SpellsComponent.class).getSpellsEntitiesIDs()[castPositionalSkillshotSpellCommand.getSpellIndex()];
                if(!entityWorld.hasComponent(spell, RemainingCooldownComponent.class)){
                    entityWorld.setComponent(castPositionalSkillshotSpellCommand.getEntity(), new CastPositionalSkillshotSpellComponent(spell, castPositionalSkillshotSpellCommand.getPosition()));
                }
            }
        }
    }
    
    private void deselectAllEntites(EntityWorld entityWorld){
        for(int entity : entityWorld.getEntitiesWithAll(IsSelectedComponent.class))
        {
            entityWorld.removeComponent(entity, IsSelectedComponent.class);
        }
    }
}
