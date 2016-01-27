/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanLobby.java
 *
 * Created on 02.08.2012, 23:56:34
 */
package amara.applications.master.client.launcher.panels;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import com.jme3.network.Message;
import amara.applications.ingame.shared.maps.*;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.files.FileAssets;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PanLobby extends javax.swing.JPanel{

    public PanLobby(PanPlay panPlay){
        initComponents();
        this.panPlay = panPlay;
        lblMapIcon.setIcon(FileAssets.getImageIcon("Interface/client/unknown.jpg", 120, 120));
    }
    private PanPlay panPlay;
    private Lobby lobby;
    private boolean isOwner;
    private Map map;
    
    public void update(Lobby lobby){
        this.lobby = lobby;
        isOwner = (MasterserverClientUtil.getPlayerID() == lobby.getOwnerID());
        btnInvite.setEnabled(isOwner);
        btnStart.setEnabled(isOwner);
        cbxMapName.setEnabled(isOwner);
        String mapName = lobby.getLobbyData().getMapName();
        map = MapFileHandler.load(mapName, false);
        cbxMapName.setSelectedItem(mapName);
        lblMapIcon.setIcon(FileAssets.getImageIcon("Maps/" + mapName + "/icon.png", 120, 120));
        updatePlayersList(lobby.getPlayers());
    }
    
    private void updatePlayersList(ArrayList<LobbyPlayer> players){
        panPlayers.removeAll();
        int y = 0;
        for(int i=0;i<players.size();i++){
            PanLobby_Player panPlay_Player = new PanLobby_Player(this, players.get(i));
            panPlay_Player.setLocation(0, y);
            int panelHeight = (panPlay_Player.getSelectableMapSpellsIndices().isEmpty()?30:86);
            panPlay_Player.setSize(300, panelHeight);
            panPlayers.add(panPlay_Player);
            y += panelHeight;
        }
        panPlayers.updateUI();
    }
    
    public void sendMessage(Message message){
        NetworkClient networkClient = MasterserverClientUtil.getNetworkClient();
        networkClient.sendMessage(message);
    }

    public boolean isOwner(){
        return isOwner;
    }

    public Map getMap(){
        return map;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrHostOrConnect = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMapIcon = new javax.swing.JLabel();
        panPlayers = new javax.swing.JPanel();
        btnInvite = new javax.swing.JButton();
        cbxMapName = new javax.swing.JComboBox();
        btnLeave = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setBackground(new java.awt.Color(30, 30, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Map:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Players:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Options:");

        lblMapIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(65, 65, 65)));

        panPlayers.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panPlayersLayout = new javax.swing.GroupLayout(panPlayers);
        panPlayers.setLayout(panPlayersLayout);
        panPlayersLayout.setHorizontalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panPlayersLayout.setVerticalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        btnInvite.setText("Invite");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        cbxMapName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "testmap", "destroforest", "etherdesert", "arama", "techtest" }));
        cbxMapName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMapNameItemStateChanged(evt);
            }
        });

        btnLeave.setText("Leave");
        btnLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxMapName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMapIcon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInvite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLeave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInvite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMapIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxMapName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 96, Short.MAX_VALUE))
                            .addComponent(panPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        String login = FrameUtil.showInputDialog(this, "Enter login:");
        if((login != null) && (!login.isEmpty())){
            PlayerProfileData playerProfileData = MasterserverClientUtil.getPlayerProfile(login);
            if(playerProfileData != null){
                int playerID = playerProfileData.getID();
                if(!lobby.containsPlayer(playerID)){
                    PlayerStatus playerStatus = MasterserverClientUtil.getPlayerStatus(playerProfileData.getID());
                    if(playerStatus == PlayerStatus.ONLINE){
                        sendMessage(new Message_InviteLobbyPlayer(playerID));
                    }
                    else{
                        FrameUtil.showMessageDialog(this, "'" + login + "' is not available for a game.\nStatus: " + playerStatus, FrameUtil.MessageType.WARNING);
                    }
                }
                else{
                    FrameUtil.showMessageDialog(this, "'" + login + "' is already participating.", FrameUtil.MessageType.WARNING);
                }
            }
            else{
                FrameUtil.showMessageDialog(this, "Player '" + login + "' wasn't found.", FrameUtil.MessageType.ERROR);
            }
        }
    }//GEN-LAST:event_btnInviteActionPerformed

    private void btnLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveActionPerformed
        sendMessage(new Message_LeaveLobby());
        panPlay.displayCreatePanel();
    }//GEN-LAST:event_btnLeaveActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        sendMessage(new Message_StartGame());
    }//GEN-LAST:event_btnStartActionPerformed

    private void cbxMapNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMapNameItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String mapName = cbxMapName.getSelectedItem().toString();
            if(!mapName.equals(lobby.getLobbyData().getMapName())){
                sendMessage(new Message_SetLobbyData(new LobbyData(mapName)));
            }
        }
    }//GEN-LAST:event_cbxMapNameItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrHostOrConnect;
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnLeave;
    private javax.swing.JButton btnStart;
    private javax.swing.JComboBox cbxMapName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblMapIcon;
    private javax.swing.JPanel panPlayers;
    // End of variables declaration//GEN-END:variables
}
