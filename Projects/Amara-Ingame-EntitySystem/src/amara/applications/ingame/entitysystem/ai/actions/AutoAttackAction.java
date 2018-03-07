/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.actions;

import amara.ingame.ai.Action;

/**
 *
 * @author Carl
 */
public class AutoAttackAction extends Action{

    public AutoAttackAction(int targetEntity){
        this.targetEntity = targetEntity;
    }
    private int targetEntity;

    public int getTargetEntity(){
        return targetEntity;
    }
}
