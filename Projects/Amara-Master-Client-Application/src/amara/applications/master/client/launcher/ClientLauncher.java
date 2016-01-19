/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.applications.master.client.launcher;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import javax.swing.JFrame;
import amara.applications.master.client.MasterserverClientApplication;
import amara.applications.master.client.launcher.api.objects.Masterserver;
import amara.applications.master.client.launcher.api.requests.GetMasterserversRequest;
import amara.applications.master.client.launcher.loginscreen.screens.LoginScreen_Forest;
import amara.applications.master.client.launcher.network.backends.*;
import amara.applications.master.client.launcher.panels.*;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.UpdateFile;
import amara.core.*;
import amara.core.files.FileManager;
import amara.applications.master.server.launcher.Launcher_Game;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.*;
import amara.libraries.network.exceptions.*;

/**
 *
 * @author Carl
 */
public class ClientLauncher extends JFrame{

    public ClientLauncher(){
        initComponents();
        PanLauncher panLauncher = new PanLauncher();
        panLauncher.setSize(panImage.getSize());
        panImage.add(panLauncher);
        Toolkit.getDefaultToolkit().addAWTEventListener(keyListener, AWTEvent.KEY_EVENT_MASK);
        getContentPane().requestFocus();
        FrameUtil.initFrameSpecials(this);
        FrameUtil.centerFrame(this);
        loadMasterservers();
        connectToMasterserver();
    }
    private MasterserverClientApplication masterClient;
    private boolean wasUpdateNeeded;
    private PanLogin forcedLoginPanel;
    private AWTEventListener keyListener = new AWTEventListener(){

        @Override
        public void eventDispatched(AWTEvent event){
            KeyEvent keyEvent = (KeyEvent) event;
            if(keyEvent.getID() == KeyEvent.KEY_RELEASED){
                switch(keyEvent.getKeyCode()){
                    case KeyEvent.VK_NUMPAD1:
                        forcedLoginPanel = new PanLogin_JME(new LoginScreen_Forest());
                        break;
                }
            }
        }
    };
    
    private void loadMasterservers(){
        cbxMasterserverHost.removeAllItems();
        GetMasterserversRequest request = new GetMasterserversRequest();
        request.send();
        if(request.getMasterservers().length > 0){
            Masterserver masterserver = request.getMasterservers()[0];
            cbxMasterserverHost.addItem(masterserver.getIP());
            txtMasterserverPort.setText("" + masterserver.getPort());
        }
    }
    
