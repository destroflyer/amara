/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network;

import com.jme3.network.serializing.Serializer;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class MessagesSerializer_Master{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            //These update classes have to be always registered first to keep the updater backwards compatible
            Message_GetUpdateFiles.class,
            Message_GetUpdateFile.class,
            Message_UpdateFilePart.class,
            Message_UpdateFiles.class,
                UpdateFile.class,
                
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
            
            Message_AcceptGameSelection.class,
            Message_AddLobbyBot.class,
            Message_CancelLobbyQueue.class,
            Message_CreateLobby.class,
            Message_GameSelectionAcceptRequest.class,
            Message_GameSelectionUpdate.class,
                GameSelection.class,
                    GameSelectionData.class,
                        TeamFormat.class,
                    GameSelectionPlayer.class,
                        GameSelectionPlayerData.class,
                    Lobby.class,
                        LobbyData.class,
                        LobbyPlayer_Human.class,
                        LobbyPlayer_Bot.class,
            Message_GenericInformation.class,
            Message_InviteLobbyPlayer.class,
            Message_LeaveLobby.class,
            Message_LobbyClosed.class,
            Message_LobbyQueueStatus.class,
            Message_KickLobbyPlayer.class,
            Message_LobbyUpdate.class,
            Message_LockInGameSelection.class,
            Message_SetGameSelectionPlayerData.class,
            Message_SetLobbyData.class,
            Message_StartLobbyQueue.class,
            Message_GameCreated.class
        );
    }
}
