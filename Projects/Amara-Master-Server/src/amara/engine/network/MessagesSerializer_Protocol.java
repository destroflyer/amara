/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import com.jme3.network.serializing.Serializer;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.messages.Message_GameCreated;
import amara.engine.network.messages.Message_Ping;
import amara.engine.network.messages.Message_Pong;
import amara.engine.network.messages.protocol.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer_Protocol{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            //These update classes have to be always registered first to keep the updater backwards compatible
            Message_GetUpdateFiles.class,
            Message_GetUpdateFile.class,
            Message_UpdateFilePart.class,
            Message_UpdateFiles.class,
                UpdateFile.class,
            
            Message_Ping.class,
            Message_Pong.class,
                
            Message_EditActiveCharacterSkin.class,
            Message_EditCharacterInventory.class,
            Message_EditUserMeta.class,
            Message_GameContents.class,
                GameCharacter.class,
                    GameCharacterSkin.class,
                Item.class,
            Message_GetGameContents.class,
            Message_GetPlayerProfileData.class,
            Message_GetPlayerStatus.class,
            Message_Login.class,
                AuthentificationInformation.class,
            Message_LoginResult.class,
            Message_Logout.class,
            Message_OwnedCharacters.class,
                OwnedGameCharacter.class,
            Message_OwnedItems.class,
                OwnedItem.class,
            Message_PlayerProfileData.class,
                PlayerProfileData.class,
            Message_PlayerProfileDataNotExistant.class,
            Message_PlayerStatus.class,
            
            Message_CreateLobby.class,
            Message_SetLobbyData.class,
                Lobby.class,
                LobbyPlayer.class,
                LobbyData.class,
            Message_SetLobbyPlayerData.class,
                LobbyPlayerData.class,
            Message_InviteLobbyPlayer.class,
            Message_LeaveLobby.class,
            Message_LobbyClosed.class,
            Message_KickLobbyPlayer.class,
            Message_LobbyUpdate.class,
            Message_StartGame.class,
            Message_GameCreated.class
        );
    }
}
