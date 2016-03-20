/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core.files;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    
    public static BufferedImage getImage(String filePath, int width, int height){
        BufferedImage image = getImage(filePath);
        if(width == -1){
            width = (int) ((((float) image.getWidth()) / image.getHeight()) * height);
        }
        else if(height == -1){
            height = (int) ((((float) image.getHeight()) / image.getWidth()) * width);
        }
        if((width != image.getWidth()) || (height != image.getHeight())){
            BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
            graphics.dispose();
            return resizedImage;
        }
        return image;
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
