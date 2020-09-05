/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.ingame.maps;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Texture2D;
import amara.core.files.FileAssets;
import amara.libraries.applications.display.materials.PaintableImage;

/**
 *
 * @author Carl
 */
public class TerrainAlphamap{

    public TerrainAlphamap(String imagePath){
        this.imagePath = imagePath;
        paintableImage = new PaintableImage(imagePath, true);
        for(int x=0;x<paintableImage.getWidth();x++){
            for(int y=0;y<paintableImage.getHeight();y++){
                if(paintableImage.getPixel_Alpha(x, y) == 0){
                    paintableImage.setPixel_Red(x, y, 0);
                    paintableImage.setPixel_Green(x, y, 0);
                    paintableImage.setPixel_Blue(x, y, 0);
                }
                paintableImage.setPixel_Alpha(x, y, 0);
            }
        }
        texture2D = new Texture2D();
    }
    private String imagePath;
    private PaintableImage paintableImage;
    private Texture2D texture2D;
    
    public void updateTexture(){
        texture2D.setImage(paintableImage.getImage());
    }
    
    public void saveFile(){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(FileAssets.ROOT + imagePath);
            String format = imagePath.substring(imagePath.length() - 3);
            JmeSystem.writeImageFile(fileOutputStream, format, texture2D.getImage().getData(0), paintableImage.getWidth(), paintableImage.getHeight());
            fileOutputStream.close();
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public PaintableImage getPaintableImage(){
        return paintableImage;
    }

    public Texture2D getTexture2D(){
        return texture2D;
    }
}
