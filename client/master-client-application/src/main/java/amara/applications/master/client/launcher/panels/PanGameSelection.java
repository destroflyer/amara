/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanGameSelection.java
 *
 * Created on 19.03.2016, 00:52:57
 */
package amara.applications.master.client.launcher.panels;

import java.awt.event.ItemEvent;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.HashMap;
import com.jme3.network.Message;
import amara.applications.ingame.shared.maps.*;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.client.launcher.comboboxes.*;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PanGameSelection extends javax.swing.JPanel {

    public PanGameSelection() {
        initComponents();
        panCharacters = new PanGameSelection_Characters(this);
        tpaneCharacters.add("Characters", panCharacters);
        tpaneCharacters.add("Skins", new JPanel());
        tpaneCharacters.setEnabledAt(1, false);
    }
    private PanGameSelection_Characters panCharacters;
    private HashMap<OwnedGameCharacter, PanGameSelection_CharacterSkins> panCharactersSkins = new HashMap<>();
    private GameSelection gameSelection;
    private Map map;
    private JComboBox[] cbxMapSpells;
    private int selectedCharacterID;
    private LinkedList<int[]> selectableMapSpellsIndices = new LinkedList<>();
    private boolean isLockedIn;

    public void reset(){
        //Map
        String mapName = gameSelection.getGameSelectionData().getMapName();
        map = MapFileHandler.load(mapName, false);
        lblMapName.setText(map.getName());
        //Character
        panCharacters.resetScroll();
        OwnedGameCharacter defaultCharacter = MasterserverClientUtil.getOwnedCharacters()[0];
        setSelectedCharacter(defaultCharacter);
        //MapSpells
        cbxMapSpells = new JComboBox[]{cbxMapSpell1, cbxMapSpell2};
        for(JComboBox cbxMapSpell : cbxMapSpells){
            cbxMapSpell.setModel(new ComboboxModel_MapSpells(new MapSpell[0], 40));
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
        MapSpells[] mapSpells = map.getSpells();
        selectableMapSpellsIndices.clear();
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
        setLockedIn(false);
        sendGameSelectionPlayerDataUpdate();
    }
   
    private void initializeMapSpellsCombobox(int mapSpellsIndex, MapSpell[] mapSpells){
        cbxMapSpells[mapSpellsIndex].setEnabled(true);
        cbxMapSpells[mapSpellsIndex].setModel(new ComboboxModel_MapSpells(mapSpells, 40));
        cbxMapSpells[mapSpellsIndex].setRenderer(new ComboboxRenderer_MapSpells(mapSpells));
        GameSelectionPlayer gameSelectionPlayer = gameSelection.getPlayer(MasterserverClientUtil.getPlayerId());
        int[][] mapSpellsIndices = gameSelectionPlayer.getPlayerData().getMapSpellsIndices();
        if(mapSpellsIndices != null){
            int mapSpellIndex = mapSpellsIndices[selectableMapSpellsIndices.get(mapSpellsIndex)[0]][selectableMapSpellsIndices.get(mapSpellsIndex)[1]];
            cbxMapSpells[mapSpellsIndex].setSelectedIndex(mapSpellIndex);
        }
    }
    
    public void sendGameSelectionPlayerDataUpdate(){
        int[][] mapSpellsIndices = getMapSpellsIndices();
        sendMessage(new Message_SetGameSelectionPlayerData(new GameSelectionPlayerData(selectedCharacterID, mapSpellsIndices)));
    }
    
    private int[][] getMapSpellsIndices(){
        MapSpells[] mapSpells = map.getSpells();
        int[][] mapSpellsIndices = new int[mapSpells.length][];
        for(int i=0;i<mapSpells.length;i++){
            mapSpellsIndices[i] = new int[mapSpells[i].getKeys().length];
        }
        for(int i=0;i<selectableMapSpellsIndices.size();i++){
            mapSpellsIndices[selectableMapSpellsIndices.get(i)[0]][selectableMapSpellsIndices.get(i)[1]] = cbxMapSpells[i].getSelectedIndex();
        }
        return mapSpellsIndices;
    }
    
    public void update(GameSelection gameSelection){
        this.gameSelection = gameSelection;
        updateTeam(panTeamContainer1, 0);
        updateTeam(panTeamContainer2, 1);
    }
    
    private void updateTeam(JPanel panTeamContainer, int teamIndex){
        panTeamContainer.removeAll();
        if(teamIndex < gameSelection.getTeams().length){
            GameSelectionPlayer[] team = gameSelection.getTeams()[teamIndex];
            panTeamContainer.add(new PanGameSelection_Team(this, team));
        }
        panTeamContainer.updateUI();
    }

    private void setLockedIn(boolean isLockedIn) {
        this.isLockedIn = isLockedIn;
        boolean enableControls = (!isLockedIn);
        cbxMapSpell1.setEnabled(enableControls);
        cbxMapSpell2.setEnabled(enableControls);
        btnLockIn.setEnabled(enableControls);
        if (isLockedIn) {
            sendMessage(new Message_LockInGameSelection());
        }
    }

    private void sendMessage(Message message){
        NetworkClient networkClient = MasterserverClientUtil.getNetworkClient();
        networkClient.sendMessage(message);
    }

    public Map getMap(){
        return map;
    }

    public void setSelectedCharacter(OwnedGameCharacter ownedGameCharacter){
        this.selectedCharacterID = ownedGameCharacter.getCharacter().getID();
        lblCharacterTitle.setText(ownedGameCharacter.getCharacter().getTitle());
        panCharacters.updateCharacterPanels();
        tpaneCharacters.setComponentAt(1, getCharacterSkinsPanel(ownedGameCharacter));
        tpaneCharacters.setEnabledAt(1, true);
    }
    
    private PanGameSelection_CharacterSkins getCharacterSkinsPanel(OwnedGameCharacter ownedGameCharacter){
        PanGameSelection_CharacterSkins panCharacterSkins = panCharactersSkins.get(ownedGameCharacter);
        if(panCharacterSkins == null){
            panCharacterSkins = new PanGameSelection_CharacterSkins(this, ownedGameCharacter);
            panCharactersSkins.put(ownedGameCharacter, panCharacterSkins);
        }
        return panCharacterSkins;
    }

    public int getSelectedCharacterID(){
        return selectedCharacterID;
    }

    public LinkedList<int[]> getSelectableMapSpellsIndices(){
        return selectableMapSpellsIndices;
    }

    public boolean isLockedIn() {
        return isLockedIn;
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
        panTeamContainer1 = new javax.swing.JPanel();
        panTeamContainer2 = new javax.swing.JPanel();
        tpaneCharacters = new javax.swing.JTabbedPane();
        panOptions = new javax.swing.JPanel();
        btnLockIn = new javax.swing.JButton();
        cbxMapSpell2 = new javax.swing.JComboBox();
        cbxMapSpell1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblMapName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCharacterTitle = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setBackground(new java.awt.Color(30, 30, 30));

        panTeamContainer1.setOpaque(false);
        panTeamContainer1.setLayout(new java.awt.GridLayout(1, 0));

        panTeamContainer2.setOpaque(false);
        panTeamContainer2.setLayout(new java.awt.GridLayout(1, 0));

        panOptions.setBackground(new java.awt.Color(55, 55, 55));
        panOptions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 90, 90)));

        btnLockIn.setText("Lock In");
        btnLockIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLockInActionPerformed(evt);
            }
        });

        cbxMapSpell2.setEnabled(false);
        cbxMapSpell2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMapSpell2ItemStateChanged(evt);
            }
        });

        cbxMapSpell1.setEnabled(false);
        cbxMapSpell1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMapSpell1ItemStateChanged(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Map:");

        lblMapName.setForeground(new java.awt.Color(255, 255, 255));
        lblMapName.setText("?");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Character:");

        lblCharacterTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblCharacterTitle.setText("?");

        javax.swing.GroupLayout panOptionsLayout = new javax.swing.GroupLayout(panOptions);
        panOptions.setLayout(panOptionsLayout);
        panOptionsLayout.setHorizontalGroup(
            panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCharacterTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMapName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLockIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panOptionsLayout.createSequentialGroup()
                        .addComponent(cbxMapSpell1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(cbxMapSpell2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        panOptionsLayout.setVerticalGroup(
            panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panOptionsLayout.createSequentialGroup()
                .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panOptionsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxMapSpell2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMapSpell1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblMapName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblCharacterTitle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(btnLockIn)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panTeamContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpaneCharacters, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addComponent(panOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panTeamContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panTeamContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(tpaneCharacters, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(panOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addComponent(panTeamContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMapSpell1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMapSpell1ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            sendGameSelectionPlayerDataUpdate();
        }
    }//GEN-LAST:event_cbxMapSpell1ItemStateChanged

    private void cbxMapSpell2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMapSpell2ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            sendGameSelectionPlayerDataUpdate();
        }
    }//GEN-LAST:event_cbxMapSpell2ItemStateChanged

    private void btnLockInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLockInActionPerformed
        setLockedIn(true);
    }//GEN-LAST:event_btnLockInActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrHostOrConnect;
    private javax.swing.JButton btnLockIn;
    private javax.swing.JComboBox cbxMapSpell1;
    private javax.swing.JComboBox cbxMapSpell2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCharacterTitle;
    private javax.swing.JLabel lblMapName;
    private javax.swing.JPanel panOptions;
    private javax.swing.JPanel panTeamContainer1;
    private javax.swing.JPanel panTeamContainer2;
    private javax.swing.JTabbedPane tpaneCharacters;
    // End of variables declaration//GEN-END:variables
}
