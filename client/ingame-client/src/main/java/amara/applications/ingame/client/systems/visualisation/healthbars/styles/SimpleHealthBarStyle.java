package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import java.awt.Color;
import amara.applications.ingame.client.systems.visualisation.healthbars.HealthBarStyle;
import amara.libraries.applications.display.materials.PaintableImage;

public abstract class SimpleHealthBarStyle extends HealthBarStyle {

    public SimpleHealthBarStyle(int barWidth, int barHeight, int innerX, int innerY, int innerWidth, int innerHeight) {
        super(barWidth, barHeight);
        this.innerX = innerX;
        this.innerY = innerY;
        this.innerWidth = innerWidth;
        this.innerHeight = innerHeight;
    }
    private static final Color backgroundColor_CurrentHealth_Ally_Middle = new Color(0.3f, 0.9f, 0.2f);
    private static final Color backgroundColor_CurrentHealth_Ally_Top = backgroundColor_CurrentHealth_Ally_Middle.brighter().brighter();
    private static final Color backgroundColor_CurrentHealth_Ally_Bottom = backgroundColor_CurrentHealth_Ally_Middle.darker();
    private static final Color backgroundColor_CurrentHealth_Enemy_Middle = new Color(0.95f, 0.2f, 0.2f);
    private static final Color backgroundColor_CurrentHealth_Enemy_Top = backgroundColor_CurrentHealth_Enemy_Middle.brighter().brighter();
    private static final Color backgroundColor_CurrentHealth_Enemy_Bottom = backgroundColor_CurrentHealth_Enemy_Middle.darker();
    private static final Color backgroundColor_Shield_Top = Color.WHITE;
    private static final Color backgroundColor_Shield_Middle = new Color(221, 221,221);
    private static final Color backgroundColor_Shield_Bottom = new Color(187, 187,187);
    private static final Color backgroundColor_MissingHealth = new Color(16, 16,16);
    private static final Color colorBorder = Color.BLACK;
    private int innerX;
    private int innerY;
    private int innerWidth;
    private int innerHeight;
    private int currentHealthWidth;
    private int shieldWidth;
    private int missingHealthWidth;
    private Color backgroundColor_Health_Middle;
    private Color backgroundColor_Health_Top;
    private Color backgroundColor_Health_Bottom;

    @Override
    protected void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, boolean isAllied) {
        if (isAllied) {
            backgroundColor_Health_Top = backgroundColor_CurrentHealth_Ally_Top;
            backgroundColor_Health_Middle = backgroundColor_CurrentHealth_Ally_Middle;
            backgroundColor_Health_Bottom = backgroundColor_CurrentHealth_Ally_Bottom;
        } else {
            backgroundColor_Health_Top = backgroundColor_CurrentHealth_Enemy_Top;
            backgroundColor_Health_Middle = backgroundColor_CurrentHealth_Enemy_Middle;
            backgroundColor_Health_Bottom = backgroundColor_CurrentHealth_Enemy_Bottom;
        }
        float totalMaximumAmount = Math.max(currentHealth + totalShieldAmount, maximumHealth);
        currentHealthWidth = Math.round((currentHealth / totalMaximumAmount) * innerWidth);
        shieldWidth = Math.round((totalShieldAmount / totalMaximumAmount) * innerWidth);
        missingHealthWidth = (innerWidth - (currentHealthWidth + shieldWidth));
    }

    protected void drawBars(PaintableImage paintableImage, int heightTop, int heightMiddle) {
        for (int localX = 0; localX < currentHealthWidth; localX++) {
            drawBar(paintableImage, innerX + localX, heightTop, heightMiddle, backgroundColor_Health_Top, backgroundColor_Health_Middle, backgroundColor_Health_Bottom);
        }
        for (int localX = 0; localX < shieldWidth; localX++) {
            drawBar(paintableImage, innerX + currentHealthWidth + localX, heightTop, heightMiddle, backgroundColor_Shield_Top, backgroundColor_Shield_Middle, backgroundColor_Shield_Bottom);
        }
        for (int localX = 0; localX < missingHealthWidth; localX++) {
            for (int y = innerY; y < (innerY + innerHeight); y++) {
                paintableImage.setPixel(innerX + currentHealthWidth + shieldWidth + localX, y, backgroundColor_MissingHealth);
            }
        }
    }

    private void drawBar(PaintableImage paintableImage, int x, int heightTop, int heightMiddle, Color colorTop, Color colorMiddle, Color colorBottom) {
        for (int y = innerY; y < innerY + heightTop; y++) {
            paintableImage.setPixel(x, y, colorTop);
        }
        for (int y = innerY + heightTop; y < innerY + heightTop + heightMiddle; y++) {
            paintableImage.setPixel(x, y, colorMiddle);
        }
        for (int y = innerY + heightTop + heightMiddle; y < (innerY + innerHeight); y++) {
            paintableImage.setPixel(x, y, colorBottom);
        }
    }

    protected void drawSeparators(PaintableImage paintableImage, float currentHealth, float totalShieldAmount, int subBarWidth) {
        float partWidth = ((currentHealthWidth + shieldWidth) / ((currentHealth + totalShieldAmount) / subBarWidth));
        int finishedSubBars = 0;
        for (int localX = 0; localX < (currentHealthWidth + shieldWidth); localX++) {
            int subBarIndex = (int) (localX / partWidth);
            if (subBarIndex > finishedSubBars) {
                for (int y = innerY; y < (innerY + (innerHeight / 2)); y++) {
                    paintableImage.setPixel(innerX + localX, y, colorBorder);
                }
                finishedSubBars++;
            }
        }
    }

    protected void drawBorder(PaintableImage paintableImage) {
        for (int x = 0; x < imageWidth; x++) {
            paintableImage.setPixel(x, 0, colorBorder);
            paintableImage.setPixel(x, (imageHeight - 1), colorBorder);
        }
        for (int y = 1; y < (imageHeight - 1); y++) {
            paintableImage.setPixel(0, y, colorBorder);
            paintableImage.setPixel((imageWidth - 1), y, colorBorder);
        }
    }
}
