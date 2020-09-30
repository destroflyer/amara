/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanThanks.java
 *
 * Created on 02.08.2012, 23:56:34
 */
package amara.applications.master.client.launcher.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import amara.core.Util;
import amara.core.files.FileAssets;
import java.awt.Color;

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
        new Thanks("jMonkeyEngine", "Engine development, Starting help", "Interface/client/thanks/jmonkeyengine.png", "https://jmonkeyengine.org"),
        new Thanks("Meshtint", "Character models", "Interface/client/thanks/meshtint.png", "https://meshtint.com"),
        new Thanks("Mixamo", "Character models and rigs", "Interface/client/thanks/mixamo.png", "https://www.mixamo.com"),
        new Thanks("Blender", "Model editor", "Interface/client/thanks/blender.png", "https://blender.org"),
        new Thanks("YouTube", "Feedback", "Interface/client/thanks/youtube.png", "https://youtube.com/destrostudios"),
        // Persons
        new Thanks("TeMMeZz", "Tester, Game logic advices, Namings", "Interface/client/thanks/nidalee.jpg"),
        new Thanks("Icecold", "Tester, Game balance advices", "Interface/client/thanks/renekton.png"),
        new Thanks("egoVidiTe", "Tester", "Interface/client/user.png"),
        new Thanks("Golden Eagle", "Tester", "Interface/client/user.png"),
        new Thanks("Yalee", "Tester", "Interface/client/user.png"),
        new Thanks("ellimak1", "Tester", "Textures/effects/heart.png"),
        new Thanks("XBody", "Tester, Model advices", "Interface/client/user.png"),
        new Thanks("Kappa", "Inspiration", "Interface/client/thanks/kappa.png"),
        new Thanks("pspeed", "Code advices and jME help", "Interface/client/thanks/pspeed.png"),
        new Thanks("Marcel", "Code structure advices", "Interface/client/thanks/enton.png"),
        // Models
        new Thanks("Nevayashk@", "Model \"Bear Trap\"", "Interface/client/thanks/nevayashka.png", "https://free3d.com/user/nevayashka"),
        new Thanks("killyone", "Model \"Beetle Golem\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/killyone"),
        new Thanks("faouzig1990", "Model \"Cartoon City\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/faouzig1990"),
        new Thanks("PigArt", "Model \"Cartoon Forest\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/PigArt"),
        new Thanks("Sebastian Lague", "Model \"Treasure Chest\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/SebastL"),
        new Thanks("Cestmir Dammer", "Model \"Chicken\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/CDmir230"),
        new Thanks("xrg", "Model \"Column\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/xrg"),
        new Thanks("Zoltan Miklosi", "Model \"Dragon\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/miklosizoltan"),
        new Thanks("Gabriel Piacenti", "Model \"Earth Elemental\"", "Interface/client/thanks/blender.png", "https://opengameart.org/users/piacenti"),
        new Thanks("CDmir230", "Model \"Forest Monster\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/CDmir230"),
        new Thanks("MutantGenepool", "Model \"Japanese Bridge\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/MutantGenepool"),
        new Thanks("willymax-0", "Model \"Little Dragon\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/willymax-0"),
        new Thanks("derekwatts", "Model \"Mjolnir\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/DerekWatts"),
        new Thanks("Arteria3d", "Model \"Female Druid\"", "Interface/client/thanks/arteria3d.png", "https://arteria3d.myshopify.com"),
        new Thanks("Pabong", "Model \"Tower Defense\"", "Interface/client/thanks/blender.png", "https://turbosquid.com/Search/Artists/Pabong"),
        new Thanks("Julio Iglesias", "Model \"Pseudospider\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/Zafio"),
        new Thanks("Jumah Safarty", "Model \"Soldier\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/jumahsafarty"),
        new Thanks("minglepingle", "Model \"Steve\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/minglepingle"),
        new Thanks("Blake Harris", "Model \"Futuristic Glass Wall\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/CopperStache"),
        new Thanks("serienreviewer", "Model \"Thousand Sunny\"", "Interface/client/thanks/blender.png", "https://sketchfab.com/serienreviewer"),
        new Thanks("SBLMultimedia", "Model \"Tombstone\"", "Interface/client/thanks/blender.png", "https://turbosquid.com/Search/Artists/SBLMultimedia"),
        new Thanks("silveralv", "Model \"Tower\"", "Interface/client/thanks/blender.png", "https://deviantart.com/silveralv"),
        new Thanks("xuroiux", "Model \"Tree\"", "Interface/client/thanks/blender.png", "https://blendswap.com/user/xuroiux"),
        // Frameworks
        new Thanks("Effekseer", "Particle editor including samples", "Interface/client/thanks/effekseer.png", "https://effekseer.github.io"),
        new Thanks("ShaderBlow", "Shaders (Bubble, CartoonEdge, Electricity, Glass)", "Interface/client/thanks/jmonkeyengine.png", "https://github.com/jMonkeyEngine-Contributions/shaderblowlib"),
        new Thanks("t0neg0d", "Particle effects", "Interface/client/thanks/jmonkeyengine.png", "https://github.com/t0neg0d"),
        // Interface
        new Thanks("Icons8", "Icon \"Bot\"", "Interface/client/avatars/bot.png", "https://icons8.com/icon/37410/bot"),
        new Thanks("Mahmoud Saleh", "Icon set \"Mini Icons\"", "Interface/client/audio.png", "https://iconarchive.com/artist/salehhh.html"),
        new Thanks("Pixel Mixer", "Icon set \"Basic Icons\"", "Interface/client/user.png", "https://iconarchive.com/artist/pixelmixer.html"),
        new Thanks("juanello", "Cursors \"Warrior Gloves\"", "Interface/client/thanks/realworld_graphics.png", "http://rw-designer.com/user-art/19010"),
        new Thanks("Digital Worlds JSC", "Icon sets \"Loot Icons Pack Vol.1-6\"", "Interface/client/thanks/unity.png", "https://assetstore.unity.com/publishers/6364"),
        new Thanks("PONETI", "Icon sets \"500 Skill Icons Vol.1-2\"", "Interface/client/thanks/unity.png", "https://assetstore.unity.com/publishers/38930"),
        new Thanks("Temporalcortex", "HUD Overlay \"Metal Tech\"", "Interface/client/thanks/youtube.png", "https://youtube.com/Temporalcortex"),
        // Textures
        new Thanks("Twitch", "Reaction Emotes", "Interface/client/thanks/twitch.png", "https://twitch.tv"),
        new Thanks("Para", "Texture set \"Animated particle effects #2\"", "Textures/effects/blood.png", "https://opengameart.org/users/para"),
        new Thanks("RigzSoft", "Texture \"Frost\"", "Textures/effects/frost_additive.png", "https://rigzsoft.co.uk"),
        new Thanks("Rawshorts", "Texture \"Heart\"", "Textures/effects/heart.png", "https://rawshorts.com"),
        new Thanks("ClipArtBest", "Texture \"Crosshair's Cutie Mark\"", "Textures/effects/robins_gift_mark.png", "http://clipartbest.com"),
        new Thanks("CleanPNG", "Texture \"Ninja Shuriken\"", "Textures/effects/scarlet_passive.png", "https://cleanpng.com"),
        new Thanks("Jockum Skoglund", "Sky map \"Miramar\"", "Textures/skies/miramar/front.png", "https://deviantart.com/hipshot"),
        //Audio
        new Thanks("Michikawa", "Music pack \"Celestial Aeon Project\"", "Interface/client/audio.png", "https://mikseri.net/essence"),
        new Thanks("Dynamedion", "Music pack \"Music Demo Reel\"", "Interface/client/audio.png", "https://dynamedion.com/home"),
        new Thanks("Blastwave FX", "Sound pack \"Free Sound Effects Download Pack #1\"", "Interface/client/audio.png", "https://blastwavefx.com"),
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
            ImageIcon icon = FileAssets.getImageIcon(thanks.getIconFilePath(), lineHeight, lineHeight);
            JLabel lblIcon = new JLabel(icon);
            lblIcon.setLocation(x, y);
            lblIcon.setSize(lineHeight, lineHeight);
            panThanksList.add(lblIcon);
            x += 30;
            JLabel lblReceiver = new JLabel(thanks.getReceiver());
            lblReceiver.setLocation(x, y);
            lblReceiver.setSize(130, lineHeight);
            lblReceiver.setForeground(Color.WHITE);
            panThanksList.add(lblReceiver);
            x += 135;
            JLabel lblDescription = new JLabel(thanks.getDescription());
            lblDescription.setLocation(x, y);
            lblDescription.setSize(240, lineHeight);
            lblDescription.setForeground(Color.WHITE);
            panThanksList.add(lblDescription);
            x += 245;
            if(thanks.hasURL()){
                JLabel lblURL = new JLabel("<html><u>" + thanks.getURL() + "</u></html>");
                lblURL.setLocation(x, y);
                lblURL.setSize(300, lineHeight);
                lblURL.setForeground(Color.WHITE);
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

        setBackground(new java.awt.Color(30, 30, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("A big \"Thank you\" goes to:");

        jScrollPane1.setBorder(null);

        panThanksList.setBackground(new java.awt.Color(30, 30, 30));

        javax.swing.GroupLayout panThanksListLayout = new javax.swing.GroupLayout(panThanksList);
        panThanksList.setLayout(panThanksListLayout);
        panThanksListLayout.setHorizontalGroup(
            panThanksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 974, Short.MAX_VALUE)
        );
        panThanksListLayout.setVerticalGroup(
            panThanksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
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
