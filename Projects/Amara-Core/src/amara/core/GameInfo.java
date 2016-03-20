/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core;

import java.awt.image.BufferedImage;
import amara.core.files.FileAssets;

/**
 *
 * @author Carl
 */
public class GameInfo{
    
    public static final String NAME = "Amara";
    public static final String VERSION = "0.4";
    public static final BufferedImage ICON_16 = FileAssets.getImage("Interface/client/icon/16.png");
    public static final BufferedImage ICON_32 = FileAssets.getImage("Interface/client/icon/32.png");
    public static final BufferedImage ICON_64 = FileAssets.getImage("Interface/client/icon/64.png");
    public static final BufferedImage ICON_128 = FileAssets.getImage("Interface/client/icon/128.png");
    public static final BufferedImage[] ICONS = new BufferedImage[]{
        ICON_16, ICON_32, ICON_64, ICON_128
    };
    
    public static String getClientTitle(){
        return (NAME + " - GameClient [Version " + VERSION + "]");
    }
}
