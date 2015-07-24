/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara;

import java.awt.image.BufferedImage;

/**
 *
 * @author Carl
 */
public class GameInfo{
    
    public static final String NAME = "Amara";
    public static final String VERSION = "0.3";
    public static final BufferedImage ICON_16 = Util.getResourceImage("/Interface/client/icon/16.png");
    public static final BufferedImage ICON_32 = Util.getResourceImage("/Interface/client/icon/32.png");
    public static final BufferedImage ICON_64 = Util.getResourceImage("/Interface/client/icon/64.png");
    public static final BufferedImage ICON_128 = Util.getResourceImage("/Interface/client/icon/128.png");
    public static final BufferedImage[] ICONS = new BufferedImage[]{
        ICON_16, ICON_32, ICON_64, ICON_128
    };
    
    public static String getClientTitle(){
        return (NAME + " - GameClient [Version " + VERSION + "]");
    }
}
