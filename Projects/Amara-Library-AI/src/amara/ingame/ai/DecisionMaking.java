/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.ingame.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DecisionMaking{

    private ArrayList<Goal> goals = new ArrayList<>();
    private LinkedList<Action> bestActions = new LinkedList<>();
    
    public LinkedList<Action> evaluateActions(EntityWorld entityWorld, int entity){
        Goal bestGoal = evaluateBestGoal(entityWorld, entity);
        return evaluateGoalActions(entityWorld, entity, bestGoal);
    }
    
    private LinkedList<Action> evaluateGoalActions(EntityWorld entityWorld, int entity, Goal goal){
        bestActions.clear();
        if(goal != null){
            goal.addBestActions(entityWorld, entity, bestActions);
        }
        return bestActions;
    }
    
    private Goal evaluateBestGoal(EntityWorld entityWorld, int entity){
        Goal bestGoal = null;
        double maximumValue = -1;
        for(Goal goal : goals){
            if(goal.isEnabled(entityWorld, entity)){
                goal.initialize(entityWorld, entity);
                double value = goal.getValue(entityWorld, entity);
                if(value > maximumValue){
                    bestGoal = goal;
                    maximumValue = value;
                }
            }
        }
        return bestGoal;
    }
    
    public void addGoal(Goal goal){
        goals.add(goal);
    }
}
