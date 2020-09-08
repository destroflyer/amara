package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import amara.libraries.applications.display.materials.PaintableImage;

public class HealthBarStyle_Texture extends SimpleHealthBarStyle {

    public HealthBarStyle_Texture(String textureFilePath, int barWidth, int barHeight, int innerX, int innerY, int innerWidth, int innerHeight, int subBarWidth) {
        super(barWidth, barHeight, innerX, innerY, innerWidth, innerHeight);
        this.textureFilePath = textureFilePath;
        this.subBarWidth = subBarWidth;
    }
    private String textureFilePath;
    private int subBarWidth;

    @Override
    protected void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, boolean isAllied) {
        super.draw(paintableImage, maximumHealth, currentHealth, totalShieldAmount, isAllied);
        paintableImage.loadImage(textureFilePath);
        drawBars(paintableImage, 2, 2);
        drawSeparators(paintableImage, currentHealth, totalShieldAmount, subBarWidth);
    }
}
