/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Dimension;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.files.FileAssets;
import amara.core.input.Keys;
import amara.core.settings.Settings;
import amara.libraries.entitysystem.templates.StaticEntityWorld;

/**
 *
 * @author Carl
 */
public class PanCharacters_CharacterInfo extends javax.swing.JPanel{

    public PanCharacters_CharacterInfo(GameCharacter character){
        initComponents();
        lblName.setText(character.getTitle());
        lblIcon.setIcon(FileAssets.getImageIcon("Interface/client/characters/" + character.getName() + "/icon_56.png"));
        int characterEntity = StaticEntityWorld.loadTemplate("units/" + character.getName());
        int panelHeight = 70;
        int y = 0;
        PassivesComponent passivesComponent = StaticEntityWorld.getEntityWorld().getComponent(characterEntity, PassivesComponent.class);
        if(passivesComponent != null){
            lblPassives.setText(((passivesComponent.getPassiveEntities().length > 1)?"Passives":"Passive") + ":");
            for(int passiveEntity : passivesComponent.getPassiveEntities()){
                PanCharacters_CharacterInfo_Spell panPassive = new PanCharacters_CharacterInfo_Spell("Passive", passiveEntity);
                panPassive.setLocation(0, y);
                panPassive.setSize(384, panelHeight);
                panPassives.add(panPassive);
                //Subtract 1 to avoid double borders
                y += (panelHeight - 1);
            }
            y += 1;
        }
        else{
            PanCharacters_CharacterInfo_NotExisting panNotExisting = new PanCharacters_CharacterInfo_NotExisting();
            panNotExisting.setSize(384, panelHeight);
            panPassives.add(panNotExisting);
            y += panelHeight;
        }
        panPassives.setSize(new Dimension(394, y));
        y = 0;
        LearnableSpellsComponent learnableSpellsComponent = StaticEntityWorld.getEntityWorld().getComponent(characterEntity, LearnableSpellsComponent.class);
        if(learnableSpellsComponent != null){
            int[] spellEntities = learnableSpellsComponent.getSpellsEntities();
            int spellsCount = 0;
            for(int i=0;i<spellEntities.length;i++){
                if(spellEntities[i] != -1){
                    String keyTitle = Keys.getTitle(Settings.getInteger("controls_spells_" + i));
                    PanCharacters_CharacterInfo_Spell panSpell = new PanCharacters_CharacterInfo_Spell(keyTitle, spellEntities[i]);
                    panSpell.setLocation(0, y);
                    panSpell.setSize(384, panelHeight);
                    panSpells.add(panSpell);
                    //Subtract 1 to avoid double borders
                    y += (panelHeight - 1);
                    spellsCount++;
                }
            }
            y += 1;
            lblSpells.setText(((spellsCount > 1)?"Spells":"Spell") + ":");
        }
        else{
            PanCharacters_CharacterInfo_NotExisting panNotExisting = new PanCharacters_CharacterInfo_NotExisting();
            panNotExisting.setSize(384, panelHeight);
            panSpells.add(panNotExisting);
            y += panelHeight;
        }
        panSpells.setPreferredSize(new Dimension(394, y));
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
        lblName = new javax.swing.JLabel();
        lblLore = new javax.swing.JLabel();
        lblPassives = new javax.swing.JLabel();
        scrLore = new javax.swing.JScrollPane();
        txtLore = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panPassives = new javax.swing.JPanel();
        lblSpells = new javax.swing.JLabel();
        panSpells = new javax.swing.JPanel();

        setBackground(new java.awt.Color(55, 55, 55));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 90, 90)));

        lblIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 90, 90)));
        lblIcon.setPreferredSize(new java.awt.Dimension(30, 30));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("???");
        lblName.setPreferredSize(new java.awt.Dimension(115, 30));

        lblLore.setForeground(new java.awt.Color(255, 255, 255));
        lblLore.setText("Lore:");

        lblPassives.setForeground(new java.awt.Color(255, 255, 255));
        lblPassives.setText("Passive:");

        scrLore.setBorder(null);
        scrLore.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtLore.setEditable(false);
        txtLore.setBackground(new java.awt.Color(55, 55, 55));
        txtLore.setColumns(20);
        txtLore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtLore.setForeground(new java.awt.Color(255, 255, 255));
        txtLore.setLineWrap(true);
        txtLore.setRows(5);
        txtLore.setText("Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake Cheesecake.");
        txtLore.setWrapStyleWord(true);
        scrLore.setViewportView(txtLore);

        jPanel1.setOpaque(false);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Role:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("?");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("?");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("X:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Y:");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)))
        );

        panPassives.setOpaque(false);

        javax.swing.GroupLayout panPassivesLayout = new javax.swing.GroupLayout(panPassives);
        panPassives.setLayout(panPassivesLayout);
        panPassivesLayout.setHorizontalGroup(
            panPassivesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panPassivesLayout.setVerticalGroup(
            panPassivesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblSpells.setForeground(new java.awt.Color(255, 255, 255));
        lblSpells.setText("Spells:");

        panSpells.setOpaque(false);

        javax.swing.GroupLayout panSpellsLayout = new javax.swing.GroupLayout(panSpells);
        panSpells.setLayout(panSpellsLayout);
        panSpellsLayout.setHorizontalGroup(
            panSpellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panSpellsLayout.setVerticalGroup(
            panSpellsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPassives, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panPassives, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSpells, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panSpells, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrLore, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(lblPassives)
                .addGap(5, 5, 5)
                .addComponent(panPassives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblSpells)
                .addGap(5, 5, 5)
                .addComponent(panSpells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblLore)
                .addGap(5, 5, 5)
                .addComponent(scrLore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLore;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassives;
    private javax.swing.JLabel lblSpells;
    private javax.swing.JPanel panPassives;
    private javax.swing.JPanel panSpells;
    private javax.swing.JScrollPane scrLore;
    private javax.swing.JTextArea txtLore;
    // End of variables declaration//GEN-END:variables
}
