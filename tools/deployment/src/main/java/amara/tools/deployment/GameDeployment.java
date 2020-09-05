package amara.tools.deployment;

import amara.applications.master.client.launcher.api.WebserverInfo;
import com.jcraft.jsch.*;

import java.io.IOException;

/**
 * @author Carl
 */
public class GameDeployment {

    public static void main(String[] args) throws IOException, JSchException, SftpException {
        SFTPClient sftpClient = new SFTPClient(WebserverInfo.HOST, 22, "root", "4mhcSxAaMP2XpYe");
        try {
            sftpClient.connect();
            sftpClient.setIgnoredDirectoryNames("animations");
            sftpClient.setIgnoredFileEndings(".blend",".mesh.xml",".skeleton.xml",".pdn");
            // Server
            sftpClient.setTargetDirectory("/root/amara");
            sftpClient.upload("../server/master-server-application/target/master-server-application-0.7-jar-with-dependencies.jar", "amara.jar");
            // Client Update
            sftpClient.setTargetDirectory("/var/www/destrostudios/apps/Amara");
            sftpClient.upload("../client/master-client-application/target/master-client-application-0.7.jar", "Amara.jar");
            sftpClient.upload("../client/master-client-application/target/libs");
            sftpClient.upload("../assets");
        } finally {
            sftpClient.disconnect();
        }
    }
}
