/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * panPlay.java
 *
 * Created on 02.08.2012, 23:56:34
 */
package amara.launcher.client.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import amara.core.files.FileAssets;
import amara.engine.applications.masterserver.client.MasterserverClientUtil;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.messages.protocol.Message_EditCharacterInventory;
import amara.launcher.client.MainFrame;
import amara.launcher.client.buttons.*;
import amara.launcher.client.comboboxes.ComboboxModel_OwnedCharacters;
import amara.libraries.applications.windowed.FrameUtil;

/**
 *
 * @author Carl
 */
public class PanItems extends javax.swing.JPanel{

    public PanItems(){
        initComponents();
        loadOwnedItems();
        cbxCharacters.setModel(new ComboboxModel_OwnedCharacters(MasterserverClientUtil.getOwnedCharacters()));
        loadInventory();
        JComponent btnSave = ButtonUtil.addImageBackgroundButton(panContainer_btnSave, new DefaultButtonBuilder("default_150x50", "Save"));
        btnSave.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt){
                super.mouseClicked(evt);
                saveInventory();
            }
        });
        updateSelectedCharacterInventory();
    }
    private final int dragItemSize = 55;
    private PanItems_OwnedItem[] panOwnedItems;
    private HashMap<Item, ImageIcon> itemIcons;
    private ImageIcon itemIcon_None = FileAssets.getImageIcon("Interface/hud/items/none.png", dragItemSize, dragItemSize);
    private OwnedItem[] availableItems;
    private JLabel lblInvetoryItems[] = new JLabel[6];
    private int[] inventory = new int[6];
    private JLabel lblDragItem;
    
    private void loadOwnedItems(){
        OwnedItem[] ownedItems = MasterserverClientUtil.getOwnedItems();
        availableItems = new OwnedItem[ownedItems.length];
        for(int i=0;i<availableItems.length;i++){
            int amount = ownedItems[i].getAmount();
            for(int r=0;r<inventory.length;r++){
                if(inventory[r] == ownedItems[i].getItem().getID()){
                    amount--;
                }
            }
            availableItems[i] = new OwnedItem(ownedItems[i].getItem(), amount);
        }
        panOwnedItems = new PanItems_OwnedItem[ownedItems.length];
        itemIcons = new HashMap<Item, ImageIcon>(ownedItems.length);
        int padding = 2;
        int y = padding;
        for(int i=0;i<ownedItems.length;i++){
            PanItems_OwnedItem panItem = new PanItems_OwnedItem();
            panItem.setLocation(padding, y);
            panItem.setSize(320, 60);
            panelItems.add(panItem);
            panOwnedItems[i] = panItem;
            y += (60 + padding);
        }
        panelItems.setPreferredSize(new Dimension(320 + (2 * padding) + 18, y));
    }
    
    private void updateAvailableItems(){
        OwnedItem[] ownedItems = MasterserverClientUtil.getOwnedItems();
        availableItems = new OwnedItem[ownedItems.length];
        for(int i=0;i<availableItems.length;i++){
            int amount = ownedItems[i].getAmount();
            for(int r=0;r<inventory.length;r++){
                if(inventory[r] == ownedItems[i].getItem().getID()){
                    amount--;
                }
            }
            availableItems[i] = new OwnedItem(ownedItems[i].getItem(), amount);
            panOwnedItems[i].setOwnedItem(availableItems[i]);
            disableDragAndDrop(panOwnedItems[i]);
            if(amount > 0){
                enableDragAndDrop(panOwnedItems[i], ownedItems[i].getItem());
            }
        }
    }
    
    private void loadInventory(){
        int padding = 1;
        int x = padding;
        int y = padding;
        for(int i=0;i<6;i++){
            JLabel lblItem = new JLabel(itemIcon_None);
            lblItem.setLocation(x, y);
            lblItem.setSize(dragItemSize, dragItemSize);
            lblItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panInventory.add(lblItem);
            lblInvetoryItems[i] = lblItem;
            x += (dragItemSize + padding);
            if(i == 2){
                x = padding;
                y += (dragItemSize + padding);
            }
        }
        lblDragItem = new JLabel();
        lblDragItem.setSize(dragItemSize, dragItemSize);
    }
    
    private void enableDragAndDrop(JComponent component, Item item){
        component.addMouseListener(new ItemDragAdapter(component, item));
        component.addMouseMotionListener(new ItemDropAdapter(component));
    }
    
    private void disableDragAndDrop(JComponent component){
        for(MouseListener mouseListener : component.getMouseListeners()){
            if(mouseListener instanceof ItemDragAdapter){
                component.removeMouseListener(mouseListener);
            }
        }
        for(MouseMotionListener mouseMotionListener : component.getMouseMotionListeners()){
            if(mouseMotionListener instanceof ItemDropAdapter){
                component.removeMouseMotionListener(mouseMotionListener);
            }
        }
    }
    
    private void updateSelectedCharacterInventory(){
        OwnedGameCharacter ownedCharacter = getSelectedOwnedCharacter();
        for(int i=0;i<inventory.length;i++){
            disableDragAndDrop(lblInvetoryItems[i]);
            int itemID = 0;
            ImageIcon itemIcon = itemIcon_None;
            if((i < ownedCharacter.getInventory().length) && (ownedCharacter.getInventory()[i] != 0)){
                Item item = MasterserverClientUtil.getItem(ownedCharacter.getInventory()[i]);
                itemID = item.getID();
                itemIcon = getItemIcon(item);
                enableDragAndDrop(lblInvetoryItems[i], item);
            }
            inventory[i] = itemID;
            lblInvetoryItems[i].setIcon(itemIcon);
        }
        updateAvailableItems();
    }
    
    private ImageIcon getItemIcon(Item item){
        ImageIcon icon = itemIcons.get(item);
        if(icon == null){
            icon = FileAssets.getImageIcon("Interface/hud/items/" + item.getName() + ".png", dragItemSize, dragItemSize);
        }
        return icon;
    }
    
    private void saveInventory(){
        OwnedGameCharacter ownedCharacter = getSelectedOwnedCharacter();
        int[] newInventory = inventory.clone();
        MasterserverClientUtil.getNetworkClient().sendMessage(new Message_EditCharacterInventory(ownedCharacter.getCharacter().getID(), newInventory));
        ownedCharacter.setInventory(newInventory);
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

        bgrHostOrConnect = new javax.swing.ButtonGroup();
        scrollpanelItems = new javax.swing.JScrollPane();
        panelItems = new javax.swing.JPanel();
        cbxCharacters = new javax.swing.JComboBox();
        panInventory = new javax.swing.JPanel();
        panContainer_btnSave = new javax.swing.JPanel();

        setBackground(new java.awt.Color(30, 30, 30));

        scrollpanelItems.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpanelItems.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelItems.setPreferredSize(new java.awt.Dimension(324, 260));

        javax.swing.GroupLayout panelItemsLayout = new javax.swing.GroupLayout(panelItems);
        panelItems.setLayout(panelItemsLayout);
        panelItemsLayout.setHorizontalGroup(
            panelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        panelItemsLayout.setVerticalGroup(
            panelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        scrollpanelItems.setViewportView(panelItems);

        cbxCharacters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCharactersItemStateChanged(evt);
            }
        });

        panInventory.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panInventoryLayout = new javax.swing.GroupLayout(panInventory);
        panInventory.setLayout(panInventoryLayout);
        panInventoryLayout.setHorizontalGroup(
            panInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panInventoryLayout.setVerticalGroup(
            panInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panContainer_btnSaveLayout = new javax.swing.GroupLayout(panContainer_btnSave);
        panContainer_btnSave.setLayout(panContainer_btnSaveLayout);
        panContainer_btnSaveLayout.setHorizontalGroup(
            panContainer_btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        panContainer_btnSaveLayout.setVerticalGroup(
            panContainer_btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpanelItems, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCharacters, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(panContainer_btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpanelItems)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panContainer_btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCharactersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCharactersItemStateChanged
        updateSelectedCharacterInventory();
    }//GEN-LAST:event_cbxCharactersItemStateChanged

    private class ItemDragAdapter extends MouseAdapter{

        public ItemDragAdapter(JComponent sourceComponent, Item item){
            this.item = item;
            for(int i=0;i<lblInvetoryItems.length;i++){
                if(sourceComponent == lblInvetoryItems[i]){
                    inventorySourceIndex = i;
                    break;
                }
            }
        }
        private Item item;
        private int inventorySourceIndex = -1;

        @Override
        public void mousePressed(MouseEvent evt){
            super.mousePressed(evt);
            lblDragItem.setIcon(getItemIcon(item));
            MainFrame.getInstance().add(lblDragItem, 0);
        }

        @Override
        public void mouseReleased(MouseEvent evt){
            super.mouseReleased(evt);
            if(inventorySourceIndex != -1){
                inventory[inventorySourceIndex] = 0;
                lblInvetoryItems[inventorySourceIndex].setIcon(itemIcon_None);
                disableDragAndDrop(lblInvetoryItems[inventorySourceIndex]);
            }
            Point inventoryLocationInFrame = FrameUtil.getLocationIn(panInventory, MainFrame.getInstance().getContentPane());
            double mouseX = (lblDragItem.getX() + (lblDragItem.getWidth() / 2));
            double mouseY = (lblDragItem.getY() + (lblDragItem.getHeight() / 2));
            if((mouseX >= inventoryLocationInFrame.getX()) && (mouseX < (inventoryLocationInFrame.getX() + panInventory.getWidth()))
            && (mouseY >= inventoryLocationInFrame.getY()) && (mouseY < (inventoryLocationInFrame.getY() + panInventory.getHeight()))){
                double xPortion = ((mouseX - inventoryLocationInFrame.getX()) / panInventory.getWidth());
                double yPortion = ((mouseY - inventoryLocationInFrame.getY()) / panInventory.getHeight());
                int itemIndex = (int) (xPortion * 3);
                if(yPortion >= 0.5){
                    itemIndex += 3;
                }
                inventory[itemIndex] = item.getID();
                lblInvetoryItems[itemIndex].setIcon(getItemIcon(item));
                enableDragAndDrop(lblInvetoryItems[itemIndex], item);
            }
            updateAvailableItems();
            MainFrame.getInstance().remove(lblDragItem);
            MainFrame.getInstance().repaint();
        }
    }
    
    private class ItemDropAdapter extends MouseMotionAdapter{

        public ItemDropAdapter(JComponent sourceComponent){
            this.sourceComponent = sourceComponent;
        }
        private JComponent sourceComponent;
        
        @Override
        public void mouseDragged(MouseEvent evt){
            super.mouseDragged(evt);
            Point locationInFrame = FrameUtil.getLocationIn(sourceComponent, MainFrame.getInstance().getContentPane());
            int x = (int) (locationInFrame.getX() + evt.getX() - (lblDragItem.getWidth() / 2));
            int y = (int) (locationInFrame.getY() + evt.getY() - (lblDragItem.getHeight() / 2));
            lblDragItem.setLocation(x, y);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrHostOrConnect;
    private javax.swing.JComboBox cbxCharacters;
    private javax.swing.JPanel panContainer_btnSave;
    private javax.swing.JPanel panInventory;
    private javax.swing.JPanel panelItems;
    private javax.swing.JScrollPane scrollpanelItems;
    // End of variables declaration//GEN-END:variables
}
