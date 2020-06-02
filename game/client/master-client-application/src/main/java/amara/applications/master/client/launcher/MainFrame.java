/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 02.08.2012, 23:50:37
 */
package amara.applications.master.client.launcher;

import java.awt.*;
import javax.swing.*;

import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.client.appstates.CharactersAppState;
import amara.applications.master.client.appstates.LoginAppState;
import amara.applications.master.client.launcher.panels.*;
import amara.applications.master.client.launcher.panels.connectscreens.ConnectScreen_Shina;
import amara.applications.master.network.messages.Message_GetGameContents;
import amara.applications.master.network.messages.Message_Login;
import amara.applications.master.server.launcher.Launcher_Game;
import amara.core.Launcher_Core;
import amara.core.Util;
import amara.core.settings.Settings;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.HostInformation;
import amara.libraries.network.NetworkClient;
import amara.libraries.network.exceptions.ServerConnectionException;
import amara.libraries.network.exceptions.ServerConnectionTimeoutException;

/**
 *
 * @author Carl
 */
public class MainFrame extends JFrame {

    public static void main(String[] args) {
        String authToken = args[0];
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        EventQueue.invokeLater(() -> new MainFrame(authToken).setVisible(true));
    }

    public MainFrame(String authToken) {
        this.authToken = authToken;
        initComponents();
        instance = this;
        panConnect = new PanConnect(this, new ConnectScreen_Shina());
        setSize(1000, 600);
        setDisplayedPanel(panConnect);
        panConnect.start();
        FrameUtil.initFrameSpecials(this);
        FrameUtil.centerFrame(this);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(-1, 0, 0, 0));
        connect();
    }
    private static MainFrame instance;
    private String authToken;
    private PanConnect panConnect;
    private MasterserverClientApplication masterClient;

    public void setDisplayedPanel(JPanel panel){
        panContainer.removeAll();
        panContainer.add(panel);
        panContainer.updateUI();
    }

    private void connect() {
        new Thread(() -> {
            try {
                masterClient = new MasterserverClientApplication(new HostInformation(Settings.get("server_game_host"), Settings.getInteger("server_game_port")));
                masterClient.start();
                NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
                networkClient.sendMessage(new Message_Login(authToken));
                LoginAppState loginAppState = masterClient.getStateManager().getState(LoginAppState.class);
                loginAppState.onLoginPending();
                LoginAppState.LoginResult loginResult;
                while (true) {
                    if (loginAppState.getResult() != LoginAppState.LoginResult.PENDING) {
                        loginResult = loginAppState.getResult();
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch(Exception ex) {
                    }
                }
                switch(loginResult){
                    case FAILED:
                        panConnect.setInfoLabel("Authentication failed.");
                        break;

                    case SUCCESSFUL:
                        panConnect.setInfoLabel("Retrieving ingame content...");
                        networkClient.sendMessage(new Message_GetGameContents());
                        CharactersAppState charactersAppState = masterClient.getStateManager().getState(CharactersAppState.class);
                        while (true) {
                            if (charactersAppState.getCharacters() != null) {
                                break;
                            }
                            Util.sleep(100);
                        }
                        panConnect.setInfoLabel("");
                        panConnect.onConnected();
                        break;
                }
            } catch (ServerConnectionException | ServerConnectionTimeoutException ex) {
                panConnect.setInfoLabel("Seems like there is a problem with our servers :(");
            }
        }).start();
    }

    public void openMainMenu() {
        panConnect.close();
        setDisplayedPanel(new PanMainMenu());
    }

    public static MainFrame getInstance() {
        return instance;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panContainer.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panContainer;
    // End of variables declaration//GEN-END:variables
}
