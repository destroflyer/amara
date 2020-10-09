package amara.applications.ingame.client.systems.visualisation.healthbars;

import amara.libraries.applications.display.materials.PaintableImage;

public abstract class HealthBarStyle {

    public HealthBarStyle(int barWidth, int barHeight) {
        this(barWidth, barHeight, barWidth, barHeight);
    }

    public HealthBarStyle(int barWidth, int barHeight, int imageWidth, int imageHeight) {
        this.barWidth = barWidth;
        this.barHeight = barHeight;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }
    protected final int barWidth;
    protected final int barHeight;
    protected final int imageWidth;
    protected final int imageHeight;

    protected abstract void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, Float manaPortion, boolean isAllied);

    public int getBarWidth() {
        return barWidth;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
