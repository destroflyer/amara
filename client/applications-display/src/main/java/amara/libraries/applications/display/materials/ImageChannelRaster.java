/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.materials;

import amara.libraries.physics.shapes.PolygonMath.RasterMap;

/**
 *
 * @author Philipp
 */
public class ImageChannelRaster implements RasterMap {

    public ImageChannelRaster(PaintableImage image, float scale, int lower, int upper, int channelIndex) {
        this.image = image;
        this.scale = scale;
        this.lower = lower;
        this.upper = upper;
        this.channelIndex = channelIndex;
    }
    private PaintableImage image;
    private float scale;
    private int lower;
    private int upper;
    private int channelIndex;

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setValue(int x, int y, float value) {
        int pixelValue = (int) ((upper * value) + (lower * (1 - value)));
        int index = image.getIndex(x, y, channelIndex);
        if (image.getPixel(index) < pixelValue) {
            image.setPixel(index, pixelValue);
        }
    }

    public float getScale() {
        return scale;
    }
}
