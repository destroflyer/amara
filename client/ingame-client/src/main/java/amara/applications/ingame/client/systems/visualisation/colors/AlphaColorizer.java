package amara.applications.ingame.client.systems.visualisation.colors;

import com.jme3.math.ColorRGBA;

public class AlphaColorizer extends MaterialColorizer {

    public AlphaColorizer(float alpha) {
        this.alpha = alpha;
    }
    private float alpha;

    @Override
    public void modifyColor(ColorRGBA color) {
        color.set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() * alpha);
    }
}
