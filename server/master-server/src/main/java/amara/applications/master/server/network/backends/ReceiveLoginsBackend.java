package amara.applications.master.server.network.backends;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.applications.master.server.games.GamePlayerInfo_Human;
import amara.applications.master.server.players.*;
import amara.core.files.FileManager;
import amara.libraries.network.*;
import org.bouncycastle.openssl.PEMReader;

import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class ReceiveLoginsBackend implements MessageBackend {

    public ReceiveLoginsBackend(PlayersAppState playersAppState, GamesAppState gamesAppState) {
        this.playersAppState = playersAppState;
        this.gamesAppState = gamesAppState;
        authTokenVerifier = createAuthTokenVerifier();
    }
    private PlayersAppState playersAppState;
    private GamesAppState gamesAppState;
    private JWTVerifier authTokenVerifier;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_Login) {
            Message_Login message = (Message_Login) receivedMessage;
            try {
                DecodedJWT decodedJWT = authTokenVerifier.verify(message.getAuthToken());
                Map<String, Object> user = decodedJWT.getClaim("user").asMap();
                int playerId = (int) user.get("id");
                String login = (String) user.get("login");
                ConnectedPlayers connectedPlayers = playersAppState.getConnectedPlayers();
                connectedPlayers.login(messageResponse.getClientId(), new Player(playerId, login));
                System.out.println("Login '" + login + "' (#" + playerId + ")");
                boolean isIngame = checkAndUpdateRunningGame(messageResponse.getClientId(), playerId);
                messageResponse.addAnswerMessage(new Message_LoginResult(playerId, isIngame));
            } catch (JWTVerificationException ex) {
                // Token should not be trusted
            }
        }
    }

    private JWTVerifier createAuthTokenVerifier() {
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) readPublicKey("./public_auth_key_to_the_city.ini"), null);
        return JWT.require(algorithm).build();
    }

    private PublicKey readPublicKey(String pathFilePath) {
        try {
            String publicKeyPath = FileManager.getFileContent(pathFilePath);
            PEMReader pemReader = new PEMReader(new FileReader(publicKeyPath));
            byte[] publicKeyBytes = pemReader.readPemObject().getContent();
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean checkAndUpdateRunningGame(int clientId, int playerId) {
        Game game = gamesAppState.getRunningGames().getGame(playerId);
        if (game != null) {
            GamePlayer<GamePlayerInfo_Human> gamePlayer = game.getPlayerByPlayerId(playerId);
            gamePlayer.getGamePlayerInfo().setClientId(clientId);
            game.getSubNetworkServer().add(clientId);
            System.out.println("Player #" + playerId + " is already ingame - Updated to the new connection (Client #" + clientId + ").");
            return true;
        }
        return false;
    }
}
