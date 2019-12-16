package amara.applications.ingame.client.systems.visualisation.colors;

import com.jme3.math.ColorRGBA;

public class StealthColorizer extends MaterialColorizer {

    @Override
    public void modifyColor(ColorRGBA color) {
        color.set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() * 0.2f);
    }
}
