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
    
    public static JComponent addImageBackgroundButton(JComponent parent, ImageButtonBuilder imageButtonBuilder){
        ImageButtonPanel panel = new ImageButtonPanel(imageButtonBuilder);
        panel.setLocation(0, 0);
        BufferedImage imageNormal = imageButtonBuilder.getImageNormal();
        panel.setSize(new Dimension(imageNormal.getWidth(), imageNormal.getHeight()));
        parent.add(panel);
        parent.setOpaque(false);
        JLabel lblText = new JLabel(imageButtonBuilder.getText());
        lblText.setFont(imageButtonBuilder.getFont());
        lblText.setForeground(imageButtonBuilder.getTextColor());
        panel.add(lblText);
        return panel;
    }
}
