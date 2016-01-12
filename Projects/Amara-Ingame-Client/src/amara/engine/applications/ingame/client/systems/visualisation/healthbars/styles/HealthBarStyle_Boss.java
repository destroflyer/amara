/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.healthbars.styles;

import java.awt.Color;
import amara.engine.applications.ingame.client.systems.visualisation.healthbars.HealthBarStyle;
import amara.engine.materials.PaintableImage;

/**
 *
 * @author Carl
 */
public class HealthBarStyle_Boss extends HealthBarStyle{

    public HealthBarStyle_Boss(){
        super(150, 25, 150, 25);
    }
    private int subBarWidth = 100;
    private final int innerX = 10;
    private final int innerY = 6;
    private final int innerWidth = 130;
    private final int innerHeight = 8;

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied){
        paintableImage.loadImage("Interface/hud/healthbars/boss_150.png");
        Color color = (isAllied?HealthBarStyle_Default.COLOR_ALLIES:HealthBarStyle_Default.COLOR_ENEMIES);
        for(int x=innerX;x<(innerX + innerWidth);x++){
            for(int y=innerY;y<(innerY + innerHeight);y++){
                paintableImage.setPixel(x, y, color);
            }
        }
        float partWidth = (innerWidth / (maximumHealth / subBarWidth));
        int finishedSubBars = 0;
        for(int x=innerX;x<(innerX + innerWidth);x++){
            int subBarIndex = (int) ((x - innerX) / partWidth);
            if(subBarIndex > finishedSubBars){
                for(int y=innerY;y<(innerY + innerHeight);y++){
                    paintableImage.setPixel(x - 1, y, 0, 0, 0, 255);
                }
                finishedSubBars++;
            }
        }
    }

    @Override
    protected void drawCurrentHealth(PaintableImage paintableImage, float healthPortion){
        int offset = (int) (healthPortion * imageWidth);
        int alpha;
        for(int x=0;x<imageWidth;x++){
            for(int y=0;y<imageHeight;y++){
                alpha = 0;
                if((x >= innerX) && (x < (innerX + innerWidth)) && (y >= innerY) && (y < (innerY + innerHeight))){
                    if(x > offset){
                        alpha = 255;
                    }
                }
                paintableImage.setPixel(x, y, 0, 0, 0, alpha);
            }
        }
    }
}
