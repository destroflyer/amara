package amara.applications.master.server.appstates;

import amara.applications.master.server.appstates.model.DestrostudiosUser;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.BaseHeadlessAppState;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class DestrostudiosAppState extends BaseHeadlessAppState {

    private static final String MASTER_SERVER_URL = "https://destrostudios.com:8080";

    private HttpClient httpClient;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<Integer, String> loginsByPlayerId = new HashMap<>();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        httpClient = HttpClient.newHttpClient();
    }

    public String getLoginByPlayerId(int playerId) {
        return loginsByPlayerId.computeIfAbsent(playerId, pid -> {
            DestrostudiosUser userById = getUserById(playerId);
            return ((userById != null) ? userById.getLogin() : null);
        });
    }

    public DestrostudiosUser getUserById(int playerId) {
        return get("/users/" + playerId, DestrostudiosUser.class);
    }

    public DestrostudiosUser getUserByLogin(String login) {
        return get("/users/byLogin?login=" + login, DestrostudiosUser.class);
    }

    private <T> T get(String endpoint, Class<T> responseClass) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(MASTER_SERVER_URL + endpoint)).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return objectMapper.readValue(httpResponse.body(), responseClass);
            }
        } catch (URISyntaxException | InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
