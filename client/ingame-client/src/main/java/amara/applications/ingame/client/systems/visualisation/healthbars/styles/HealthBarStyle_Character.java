/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import amara.libraries.applications.display.materials.PaintableImage;

/**
 *
 * @author Carl
 */
public class HealthBarStyle_Character extends SimpleHealthBarStyle{

    public HealthBarStyle_Character(){
        super(70, 8);
    }
    private int subBarWidth = 100;

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied){
        super.drawMaximumHealth(paintableImage, maximumHealth, isAllied);
        for(int x=1;x<(imageWidth - 1);x++){
            for(int y=1;y<3;y++){
                paintableImage.setPixel(x, y, backgroundColor_Brighter);
            }
            paintableImage.setPixel(x, 3, backgroundColor);
            for(int y=4;y<(imageHeight - 1);y++){
                paintableImage.setPixel(x, y, backgroundColor_Darker);
            }
        }
        float partWidth = (imageWidth / (maximumHealth / subBarWidth));
        int finishedSubBars = 0;
        for(int x=1;x<(imageWidth - 1);x++){
            int subBarIndex = (int) (x / partWidth);
            if(subBarIndex > finishedSubBars){
                for(int y=1;y<(imageHeight - 1);y++){
                    paintableImage.setPixel(x - 1, y, 0, 0, 0, 255);
                }
                finishedSubBars++;
            }
        }
        for(int x=0;x<imageWidth;x++){
            paintableImage.setPixel(x, 0, 0, 0, 0, 255);
            paintableImage.setPixel(x, (imageHeight - 1), 0, 0, 0, 255);
        }
        for(int y=1;y<(imageHeight - 1);y++){
            paintableImage.setPixel(0, y, 0, 0, 0, 255);
            paintableImage.setPixel((imageWidth - 1), y, 0, 0, 0, 255);
        }
    }

    @Override
    protected void drawCurrentHealth(PaintableImage paintableImage, float healthPortion){
        int offset = (int) (healthPortion * imageWidth);
        int alpha = 0;
        for(int x=0;x<imageWidth;x++){
            if(x > offset){
                alpha = 255;
            }
            for(int y=0;y<imageHeight;y++){
                paintableImage.setPixel(x, y, 0, 0, 0, alpha);
            }
        }
    }
}