/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.buttons;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import amara.libraries.applications.windowed.FrameUtil;

/**
 *
 * @author Carl
 */
public class ImageButtonPanel extends JPanel{

    public ImageButtonPanel(ImageButtonBuilder imageButtonBuilder, JLabel lblText){
        this.imageButtonBuilder = imageButtonBuilder;
        this.lblText = lblText;
        setLayout(new GridBagLayout());
        setOpaque(false);
        addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent evt){
                super.mouseEntered(evt);
                setButtonActionType(ImageButtonBuilder.ButtonActionType.HOVERED);
            }

            @Override
            public void mouseExited(MouseEvent evt){
                super.mouseExited(evt);
                setButtonActionType(ImageButtonBuilder.ButtonActionType.NORMAL);
            }

            @Override
            public void mousePressed(MouseEvent evt){
                super.mousePressed(evt);
                setButtonActionType(ImageButtonBuilder.ButtonActionType.PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent evt){
                super.mouseReleased(evt);
                if(buttonActionType == ImageButtonBuilder.ButtonActionType.PRESSED){
                    setButtonActionType(ImageButtonBuilder.ButtonActionType.HOVERED);
                }
            }
        });
        add(lblText);
    }
    private ImageButtonBuilder imageButtonBuilder;
    private ImageButtonBuilder.ButtonActionType buttonActionType = ImageButtonBuilder.ButtonActionType.NORMAL;
    private JLabel lblText;
            
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(imageButtonBuilder.getImage(buttonActionType), 0, 0, this);
    }
    
    private void setButtonActionType(ImageButtonBuilder.ButtonActionType buttonActionType){
        this.buttonActionType = buttonActionType;
        FrameUtil.getWindow(this).repaint();
    }

    public void setText(String text){
        lblText.setText(text);
    }
}
