/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.files;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Carl
 */
public class FileAssets{
    
    public static String ROOT;
    
    public static void readRootFile(){
        ROOT = FileManager.getFileContent("./assets.ini");
    }
    
    public static boolean exists(String filePath){
        return FileManager.existsFile(ROOT + filePath);
    }
    
    public static ImageIcon getImageIcon(String filePath){
        return new ImageIcon(ROOT + filePath);
    }
    
    public static ImageIcon getImageIcon(String filePath, int width, int height){
        return new ImageIcon(getImage(filePath, width, height));
    }
    
    public static Image getImage(String filePath, int width, int height){
        return getImage(filePath).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    public static BufferedImage getImage(String filePath){
        try{
            return ImageIO.read(new File(ROOT + filePath));
        }catch(Exception ex){
            System.err.println("Error while reading image file '" + filePath + "'.");
        }
        return null;
    }
    
    public static FileInputStream getInputStream(String filePath){
        try{
            return new FileInputStream(ROOT + filePath);
        }catch(FileNotFoundException ex){
            System.err.println("Error while reading file '" + filePath + "'.");
        }
        return null;
    }
}
