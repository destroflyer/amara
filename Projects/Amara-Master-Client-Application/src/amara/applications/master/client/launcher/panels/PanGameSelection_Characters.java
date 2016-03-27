/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Dimension;
import amara.applications.master.client.MasterserverClientUtil;
import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class PanGameSelection_Characters extends javax.swing.JPanel{

    public PanGameSelection_Characters(PanGameSelection panGameSelection){
        initComponents();
        this.panGameSelection = panGameSelection;
        int panelSize = 58;
        int padding = 5;
        int containerWidth = 225;
        int x = padding;
        int y = padding;
        OwnedGameCharacter[] ownedCharacters = MasterserverClientUtil.getOwnedCharacters();
        panCharacters = new PanGameSelection_Character[ownedCharacters.length];
        for(int i=0;i<ownedCharacters.length;i++){
            if((x + panelSize + padding) >= containerWidth){
                x = padding;
                y += (panelSize + padding);
            }
            PanGameSelection_Character panCharacter = new PanGameSelection_Character(panGameSelection, ownedCharacters[i]);
            panCharacter.setLocation(x, y);
            panCharacter.setSize(panelSize, panelSize);
            panCharactersContainer.add(panCharacter);
            panCharacters[i] = panCharacter;
            x += (panelSize + padding);
        }
        y += (panelSize + padding);
        panCharactersContainer.setPreferredSize(new Dimension(containerWidth, y));
    }
    private PanGameSelection panGameSelection;
    private PanGameSelection_Character[] panCharacters;
    
    public void resetScroll(){
        scrCharacters.getVerticalScrollBar().setValue(0);
    }
    
    public void updateCharacterPanels(){
        for(PanGameSelection_Character panCharacter : panCharacters){
            panCharacter.updateBorder();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrCharacters = new javax.swing.JScrollPane();
        panCharactersContainer = new javax.swing.JPanel();

        scrCharacters.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrCharacters.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panCharactersContainer.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panCharactersContainerLayout = new javax.swing.GroupLayout(panCharactersContainer);
        panCharactersContainer.setLayout(panCharactersContainerLayout);
        panCharactersContainerLayout.setHorizontalGroup(
            panCharactersContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        panCharactersContainerLayout.setVerticalGroup(
            panCharactersContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        scrCharacters.setViewportView(panCharactersContainer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrCharacters, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrCharacters, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panCharactersContainer;
    private javax.swing.JScrollPane scrCharacters;
    // End of variables declaration//GEN-END:variables
}