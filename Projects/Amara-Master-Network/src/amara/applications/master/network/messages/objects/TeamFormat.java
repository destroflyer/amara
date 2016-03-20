/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import java.util.Arrays;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TeamFormat{

    public TeamFormat(){
        
    }
    
    public TeamFormat(int... teamSizes){
        this.teamSizes = teamSizes;
    }
    private int[] teamSizes;
    
    public int getTeamsCount(){
        return teamSizes.length;
    }

    public int getTeamSize(int teamIndex){
        return teamSizes[teamIndex];
    }

    public boolean equals(TeamFormat teamFormat){
        return Arrays.equals(teamSizes, teamFormat.teamSizes);
    }

    public int[] getTeamSizes(){
        return teamSizes;
    }
}
