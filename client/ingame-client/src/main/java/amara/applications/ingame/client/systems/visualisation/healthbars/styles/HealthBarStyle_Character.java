package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import amara.libraries.applications.display.materials.PaintableImage;

public class HealthBarStyle_Character extends SimpleHealthBarStyle {

    public HealthBarStyle_Character() {
        super(WIDTH, HEIGHT, 1, 1, WIDTH - 2, HEIGHT - 2);
    }
    private static final int WIDTH = 107;
    private static final int HEIGHT = 18;

    @Override
    protected void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, Float manaPortion, boolean isAllied) {
        super.draw(paintableImage, maximumHealth, currentHealth, totalShieldAmount, manaPortion, isAllied);
        drawBars(paintableImage, 4, 4);
        drawHealthSeparators(paintableImage, currentHealth, totalShieldAmount, 100);
        drawBorder(paintableImage);
    }
}
