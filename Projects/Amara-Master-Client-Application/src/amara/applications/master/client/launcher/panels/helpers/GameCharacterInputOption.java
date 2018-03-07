/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.helpers;

import amara.applications.master.network.messages.objects.GameCharacter;

/**
 *
 * @author Carl
 */
public class GameCharacterInputOption {

    public GameCharacterInputOption(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }
    private GameCharacter gameCharacter;

    @Override
    public String toString() {
        return gameCharacter.getTitle();
    }

    public GameCharacter getGameCharacter(){
        return gameCharacter;
    }
    
    public static GameCharacterInputOption[] createOptions(GameCharacter[] gameCharacters){
        GameCharacterInputOption[] inputOptions = new GameCharacterInputOption[gameCharacters.length];
        for(int i=0;i<gameCharacters.length;i++){
            inputOptions[i] = new GameCharacterInputOption(gameCharacters[i]);
        }
        return inputOptions;
    }
}