    private void connectToMasterserver(){
        btnPlay.setEnabled(false);
        if((cbxMasterserverHost.getSelectedItem() != null) && (!txtMasterserverPort.getText().isEmpty())){
            new Thread(new Runnable(){

                @Override
                public void run(){
                    pbrCompleteUpdate.setString("Connecting to masterserver...");
                    pbrCompleteUpdate.setIndeterminate(true);
                    pbrCompleteUpdate.setValue(0);
                    pbrCurrentFile.setValue(0);
                    cbxMasterserverHost.setEnabled(false);
                    txtMasterserverPort.setEnabled(false);
                    String host = cbxMasterserverHost.getSelectedItem().toString();
                    int port = Integer.parseInt(txtMasterserverPort.getText());
                    try{
                        if(masterClient != null){
                            NetworkClient networkClient = getNetworkClient();
                            networkClient.disconnect();
                        }
                        masterClient = new MasterserverClientApplication(new HostInformation(host, port));
                        masterClient.start();
                        NetworkClient networkClient = getNetworkClient();
                        networkClient.addMessageBackend(new UpdateFilesBackend(ClientLauncher.this));
                        networkClient.sendMessage(new Message_GetUpdateFiles());
                    }catch(ServerConnectionException ex){
                        onMasterserverOffline();
                    }catch(ServerConnectionTimeoutException ex){
                        onMasterserverOffline();
                    }
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run(){
                            pbrCompleteUpdate.setIndeterminate(false);
                        }
                    });
                }
            }).start();
        }
        else{
            onMasterserverOffline();
        }
    }
    
    private NetworkClient getNetworkClient(){
        return masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }
    
    private void onMasterserverOffline(){
        pbrCompleteUpdate.setString("Couldn't connect to masterserver");
        cbxMasterserverHost.setEnabled(true);
        txtMasterserverPort.setEnabled(true);
    }
    
    public void update(final LinkedList<UpdateFile> updateFiles){
        new Thread(new Runnable(){

            @Override
            public void run(){
                pbrCompleteUpdate.setMaximum(updateFiles.size());
                int i = 0;
                for(UpdateFile updateFile : updateFiles){
                    pbrCompleteUpdate.setString(updateFile.getFilePath());
                    updateFile(updateFile, i);
                    pbrCompleteUpdate.setValue(i + 1);
                    i++;
                }
                pbrCompleteUpdate.setString("The game is up to date.");
                pbrCurrentFile.setString("");
                if(wasUpdateNeeded){
                    pbrCompleteUpdate.setString("To activate the update, please restart the game.");
                    btnPlay.setText("Close");
                }
                btnPlay.setEnabled(true);
                cbxMasterserverHost.setEnabled(true);
                txtMasterserverPort.setEnabled(true);
            }
        }).start();
    }
    
    private void updateFile(UpdateFile updateFile, int index){
        File file = new File(updateFile.getFilePath());
        if(FileManager.isDirectory(file)){
            FileManager.createDirectoryIfNotExists(file.getPath());
        }
        else{
            boolean needsUpdate = true;
            if(file.exists()){
                String checksumLocalFile = VersionManager.getInstance().getFileChecksumMD5(updateFile.getFilePath());
                needsUpdate = (!checksumLocalFile.equals(updateFile.getChecksum_MD5()));
            }
            if(needsUpdate){
                pbrCurrentFile.setMaximum((int) updateFile.getSize());
                pbrCurrentFile.setString("Initializing download (" + (updateFile.getSize() / 100) + " kB)");
                NetworkClient networkClient = masterClient.getStateManager().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
                WriteUpdateFileBackend writeUpdateFileBackend = new WriteUpdateFileBackend(updateFile);
                networkClient.addMessageBackend(writeUpdateFileBackend);
                networkClient.sendMessage(new Message_GetUpdateFile(index));
                while(!writeUpdateFileBackend.isFinished()){
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException ex){
                    }
                    int downloadedBytes = (int) file.length();
                    pbrCurrentFile.setString(Util.getPercentage_Rounded(pbrCurrentFile.getMaximum(), downloadedBytes) + "%");
                    pbrCurrentFile.setValue(downloadedBytes);
                }
                networkClient.removeMessageBackend(writeUpdateFileBackend);
                VersionManager.getInstance().onFileUpdated(updateFile.getFilePath());
                wasUpdateNeeded = true;
            }
            else{
                pbrCurrentFile.setValue(pbrCurrentFile.getMaximum());
                pbrCurrentFile.setString("File up to date");
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panImage = new javax.swing.JPanel();
        cbxMasterserverHost = new javax.swing.JComboBox();
        txtMasterserverPort = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        pbrCompleteUpdate = new javax.swing.JProgressBar();
        btnPlay = new javax.swing.JButton();
        pbrCurrentFile = new javax.swing.JProgressBar();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        cbxMasterserverHost.setEditable(true);
        cbxMasterserverHost.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMasterserverHostItemStateChanged(evt);
            }
        });

        txtMasterserverPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMasterserverPortKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panImageLayout = new javax.swing.GroupLayout(panImage);
        panImage.setLayout(panImageLayout);
        panImageLayout.setHorizontalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panImageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxMasterserverHost, 0, 146, Short.MAX_VALUE)
                    .addComponent(txtMasterserverPort))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panImageLayout.setVerticalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panImageLayout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addComponent(cbxMasterserverHost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMasterserverPort, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pbrCompleteUpdate.setString("");
        pbrCompleteUpdate.setStringPainted(true);

        btnPlay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        pbrCurrentFile.setString("");
        pbrCurrentFile.setStringPainted(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbrCompleteUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(pbrCurrentFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pbrCompleteUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbrCurrentFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
    if(wasUpdateNeeded){
        System.exit(0);
    }
    else{
        Toolkit.getDefaultToolkit().removeAWTEventListener(keyListener);
        PanLogin panLogin = ((forcedLoginPanel != null)?forcedLoginPanel:new PanLogin_Swing());
        MainFrame mainFrame = new MainFrame(panLogin);
        setVisible(false);
        mainFrame.setVisible(true);
    }
}//GEN-LAST:event_btnPlayActionPerformed

    private void cbxMasterserverHostItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMasterserverHostItemStateChanged
        connectToMasterserver();
    }//GEN-LAST:event_cbxMasterserverHostItemStateChanged

    private void txtMasterserverPortKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMasterserverPortKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            connectToMasterserver();
        }
    }//GEN-LAST:event_txtMasterserverPortKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        Launcher_Core.initialize();
        Launcher_Game.initialize();
        java.awt.EventQueue.invokeLater(new Runnable(){

            @Override
            public void run(){
                new ClientLauncher().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JComboBox cbxMasterserverHost;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panImage;
    private javax.swing.JProgressBar pbrCompleteUpdate;
    private javax.swing.JProgressBar pbrCurrentFile;
    private javax.swing.JTextField txtMasterserverPort;
    // End of variables declaration//GEN-END:variables
}
