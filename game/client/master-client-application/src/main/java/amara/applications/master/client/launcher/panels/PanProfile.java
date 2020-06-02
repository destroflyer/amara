/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanProfile.java
 *
 * Created on 02.08.2012, 23:56:34
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.client.launcher.comboboxes.*;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.files.FileAssets;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PanProfile extends javax.swing.JPanel{

    public PanProfile(){
        initComponents();
        btnAvatar.setIcon(FileAssets.getImageIcon("Interface/client/unknown.jpg", 100, 100));
        lblSearchLoader.setIcon(FileAssets.getImageIcon("Interface/client/loaders/user_search.gif"));
        lblSearchLoader.setVisible(false);
        cbxCharacters.setModel(new ComboboxModel_OwnedCharacters(MasterserverClientUtil.getOwnedCharacters()));
        updateSelectedCharacterSkins();
    }
    private boolean isOwnProfile;
    private boolean isSearchLoginFieldEmpty = true;
    private PanAvatarSelection panAvatarSelection;
    
    public void onTabSelected(){
        txtSearchLogin.setText("");
        txtSearchLoginFocusLost(null);
        resetToOwnProfile();
    }
    
    public void resetToOwnProfile(){
        loadPlayerProfile(MasterserverClientUtil.getPlayerId());
    }
    
    private void loadPlayerProfile(int playerID){
        loadPlayerProfile(playerID, null);
    }
    
    private void loadPlayerProfile(String login){
        loadPlayerProfile(0, login);
    }
    
    private void loadPlayerProfile(final int playerID, final String login){
        new Thread(new Runnable(){

            @Override
            public void run(){
                lblSearchLoader.setVisible(true);
                PlayerProfileData player;
                if(playerID != 0){
                    player = MasterserverClientUtil.getPlayerProfile(playerID);
                }
                else{
                    player = MasterserverClientUtil.getPlayerProfile(login);
                }
                lblSearchLoader.setVisible(false);
                if(player != null){
                    showPlayerProfile(player);
                }
                else{
                    FrameUtil.showMessageDialog(PanProfile.this, "Player '" + login + "' wasn't found.", FrameUtil.MessageType.ERROR);
                }
            }
        }).start();
    }
    
    private void showPlayerProfile(PlayerProfileData player){
        lblProfileTitle.setText("Profile of " + player.getLogin());
        String profileText = player.getMeta("profile_text");
        lblProfileText.setText(profileText.isEmpty()?"<html><i>No profile text existing.</i></html>":profileText);
        setAvatarIcon(player.getMeta("avatar"));
        lblUserData_ID.setText("" + player.getId());
        //lblUserData_RegistrationDate.setText(Util.getFormattedDate(player.getRegistrationDate()));
        isOwnProfile = (player.getId() == MasterserverClientUtil.getPlayerId());
        if(isOwnProfile){
            btnAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            //lblUserData_EMail.setText(player.getEMail());
        }
        else{
            btnAvatar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            lblUserData_EMail.setText("?");
        }
    }
    
    public void changeAvatar(String avatar){
        NetworkClient networkClient = MasterserverClientUtil.getNetworkClient();
        networkClient.sendMessage(new Message_EditUserMeta("avatar", avatar));
        setAvatarIcon(avatar);
        panAvatarSelection.setVisible(false);
        txtSearchLogin.setEnabled(true);
    }
    
    private void setAvatarIcon(String avatar){
        btnAvatar.setIcon(FileAssets.getImageIcon(PanAvatarSelection.getAvatarFilePath(avatar), btnAvatar.getWidth(), btnAvatar.getHeight()));
    }
    
    private void updateSelectedCharacterSkins(){
        OwnedGameCharacter ownedCharacter = getSelectedOwnedCharacter();
        GameCharacterSkin[] skins = ownedCharacter.getCharacter().getSkins();
        cbxSkins.setModel(new ComboboxModel_CharacterSkins(skins));
        for(int i=0;i<skins.length;i++){
            if(skins[i].getID() == ownedCharacter.getActiveSkinID()){
                cbxSkins.setSelectedIndex(i + 1);
                break;
            }
        }
    }
    
    private OwnedGameCharacter getSelectedOwnedCharacter(){
        return MasterserverClientUtil.getOwnedCharacters()[cbxCharacters.getSelectedIndex()];
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAvatar = new javax.swing.JButton();
        lblProfileTitle = new javax.swing.JLabel();
        lblProfileText = new javax.swing.JLabel();
        panUserData = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblUserData_ID = new javax.swing.JLabel();
        lblUserData_EMail = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUserData_RegistrationDate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSearchLoader = new javax.swing.JLabel();
        txtSearchLogin = new javax.swing.JTextField();
        cbxCharacters = new javax.swing.JComboBox();
        lblProfileTitle1 = new javax.swing.JLabel();
        cbxSkins = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(30, 30, 30));

        btnAvatar.setContentAreaFilled(false);
        btnAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAvatarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAvatarMouseExited(evt);
            }
        });
        btnAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvatarActionPerformed(evt);
            }
        });

        lblProfileTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblProfileTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblProfileTitle.setText("Profile of ?");

        lblProfileText.setForeground(new java.awt.Color(255, 255, 255));
        lblProfileText.setText("<html>Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage, Sausage.</html>");
        lblProfileText.setToolTipText("");
        lblProfileText.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        panUserData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.white));
        panUserData.setOpaque(false);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID:");

        lblUserData_ID.setForeground(new java.awt.Color(255, 255, 255));
        lblUserData_ID.setText("?");

        lblUserData_EMail.setForeground(new java.awt.Color(255, 255, 255));
        lblUserData_EMail.setText("?");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("E-Mail:");

        lblUserData_RegistrationDate.setForeground(new java.awt.Color(255, 255, 255));
        lblUserData_RegistrationDate.setText("?");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Regist.:");

        javax.swing.GroupLayout panUserDataLayout = new javax.swing.GroupLayout(panUserData);
        panUserData.setLayout(panUserDataLayout);
        panUserDataLayout.setHorizontalGroup(
            panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUserDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panUserDataLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUserData_ID, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panUserDataLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUserData_EMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panUserDataLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUserData_RegistrationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panUserDataLayout.setVerticalGroup(
            panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUserDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserData_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserData_EMail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserData_RegistrationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearchLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearchLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchLoginFocusLost(evt);
            }
        });
        txtSearchLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchLoginKeyPressed(evt);
            }
        });

        cbxCharacters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCharactersItemStateChanged(evt);
            }
        });

        lblProfileTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblProfileTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblProfileTitle1.setText("Character skins");

        cbxSkins.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSkinsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProfileText, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblProfileTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblSearchLoader, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearchLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panUserData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProfileTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSkins, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblProfileTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProfileText, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProfileTitle1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxSkins, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(cbxCharacters))
                        .addGap(260, 260, 260))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSearchLoader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSearchLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panUserData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchLoginFocusGained
        if(isSearchLoginFieldEmpty){
            txtSearchLogin.setText("");
            txtSearchLogin.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtSearchLoginFocusGained

    private void txtSearchLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchLoginFocusLost
        isSearchLoginFieldEmpty = txtSearchLogin.getText().isEmpty();
        if(isSearchLoginFieldEmpty){
            txtSearchLogin.setText("Search Player");
            txtSearchLogin.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtSearchLoginFocusLost

    private void txtSearchLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchLoginKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String login = txtSearchLogin.getText();
            if(login.isEmpty()){
                resetToOwnProfile();
            }
            else{
                loadPlayerProfile(login);
            }
        }
    }//GEN-LAST:event_txtSearchLoginKeyPressed

    private void btnAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvatarActionPerformed
        if(isOwnProfile){
            if(panAvatarSelection == null){
                panAvatarSelection = new PanAvatarSelection(this);
                panAvatarSelection.setLocation(btnAvatar.getX(), btnAvatar.getY());
                panAvatarSelection.setSize(352, 212);
                add(panAvatarSelection, 0);
            }
            btnAvatar.setIcon(null);
            txtSearchLogin.setEnabled(false);
            panAvatarSelection.setVisible(true);
            updateUI();
        }
    }//GEN-LAST:event_btnAvatarActionPerformed

    private void btnAvatarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvatarMouseEntered
        if(isOwnProfile){
            btnAvatar.setBorder(new LineBorder(Color.WHITE));
        }
    }//GEN-LAST:event_btnAvatarMouseEntered

    private void btnAvatarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvatarMouseExited
        if(isOwnProfile){
            btnAvatar.setBorder(null);
        }
    }//GEN-LAST:event_btnAvatarMouseExited

    private void cbxCharactersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCharactersItemStateChanged
        updateSelectedCharacterSkins();
    }//GEN-LAST:event_cbxCharactersItemStateChanged

    private void cbxSkinsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSkinsItemStateChanged
        //Deactivated for now, since changing the active character skin is possible in the game selection
        /*if(evt.getStateChange() == ItemEvent.SELECTED){
            OwnedGameCharacter ownedCharacter = getSelectedOwnedCharacter();
            GameCharacter character = ownedCharacter.getCharacter();
            int skinID = 0;
            if(cbxSkins.getSelectedIndex() != 0){
                skinID = character.getSkins()[cbxSkins.getSelectedIndex() - 1].getID();
            }
            MasterserverClientUtil.getNetworkClient().sendMessage(new Message_EditActiveCharacterSkin(character.getID(), skinID));
            ownedCharacter.setActiveSkinID(skinID);
        }*/
    }//GEN-LAST:event_cbxSkinsItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvatar;
    private javax.swing.JComboBox cbxCharacters;
    private javax.swing.JComboBox cbxSkins;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblProfileText;
    private javax.swing.JLabel lblProfileTitle;
    private javax.swing.JLabel lblProfileTitle1;
    private javax.swing.JLabel lblSearchLoader;
    private javax.swing.JLabel lblUserData_EMail;
    private javax.swing.JLabel lblUserData_ID;
    private javax.swing.JLabel lblUserData_RegistrationDate;
    private javax.swing.JPanel panUserData;
    private javax.swing.JTextField txtSearchLogin;
    // End of variables declaration//GEN-END:variables
}
