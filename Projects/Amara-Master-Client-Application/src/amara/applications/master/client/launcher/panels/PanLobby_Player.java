/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.event.ItemEvent;
import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;
import amara.applications.ingame.shared.maps.*;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.client.launcher.comboboxes.*;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class PanLobby_Player extends javax.swing.JPanel{

    public PanLobby_Player(PanLobby panLobby, LobbyPlayer lobbyPlayer){
        initComponents();
        this.panLobby = panLobby;
        this.lobbyPlayer = lobbyPlayer;
        PlayerProfileData playerProfileData = MasterserverClientUtil.getPlayerProfile(lobbyPlayer.getID());
        String avatarFilePath = PanAvatarSelection.getAvatarFilePath(playerProfileData.getMeta("avatar"));
        lblIcon.setIcon(FileAssets.getImageIcon(avatarFilePath, 30, 30));
        lblName.setText(playerProfileData.getLogin());
        int characterID = lobbyPlayer.getPlayerData().getCharacterID();
        boolean isOwnPlayer = (MasterserverClientUtil.getPlayerID() == lobbyPlayer.getID());
        if(isOwnPlayer){
            OwnedGameCharacter[] ownedCharacters = MasterserverClientUtil.getOwnedCharacters();
            cbxCharacter.setModel(new ComboboxModel_OwnedCharacters(MasterserverClientUtil.getOwnedCharacters()));
            for(int i=0;i<ownedCharacters.length;i++){
                if(ownedCharacters[i].getCharacter().getID() == characterID){
                    cbxCharacter.setSelectedIndex(i);
                    break;
                }
            }
        }
        else{
            GameCharacter character = MasterserverClientUtil.getCharacter(characterID);
            cbxCharacter.setModel(new DefaultComboBoxModel(new String[]{ComboboxModel_OwnedCharacters.getItemTitle(character)}));
        }
        cbxCharacter.setEnabled(isOwnPlayer);
        btnKick.setEnabled(panLobby.isOwner() && (!isOwnPlayer));
        //MapSpells
        cbxMapSpells = new JComboBox[]{cbxMapSpell1, cbxMapSpell2};
        for(JComboBox cbxMapSpell : cbxMapSpells){
            cbxMapSpell.setEnabled(false);
            cbxMapSpell.setUI(new BasicComboBoxUI(){
                
                @Override
                protected JButton createArrowButton(){
                    return new JButton(){

                        @Override
                        public int getWidth(){
                            return 0;
                        }
                    };
                }
            });
        }
        MapSpells[] mapSpells = panLobby.getMap().getSpells();
        int mapSpellsIndex = 0;
        for(int i=0;i<mapSpells.length;i++){
            if(mapSpells[i].getMapSpells().length > 1){
                selectableMapSpellsIndices.add(new int[]{i, 0});
                initializeMapSpellsCombobox(mapSpellsIndex, mapSpells[i].getMapSpells());
                mapSpellsIndex++;
                if(mapSpells[i].getKeys().length > 1){
                    selectableMapSpellsIndices.add(new int[]{i, 1});
                    initializeMapSpellsCombobox(mapSpellsIndex, mapSpells[i].getMapSpells());
                    break;
                }
            }
        }
        setSize(300, (selectableMapSpellsIndices.isEmpty()?30:86));
        if(sendUpdateAfterInitializing){
            sendLobbyPlayerUpdate();
        }
        else{
            reactToChanges = true;
        }
    }
    private PanLobby panLobby;
    private LobbyPlayer lobbyPlayer;
    private JComboBox[] cbxMapSpells;
    private LinkedList<int[]> selectableMapSpellsIndices = new LinkedList<int[]>();
    private boolean sendUpdateAfterInitializing;
    private boolean reactToChanges;
   
    private void initializeMapSpellsCombobox(int mapSpellsIndex, MapSpell[] mapSpells){
        cbxMapSpells[mapSpellsIndex].setEnabled(true);
        cbxMapSpells[mapSpellsIndex].setModel(new ComboboxModel_MapSpells(mapSpells));
        int[][] mapSpellsIndices = lobbyPlayer.getPlayerData().getMapSpellsIndices();
        if(mapSpellsIndices != null){
            int mapSpellIndex = mapSpellsIndices[selectableMapSpellsIndices.get(mapSpellsIndex)[0]][selectableMapSpellsIndices.get(mapSpellsIndex)[1]];
            cbxMapSpells[mapSpellsIndex].setSelectedIndex(mapSpellIndex);
        }
        else{
            sendUpdateAfterInitializing = true;
        }
    }
    
    private void sendLobbyPlayerUpdate(){
        int characterID = getCharacterID();
        int[][] mapSpellsIndices = getMapSpellsIndices();
        panLobby.sendMessage(new Message_SetLobbyPlayerData(new LobbyPlayerData(characterID, mapSpellsIndices)));
    }
    
    private int getCharacterID(){
        OwnedGameCharacter ownedCharacter = MasterserverClientUtil.getOwnedCharacters()[cbxCharacter.getSelectedIndex()];
        return ownedCharacter.getCharacter().getID();
    }
    
    private int[][] getMapSpellsIndices(){
        MapSpells[] mapSpells = panLobby.getMap().getSpells();
        int[][] mapSpellsIndices = new int[mapSpells.length][];
        for(int i=0;i<mapSpells.length;i++){
            mapSpellsIndices[i] = new int[mapSpells[i].getKeys().length];
        }
        for(int i=0;i<selectableMapSpellsIndices.size();i++){
            mapSpellsIndices[selectableMapSpellsIndices.get(i)[0]][selectableMapSpellsIndices.get(i)[1]] = cbxMapSpells[i].getSelectedIndex();
        }
        return mapSpellsIndices;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblSeparator1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        cbxCharacter = new javax.swing.JComboBox();
        btnKick = new javax.swing.JButton();
        cbxMapSpell1 = new javax.swing.JComboBox();
        cbxMapSpell2 = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(30, 30, 30));

        lblIcon.setPreferredSize(new java.awt.Dimension(30, 30));

        lblSeparator1.setPreferredSize(new java.awt.Dimension(5, 30));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("???");
        lblName.setPreferredSize(new java.awt.Dimension(115, 30));

        cbxCharacter.setPreferredSize(new java.awt.Dimension(110, 30));
        cbxCharacter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCharacterItemStateChanged(evt);
            }
        });

        btnKick.setText("X");
        btnKick.setPreferredSize(new java.awt.Dimension(40, 30));
        btnKick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKickActionPerformed(evt);
            }
        });

        cbxMapSpell1.setPreferredSize(new java.awt.Dimension(110, 30));
        cbxMapSpell1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMapSpell1ItemStateChanged(evt);
            }
        });

        cbxMapSpell2.setPreferredSize(new java.awt.Dimension(110, 30));
        cbxMapSpell2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMapSpell2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxMapSpell1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMapSpell2, 0, 1, Short.MAX_VALUE))
                    .addComponent(cbxCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(btnKick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMapSpell1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMapSpell2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCharacterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCharacterItemStateChanged
        if(reactToChanges && (evt.getStateChange() == ItemEvent.SELECTED)){
            sendLobbyPlayerUpdate();
        }
    }//GEN-LAST:event_cbxCharacterItemStateChanged

    private void btnKickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKickActionPerformed
        panLobby.sendMessage(new Message_KickLobbyPlayer(lobbyPlayer.getID()));
    }//GEN-LAST:event_btnKickActionPerformed

    private void cbxMapSpell1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMapSpell1ItemStateChanged
        if(reactToChanges && (evt.getStateChange() == ItemEvent.SELECTED)){
            sendLobbyPlayerUpdate();
        }
    }//GEN-LAST:event_cbxMapSpell1ItemStateChanged

    private void cbxMapSpell2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMapSpell2ItemStateChanged
        if(reactToChanges && (evt.getStateChange() == ItemEvent.SELECTED)){
            sendLobbyPlayerUpdate();
        }
    }//GEN-LAST:event_cbxMapSpell2ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKick;
    private javax.swing.JComboBox cbxCharacter;
    private javax.swing.JComboBox cbxMapSpell1;
    private javax.swing.JComboBox cbxMapSpell2;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSeparator1;
    // End of variables declaration//GEN-END:variables
}
