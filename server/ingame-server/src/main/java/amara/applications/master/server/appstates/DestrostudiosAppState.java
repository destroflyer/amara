/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.master.server.appstates.model.DestrostudiosUser;
import amara.core.files.FileManager;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.BaseHeadlessAppState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class DestrostudiosAppState extends BaseHeadlessAppState {

    private static final String MASTER_SERVER_URL = "https://destrostudios.com:8080";

    private CloseableHttpClient httpClient;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<Integer, String> loginsByPlayerId = new HashMap<>();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        httpClient = createHttpClient();
    }

    private CloseableHttpClient createHttpClient() {
        try {
            String[] sslKeyStore = FileManager.getFileLines("./ssl_keystore_to_the_city.ini");
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File(sslKeyStore[0]), sslKeyStore[1].toCharArray(), new TrustSelfSignedStrategy())
                    .build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier()
            );
            return HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | KeyManagementException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getLoginByPlayerId(int playerId) {
        return loginsByPlayerId.computeIfAbsent(playerId, pid -> {
            DestrostudiosUser userById = getUserById(playerId);
            return ((userById != null) ? userById.getLogin() : null);
        });
    }

    public DestrostudiosUser getUserById(int playerId) {
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(MASTER_SERVER_URL + "/users/" + playerId));
            return objectMapper.readValue(response.getEntity().getContent(), DestrostudiosUser.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public DestrostudiosUser getUserByLogin(String login) {
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(MASTER_SERVER_URL + "/users/byLogin?login=" + login));
            if (response.getStatusLine().getStatusCode() == 200) {
                return objectMapper.readValue(response.getEntity().getContent(), DestrostudiosUser.class);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
