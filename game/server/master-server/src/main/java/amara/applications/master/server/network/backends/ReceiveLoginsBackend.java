/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import amara.core.files.FileManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.server.appstates.*;
import amara.applications.master.server.players.*;
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

/**
 *
 * @author Carl
 */
public class ReceiveLoginsBackend implements MessageBackend {

    public ReceiveLoginsBackend(PlayersAppState playersAppState) {
        this.playersAppState = playersAppState;
        authTokenVerifier = createAuthTokenVerifier();
    }
    private PlayersAppState playersAppState;
    private JWTVerifier authTokenVerifier;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_Login) {
            Message_Login message = (Message_Login) receivedMessage;
            int playerId = 0;
            try {
                DecodedJWT decodedJWT = authTokenVerifier.verify(message.getAuthToken());
                Map<String, Object> user = decodedJWT.getClaim("user").asMap();
                playerId = (int) user.get("id");
                String login = (String) user.get("login");
                ConnectedPlayers connectedPlayers = playersAppState.getConnectedPlayers();
                connectedPlayers.login(messageResponse.getClientId(), new Player(playerId, login));
                System.out.println("Login '" + login + "' (#" + playerId + ")");
            } catch (JWTVerificationException ex) {
                // Token should not be trusted
            }
            messageResponse.addAnswerMessage(new Message_LoginResult(playerId));
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
}
