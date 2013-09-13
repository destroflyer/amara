/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.commands;

import java.util.Iterator;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.input.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.selection.*;
import amara.game.entitysystem.components.units.SpellsComponent;

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
                if(entityWorld.getCurrent().hasAllComponents(entity, IsSelectableComponent.class)){
                    entityWorld.getCurrent().setComponent(entity, new IsSelectedComponent());
                }
            }
            else if(command instanceof DeselectionCommand){
                DeselectionCommand deselectionCommand = (DeselectionCommand) command;
                deselectAllEntites(entityWorld);
            }
            else if(command instanceof MoveCommand){
                MoveCommand moveCommand = (MoveCommand) command;
                for(int entity : entityWorld.getCurrent().getEntitiesWithAll(IsSelectedComponent.class))
                {
                    entityWorld.getCurrent().setComponent(entity, new MovementTargetComponent(moveCommand.getPosition()));
                }
            }
            else if(command instanceof CastSingleTargetSpellCommand){
                CastSingleTargetSpellCommand castSingleTargetSpellCommand = (CastSingleTargetSpellCommand) command;
                for(int entity : entityWorld.getCurrent().getEntitiesWithAll(IsSelectedComponent.class))
                {
                    int[] spells = entityWorld.getCurrent().getComponent(entity, SpellsComponent.class).getSpellsEntitiesIDs();
                    int spellEntityID = spells[castSingleTargetSpellCommand.getSpellIndex()];
                    entityWorld.getCurrent().setComponent(entity, new CastSingleTargetSpellComponent(spellEntityID, castSingleTargetSpellCommand.getTargetEntityID()));
                }
            }
        }
    }
    
    private void deselectAllEntites(EntityWorld entityWorld){
        for(int entity : entityWorld.getCurrent().getEntitiesWithAll(IsSelectedComponent.class))
        {
            entityWorld.getCurrent().removeComponent(entity, IsSelectedComponent.class);
        }
    }
}
