/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.bots;

import amara.applications.ingame.entitysystem.ai.goals.*;

/**
 *
 * @author Carl
 */
public class EasyBot extends Bot{

    public EasyBot(){
        decisionMaking.addGoal(new WalkToEnemyNexusGoal());
        decisionMaking.addGoal(new LastHitGoal());
    }
}
