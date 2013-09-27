/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.units;

/**
 *
 * @author Carl
 */
public class TeamComponent{

    public TeamComponent(int teamEntityID){
        this.teamEntityID = teamEntityID;
    }
    private int teamEntityID;

    public int getTeamEntityID(){
        return teamEntityID;
    }
}
