package amara.applications.master.client;

import amara.applications.master.client.appstates.*;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.applications.headless.applications.HeadlessAppState;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

public class MasterserverClientUtil {

    public static NetworkClient getNetworkClient() {
        return getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }

    public static int getPlayerId() {
        return getState(LoginAppState.class).getPlayerId();
    }

    public static PlayerProfileData getPlayerProfile(int playerId) {
        return getState(PlayerProfilesAppState.class).getPlayerProfile(playerId);
    }

    public static PlayerProfileData getPlayerProfile(String login) {
        return getState(PlayerProfilesAppState.class).getPlayerProfile(login);
    }

    public static PlayerStatus getPlayerStatus(int playerId) {
        return getState(PlayerStatiAppState.class).getPlayerStatus(playerId);
    }

    public static GameCharacter getCharacter(int characterId) {
        return getState(CharactersAppState.class).getCharacter(characterId);
    }

    public static GameCharacter[] getPublicCharacters() {
        return getState(CharactersAppState.class).getPublicCharacters();
    }

    public static OwnedGameCharacter[] getOwnedCharacters() {
        return getState(CharactersAppState.class).getOwnedCharacters();
    }

    public static String[] getAvailableMaps() {
        return getState(MapsAppState.class).getMapNames();
    }

    private static <T extends HeadlessAppState> T getState(Class<T> stateClass) {
        return MasterserverClientApplication.getInstance().getStateManager().getState(stateClass);
    }
}
