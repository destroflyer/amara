/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.buttons;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Carl
 */
public class ButtonUtil{
    
    public static ImageButtonPanel addImageBackgroundButton(JComponent parent, ImageButtonBuilder imageButtonBuilder){
        JLabel lblText = new JLabel();
        lblText.setFont(imageButtonBuilder.getFont());
        lblText.setForeground(imageButtonBuilder.getTextColor());
        ImageButtonPanel panel = new ImageButtonPanel(imageButtonBuilder, lblText);
        panel.setLocation(0, 0);
        BufferedImage imageNormal = imageButtonBuilder.getImageNormal();
        panel.setSize(new Dimension(imageNormal.getWidth(), imageNormal.getHeight()));
        panel.setText(imageButtonBuilder.getText());
        parent.add(panel);
        parent.setOpaque(false);
        return panel;
    }
}
