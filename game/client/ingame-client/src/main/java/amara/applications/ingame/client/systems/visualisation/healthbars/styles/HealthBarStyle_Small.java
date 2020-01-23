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
public class HealthBarStyle_Small extends SimpleHealthBarStyle {

    public HealthBarStyle_Small() {
        super(50, 5);
    }

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied) {
        super.drawMaximumHealth(paintableImage, maximumHealth, isAllied);
        for (int x = 1; x < (imageWidth - 1); x++) {
            paintableImage.setPixel(x, 1, backgroundColor_Brighter);
            paintableImage.setPixel(x, 2, backgroundColor);
            paintableImage.setPixel(x, 3, backgroundColor_Darker);
        }
        for (int x = 0; x < imageWidth; x++) {
            paintableImage.setPixel(x, 0, 0, 0, 0, 255);
            paintableImage.setPixel(x, (imageHeight - 1), 0, 0, 0, 255);
        }
        for (int y = 1; y < (imageHeight - 1); y++) {
            paintableImage.setPixel(0, y, 0, 0, 0, 255);
            paintableImage.setPixel((imageWidth - 1), y, 0, 0, 0, 255);
        }
    }

    @Override
    protected void drawCurrentHealth(PaintableImage paintableImage, float healthPortion) {
        int offset = (int) (healthPortion * imageWidth);
        int alpha = 0;
        for (int x = 0; x < imageWidth; x++) {
            if (x > offset) {
                alpha = 255;
            }
            for (int y = 0; y < imageHeight; y++) {
                paintableImage.setPixel(x, y, 0, 0, 0, alpha);
            }
        }
    }
}
