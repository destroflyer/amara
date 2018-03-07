/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.games;

import java.util.LinkedList;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class PendingGameSelection{

    public PendingGameSelection(GameSelection gameSelection, float remainingAcceptTime){
        this.gameSelection = gameSelection;
        this.remainingAcceptTime = remainingAcceptTime;
        handleBotSelections();
    }
    private GameSelection gameSelection;
    private float remainingAcceptTime;
    private LinkedList<GameSelectionPlayer> acceptingPlayers = new LinkedList<GameSelectionPlayer>();
    
    private void handleBotSelections() {
        for (GameSelectionPlayer[] team : gameSelection.getTeams()) {
            for (GameSelectionPlayer player : team) {
                if (player.getLobbyPlayer() instanceof LobbyPlayer_Bot) {
                    LobbyPlayer_Bot lobbyPlayer_Bot = (LobbyPlayer_Bot) player.getLobbyPlayer();
                    accept(player);
                    player.setPlayerData(lobbyPlayer_Bot.getGameSelectionPlayerData());
                    player.lockIn();
                }
            }
        }
    }

    public void onTimePassed(float time){
        remainingAcceptTime -= time;
        if(remainingAcceptTime < 0){
            remainingAcceptTime = 0;
        }
    }
    
    public void accept(GameSelectionPlayer player){
        if(!acceptingPlayers.contains(player)){
            acceptingPlayers.add(player);
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
    
    public boolean hasAccepted(LobbyPlayer lobbyPlayer){
        for(GameSelectionPlayer player : acceptingPlayers){
            if(player.getLobbyPlayer() == lobbyPlayer){
                return true;
            }
        }
        return false;
    }
}
