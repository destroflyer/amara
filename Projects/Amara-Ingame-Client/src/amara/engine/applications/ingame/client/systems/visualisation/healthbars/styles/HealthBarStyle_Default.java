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
public class HealthBarStyle_Default extends HealthBarStyle{

    public HealthBarStyle_Default(){
        super(50, 5, 50, 5);
    }
    public static final Color COLOR_ALLIES = new Color(0.3f, 0.9f, 0.2f);
    public static final Color COLOR_ENEMIES = new Color(0.95f, 0.2f, 0.2f);

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied){
        paintableImage.setBackground(isAllied?COLOR_ALLIES:COLOR_ENEMIES);
        for(int x=0;x<imageWidth;x++){
            paintableImage.setPixel(x, 0, 0, 0, 0, 255);
            paintableImage.setPixel(x, (imageHeight - 1), 0, 0, 0, 255);
        }
        for(int y=0;y<imageHeight;y++){
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
