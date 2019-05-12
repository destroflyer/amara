/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.ingame.ai;

import java.util.LinkedList;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class Goal{

    public abstract boolean isEnabled(EntityWorld entityWorld, int entity);

    public abstract void initialize(EntityWorld entityWorld, int entity);

    public abstract double getValue(EntityWorld entityWorld, int entity);
    
    public abstract void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions);
}
