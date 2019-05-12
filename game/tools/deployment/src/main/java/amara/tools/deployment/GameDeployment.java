package amara.tools.deployment;

import amara.applications.master.client.launcher.api.WebserverInfo;
import com.jcraft.jsch.*;

import java.io.IOException;

/**
 * @author Carl
 */
public class GameDeployment {

    public static void main(String[] args) throws IOException, JSchException, SftpException {
        SFTPClient sftpClient = new SFTPClient(WebserverInfo.HOST, 22, "USER", "PASSWORD");
        String serverDirectory = "/root/amara";
        try {
            sftpClient.connect();
            sftpClient.setIgnoredDirectoryNames("animations");
            sftpClient.setIgnoredFileEndings(".blend",".mesh.xml",".skeleton.xml");
            sftpClient.setTargetDirectory(serverDirectory);
            // Server
            sftpClient.upload("../dist/AmaraServer.jar");
            sftpClient.upload("../files/server/AmaraServer.sh");
            sftpClient.upload("../files/server/assets.ini");
            sftpClient.upload("../files/server/update.ini");
            sftpClient.upload("../files/server/key_to_the_city.ini");
            // Client Update
            sftpClient.upload("../dist/Amara.jar", "update/Amara.jar");
            sftpClient.upload("../files/client/Amara.bat", "update/Amara.bat");
            // sftpClient.upload("../assets", "update/assets");
            // Client Download
            sftpClient.upload("../files/server/create_downloadable_client.sh");
            System.out.println("Creating downloadable client...");
            String downloadableClientCreation = sftpClient.executeCommand("sh " + serverDirectory + "/create_downloadable_client.sh");
            System.out.println(downloadableClientCreation);
        } finally {
            sftpClient.disconnect();
        }
    }
}
