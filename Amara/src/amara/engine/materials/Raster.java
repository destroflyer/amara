/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.materials;

import amara.game.entitysystem.systems.physics.shapes.PolygonMath.RasterMap;

/**
 *
 * @author Philipp
 */
public class Raster implements RasterMap
{
    private PaintableImage image;
    private float scale;
    private int lower, upper;

    public Raster(PaintableImage image, float scale, int lower, int upper) {
        this.image = image;
        this.scale = scale;
        this.lower = lower;
        this.upper = upper;
    }
    
    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setValue(int x, int y, float value)
    {
        int pixelValue = (int) ((upper * value) + (lower * (1 - value)));
        if(image.getPixel_Red(x, y) < pixelValue){
            image.setPixel_Red(x, y, pixelValue);
        }
    }

    public float getScale() {
        return scale;
    }
    
}
