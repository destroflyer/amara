/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.LinkedList;
import amara.applications.master.network.messages.objects.GameSelection;

/**
 *
 * @author Carl
 */
public class PendingGameSelection{

    public PendingGameSelection(GameSelection gameSelection, float remainingAcceptTime){
        this.gameSelection = gameSelection;
        this.remainingAcceptTime = remainingAcceptTime;
    }
    private GameSelection gameSelection;
    private float remainingAcceptTime;
    private LinkedList<Integer> acceptingPlayers = new LinkedList<Integer>();
    private LinkedList<Integer> lockedInPlayers = new LinkedList<Integer>();

    public void onTimePassed(float time){
        remainingAcceptTime -= time;
        if(remainingAcceptTime < 0){
            remainingAcceptTime = 0;
        }
    }
    
    public void accept(int playerID){
        if(!acceptingPlayers.contains(playerID)){
            acceptingPlayers.add(playerID);
        }
    }
    
    public void lockIn(int playerID){
        if(!lockedInPlayers.contains(playerID)){
            lockedInPlayers.add(playerID);
        }
    }

    public GameSelection getGameSelection(){
        return gameSelection;
    }

    public float getRemainingAcceptTime(){
        return remainingAcceptTime;
    }
    
    public boolean isAccepted(){
        return (acceptingPlayers.size() == gameSelection.getPlayersCount());
    }
    
    public boolean hasAccepted(int playerID){
        return acceptingPlayers.contains(playerID);
    }
    
    public boolean isLockedIn(){
        return (lockedInPlayers.size() == gameSelection.getPlayersCount());
    }
}
