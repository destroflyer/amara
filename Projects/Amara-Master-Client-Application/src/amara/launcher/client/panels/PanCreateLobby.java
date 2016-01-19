/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import amara.applications.master.network.messages.Message_CreateLobby;
import amara.applications.master.network.messages.objects.LobbyData;
import amara.engine.applications.masterserver.client.MasterserverClientUtil;
import amara.launcher.client.buttons.*;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PanCreateLobby extends javax.swing.JPanel{

    public PanCreateLobby(){
        initComponents();
        JComponent btnPlay = ButtonUtil.addImageBackgroundButton(panContainer_btnCreateLobby, new DefaultButtonBuilder("default_150x50", "Create Lobby"));
        btnPlay.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt){
                super.mouseClicked(evt);
                createLobby();
            }
        });
    }
    
    private void createLobby(){
        NetworkClient networkClient = MasterserverClientUtil.getNetworkClient();
        networkClient.sendMessage(new Message_CreateLobby(new LobbyData("testmap")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panContainer_btnCreateLobby = new javax.swing.JPanel();

        setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panContainer_btnCreateLobbyLayout = new javax.swing.GroupLayout(panContainer_btnCreateLobby);
        panContainer_btnCreateLobby.setLayout(panContainer_btnCreateLobbyLayout);
        panContainer_btnCreateLobbyLayout.setHorizontalGroup(
            panContainer_btnCreateLobbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panContainer_btnCreateLobbyLayout.setVerticalGroup(
            panContainer_btnCreateLobbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panContainer_btnCreateLobby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(484, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panContainer_btnCreateLobby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panContainer_btnCreateLobby;
    // End of variables declaration//GEN-END:variables
}
