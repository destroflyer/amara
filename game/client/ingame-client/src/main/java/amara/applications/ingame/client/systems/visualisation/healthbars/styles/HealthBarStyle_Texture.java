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
public class HealthBarStyle_Texture extends SimpleHealthBarStyle {

    public HealthBarStyle_Texture(String textureFilePath, int barWidth, int barHeight, int innerX, int innerY, int innerWidth, int innerHeight, int subBarWidth) {
        super(barWidth, barHeight);
        this.textureFilePath = textureFilePath;
        this.innerX = innerX;
        this.innerY = innerY;
        this.innerWidth = innerWidth;
        this.innerHeight = innerHeight;
        this.subBarWidth = subBarWidth;
    }
    private String textureFilePath;
    private int innerX;
    private int innerY;
    private int innerWidth ;
    private int innerHeight;
    private int subBarWidth;

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied) {
        super.drawMaximumHealth(paintableImage, maximumHealth, isAllied);
        paintableImage.loadImage(textureFilePath);
        int y;
        for (int x = innerX; x < (innerX + innerWidth); x++) {
            for (y = innerY; y < (innerY + 2); y++) {
                paintableImage.setPixel(x, y, backgroundColor_Brighter);
            }
            for (; y < (innerY + 4); y++){
                paintableImage.setPixel(x, y, backgroundColor);
            }
            for (; y < (innerY + innerHeight); y++){
                paintableImage.setPixel(x, y, backgroundColor_Darker);
            }
        }
        float partWidth = (innerWidth / (maximumHealth / subBarWidth));
        int finishedSubBars = 0;
        for (int x = innerX; x <(innerX + innerWidth) ;x++) {
            int subBarIndex = (int) ((x - innerX) / partWidth);
            if (subBarIndex > finishedSubBars) {
                for (y = innerY; y < (innerY + innerHeight); y++) {
                    paintableImage.setPixel(x - 1, y, 0, 0, 0, 255);
                }
                finishedSubBars++;
            }
        }
    }

    @Override
    protected void drawCurrentHealth(PaintableImage paintableImage, float healthPortion) {
        int offset = (int) (healthPortion * imageWidth);
        int alpha;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                alpha = 0;
                if ((x >= innerX) && (x < (innerX + innerWidth)) && (y >= innerY) && (y < (innerY + innerHeight))) {
                    if(x > offset){
                        alpha = 255;
                    }
                }
                paintableImage.setPixel(x, y, 0, 0, 0, alpha);
            }
        }
    }
}
