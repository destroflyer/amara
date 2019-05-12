package amara.tools.deployment;

import amara.core.files.FileManager;
import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

public class SFTPClient {

    public SFTPClient(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    private String host;
    private int port;
    private String user;
    private String password;
    private Session session;
    private String targetDirectory;
    private String[] ignoredDirectoryNames;
    private String[] ignoredFileEndings;
    private ChannelSftp channelSftp;
    private ChannelExec channelExec;

    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(this.user, this.host, this.port);
        session.setPassword(this.password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.connect();
    }

    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
        if (channelSftp != null) {
            channelSftp.disconnect();
        }
        if (channelExec != null) {
            channelExec.disconnect();
        }
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setIgnoredDirectoryNames(String... ignoredDirectoryNames) {
        this.ignoredDirectoryNames = ignoredDirectoryNames;
    }

    public void setIgnoredFileEndings(String... ignoredFileEndings) {
        this.ignoredFileEndings = ignoredFileEndings;
    }

    public void upload(String filePath) throws IOException, JSchException, SftpException {
        File localFile = new File(filePath);
        upload(localFile, localFile.getName());
    }

    public void upload(String localFilePath, String targetFilePath) throws IOException, JSchException, SftpException {
        upload( new File(localFilePath), targetFilePath);
    }

    private void upload(File localFile, String targetFilePath) throws IOException, JSchException, SftpException {
        if (isIgnoredFile(localFile)) {
            System.out.println("Ignoring: " + getRemoteFilePath(targetFilePath));
        } else {
            if (localFile.isDirectory()) {
                createDirectoryIfNotExists(targetFilePath);
                File[] directoryFiles = localFile.listFiles();
                for (File directoryFile : directoryFiles) {
                    upload(directoryFile, targetFilePath + "/" + directoryFile.getName());
                }
            } else {
                if (hasSameChecksum(localFile, targetFilePath)) {
                    System.out.println("Already up to date: " + getRemoteFilePath(targetFilePath));
                } else {
                    writeFile(targetFilePath, new FileInputStream(localFile));
                }
            }
        }
    }

    private boolean isIgnoredFile(File localFile) {
        if (localFile.isDirectory()) {
            if (ignoredDirectoryNames != null) {
                for (String ignoredDirectoryName : ignoredDirectoryNames) {
                    if (localFile.getName().equals(ignoredDirectoryName)) {
                        return true;
                    }
                }
            }
        } else {
            if (ignoredFileEndings != null) {
                for (String ignoredFileEnding : ignoredFileEndings) {
                    if (localFile.getName().endsWith(ignoredFileEnding)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void createDirectoryIfNotExists(String filePath) throws SftpException {
        String remoteFilePath = getRemoteFilePath(filePath);
        try {
            channelSftp.stat(remoteFilePath);
        }catch (SftpException ex) {
            channelSftp.mkdir(remoteFilePath);
        }
    }

    private boolean hasSameChecksum(File localFile, String targetFilePath) throws IOException, JSchException {
        String checksumLocal = FileManager.getFileChecksum_MD5(localFile);
        String checksumRemote = getChecksumMd5(targetFilePath);
        return checksumLocal.equals(checksumRemote);
    }

    private String getChecksumMd5(String filePath) throws IOException, JSchException {
        String output = executeCommand("md5sum " + getRemoteFilePath(filePath));
        if (output.startsWith("md5sum: ")) {
            return null;
        }
        return output.substring(0, output.indexOf(' '));
    }

    public String executeCommand(String command) throws IOException, JSchException {
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        StringBuilder outputBuffer = new StringBuilder();
        StringBuilder errorBuffer = new StringBuilder();
        InputStream in = channel.getInputStream();
        InputStream err = channel.getExtInputStream();
        channel.connect();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                outputBuffer.append(new String(tmp, 0, i));
            }
            while (err.available() > 0) {
                int i = err.read(tmp, 0, 1024);
                if (i < 0) break;
                errorBuffer.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if ((in.available() > 0) || (err.available() > 0)) {
                    continue;
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ee) {
            }
        }
        String error = errorBuffer.toString();
        String output = outputBuffer.toString();;
        String result = ((error.length() > 0) ? error : output);
        channel.disconnect();
        return result;
    }

    public void writeFile(String filePath, String content) throws SftpException {
        writeFile(filePath, new ByteArrayInputStream(content.getBytes()));
    }

    public void writeFile(String filePath, InputStream inputStream) throws SftpException {
        String remoteFilePath = getRemoteFilePath(filePath);
        System.out.println("Uploading: " + remoteFilePath);
        channelSftp.put(inputStream, remoteFilePath);
    }

    private String getRemoteFilePath(String targetFilePath) {
        return targetDirectory + "/" + targetFilePath;
    }
}
