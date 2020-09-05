/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class ImageButtonBuilder{

    public ImageButtonBuilder(String buttonName, String text){
        this.imageNormal = FileAssets.getImage("Interface/client/buttons/" + buttonName + "_normal.png");
        this.imageHovered = FileAssets.getImage("Interface/client/buttons/" + buttonName + "_hovered.png");
        this.imagePressed = FileAssets.getImage("Interface/client/buttons/" + buttonName + "_pressed.png");
        this.text = text;
    }
    public enum ButtonActionType{
        NORMAL,
        HOVERED,
        PRESSED
    }
    private BufferedImage imageNormal;
    private BufferedImage imageHovered;
    private BufferedImage imagePressed;
    private String text;
    private Font font = new Font("Tahoma", Font.PLAIN, 11);
    private Color textColor = Color.BLACK;
    
    public BufferedImage getImage(ButtonActionType buttonActionType){
        switch(buttonActionType){
            case HOVERED:
                return imageHovered;
           
            case PRESSED:
                return imagePressed;
        }
        return imageNormal;
    }

    public BufferedImage getImageNormal(){
        return imageNormal;
    }

    public BufferedImage getImageHovered(){
        return imageHovered;
    }

    public BufferedImage getImagePressed(){
        return imagePressed;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public Font getFont(){
        return font;
    }

    public Color getTextColor(){
        return textColor;
    }

    public void setFont(int style, int size, Color textColor){
        font = font.deriveFont(style, size);
        this.textColor = textColor;
    }
}
