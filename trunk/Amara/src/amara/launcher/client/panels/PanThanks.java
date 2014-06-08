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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import amara.Util;

/**
 *
 * @author Carl
 */
public class PanThanks extends javax.swing.JPanel{

    public PanThanks(){
        initComponents();
        addThanks();
    }
    private static final Thanks[] THANKS = new Thanks[]{
        new Thanks("JMonkeyEngine", "Engine development, Starting help", "/Interface/client/thanks/jmonkeyengine.png", "http://jmonkeyengine.org"),
        new Thanks("Blender", "Model editor", "/Interface/client/thanks/blender.png", "http://blender.org"),
        new Thanks("YouTube community", "Feedback", "/Interface/client/thanks/youtube.png", "http://youtube.com/destrostudios"),
        new Thanks("Icecold", "Tester", "/Interface/client/user.png"),
        new Thanks("Yalee", "Tester", "/Interface/client/user.png"),
        new Thanks("TeMMeZz", "Tester, Game logic advices", "/Interface/client/thanks/nidalee.jpg"),
        new Thanks("XBody", "Tester, Model advices", "/Interface/client/user.png"),
        new Thanks("Marcel", "Code structure advices", "/Interface/client/thanks/enton.png"),
        new Thanks("Kappa", "Inspiration", "/Interface/client/thanks/kappa.png"),
        new Thanks("Pixel Mixer", "Icon set \"Basic Set\"", "/Interface/client/user.png", "http://pixel-mixer.com"),
        new Thanks("Mahmoud Saleh", "Icon set \"Mini Icon Set\"", "/Interface/client/audio.png", "http://mahmoudsaleh.daportfolio.com"),
        new Thanks("PigArt", "Model \"Cartoon Forest\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/PigArt"),
        new Thanks("faouzig1990", "Model \"Cartoon City\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/faouzig1990"),
        new Thanks("miklosizoltan", "Model \"Dragon\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/miklosizoltan"),
        new Thanks("silveralv", "Model \"House\", \"Tower\"", "/Interface/client/thanks/blender.png", "http://silveralv.deviantart.com"),
        new Thanks("MutantGenepool", "Model \"Japanese Bridge\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/MutantGenepool"),
        new Thanks("gavlig", "Model \"Lava golem\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/blends/author/gavlig"),
        new Thanks("willymax-0", "Model \"Little dragon\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/blends/author/willymax-0"),
        new Thanks("twolpgamer", "Model \"Mill\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/blends/author/twolpgamer"),
        new Thanks("jumahsafarty", "Model \"Soldier\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/jumahsafarty"),
        new Thanks("minglepingle", "Model \"Steve\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/minglepingle"),
        new Thanks("yazjack", "Model \"Stock\", \"Barracks\"", "/Interface/client/thanks/blender.png", "http://yazjack.deviantart.com"),
        new Thanks("xuroiux", "Model \"Tree\"", "/Interface/client/thanks/blender.png", "http://www.blendswap.com/user/xuroiux"),
        new Thanks("Michikawa", "Music pack \"Celestial Aeon Project\"", "/Interface/client/audio.png", "http://mikseri.net/essence"),
        new Thanks("Blastwave FX", "Sound pack \"Free Sound Effects Download Pack #1\"", "/Interface/client/audio.png", "http://blastwavefx.com")
    };
    
    private void addThanks(){
        int padding = 5;
        int lineHeight = 20;
        int lineDistance = 4;
        int x;
        int y = padding;
        for(int i=0;i<THANKS.length;i++){
            final Thanks thanks = THANKS[i];
            x = padding;
            ImageIcon icon = Util.getResourceImageIcon(thanks.getIconFilePath(), lineHeight, lineHeight);
            JLabel lblIcon = new JLabel(icon);
            lblIcon.setLocation(x, y);
            lblIcon.setSize(lineHeight, lineHeight);
            panThanksList.add(lblIcon);
            x += 30;
            JLabel lblReceiver = new JLabel(thanks.getReceiver());
            lblReceiver.setLocation(x, y);
            lblReceiver.setSize(130, lineHeight);
            panThanksList.add(lblReceiver);
            x += 135;
            JLabel lblDescription = new JLabel(thanks.getDescription());
            lblDescription.setLocation(x, y);
            lblDescription.setSize(240, lineHeight);
            panThanksList.add(lblDescription);
            x += 245;
            if(thanks.hasURL()){
                JLabel lblURL = new JLabel("<html><a href=\"" + thanks.getURL()+ "\">" + thanks.getURL() + "</a></html>");
                lblURL.setLocation(x, y);
                lblURL.setSize(200, lineHeight);
                lblURL.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lblURL.addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseClicked(MouseEvent evt){
                        super.mouseClicked(evt);
                        Util.browseURL(thanks.getURL());
                    }
                });
                panThanksList.add(lblURL);
            }
            y += lineHeight + lineDistance;
        }
        panThanksList.setPreferredSize(new Dimension(573, y));
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panThanksList = new javax.swing.JPanel();

        jLabel1.setText("A big \"Thank you\" goes to:");

        jScrollPane1.setBorder(null);

        javax.swing.GroupLayout panThanksListLayout = new javax.swing.GroupLayout(panThanksList);
        panThanksList.setLayout(panThanksListLayout);
        panThanksListLayout.setHorizontalGroup(
            panThanksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );
        panThanksListLayout.setVerticalGroup(
            panThanksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panThanksList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrHostOrConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panThanksList;
    // End of variables declaration//GEN-END:variables
}
