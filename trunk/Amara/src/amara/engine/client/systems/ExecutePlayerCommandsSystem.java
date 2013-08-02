/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import java.util.Iterator;
import com.jme3.math.Vector2f;
import amara.Queue;
import amara.engine.client.commands.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.selection.*;

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
                    //TODO: Replace with entityWorld.getCurrent().setComponent(entity, new MovementTargetComponent(moveCommand.getPosition()));
                    PositionComponent positionComponent = entityWorld.getCurrent().getComponent(entity, PositionComponent.class);
                    Vector2f moveDirection = moveCommand.getPosition().subtract(positionComponent.getPosition()).normalizeLocal().multLocal(2.5f);
                    entityWorld.getCurrent().setComponent(entity, new MovementSpeedComponent(moveDirection));
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
