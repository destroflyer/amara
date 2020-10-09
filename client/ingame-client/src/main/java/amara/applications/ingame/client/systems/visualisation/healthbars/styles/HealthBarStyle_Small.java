package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import amara.libraries.applications.display.materials.PaintableImage;

public class HealthBarStyle_Small extends SimpleHealthBarStyle {

    public HealthBarStyle_Small() {
        super(WIDTH, HEIGHT, 1, 1, WIDTH - 2, HEIGHT - 2);
    }
    private static final int WIDTH = 50;
    private static final int HEIGHT = 5;

    @Override
    protected void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, Float manaPortion, boolean isAllied) {
        super.draw(paintableImage, maximumHealth, currentHealth, totalShieldAmount, manaPortion, isAllied);
        drawBars(paintableImage, 1, 1);
        drawBorder(paintableImage);
    }
}
