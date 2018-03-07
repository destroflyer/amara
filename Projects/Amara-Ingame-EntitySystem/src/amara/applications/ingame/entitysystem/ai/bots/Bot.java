/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.bots;

import amara.ingame.ai.DecisionMaking;

/**
 *
 * @author Carl
 */
public class Bot{

    protected float decisionInterval = 0.2f;
    protected DecisionMaking decisionMaking = new DecisionMaking();

    public float getDecisionInterval(){
        return decisionInterval;
    }

    public DecisionMaking getDecisionMaking(){
        return decisionMaking;
    }
}
