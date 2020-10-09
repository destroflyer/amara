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
    private static final Color backgroundColor_Missing = new Color(16, 16,16);
    private static final Color backgroundColor_Mana_Top = new Color(99, 153, 185);
    private static final Color backgroundColor_Mana_Middle = new Color(73, 125, 151);
    private static final Color backgroundColor_Mana_Bottom = new Color(32, 100, 132);
    private static final Color colorBorder = Color.BLACK;
    private Color backgroundColor_Health_Top;
    private Color backgroundColor_Health_Middle;
    private Color backgroundColor_Health_Bottom;
    private int innerX;
    private int innerY;
    private int innerWidth;
    private int innerHeight;
    private int currentHealthWidth;
    private int shieldWidth;
    private int missingHealthWidth;
    private int healthHeight;
    private int healthSeparatorHeight;
    private Integer currentManaWidth;
    private Integer missingManaWidth;

    @Override
    protected void draw(PaintableImage paintableImage, float maximumHealth, float currentHealth, float totalShieldAmount, Float manaPortion, boolean isAllied) {
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
        healthHeight = ((manaPortion != null) ? (innerHeight - 5) : innerHeight);
        healthSeparatorHeight = Math.round(healthHeight / 2f);
        if (manaPortion != null) {
            currentManaWidth = Math.round(manaPortion * innerWidth);
            missingManaWidth = (innerWidth - currentManaWidth);
        } else {
            currentManaWidth = null;
            missingManaWidth = null;
        }
    }

    protected void drawBars(PaintableImage paintableImage, int heightTop, int heightMiddle) {
        for (int localX = 0; localX < currentHealthWidth; localX++) {
            drawBar(paintableImage, innerX + localX, innerY, heightTop, heightMiddle, healthHeight, backgroundColor_Health_Top, backgroundColor_Health_Middle, backgroundColor_Health_Bottom);
        }
        for (int localX = 0; localX < shieldWidth; localX++) {
            drawBar(paintableImage, innerX + currentHealthWidth + localX, innerY, heightTop, heightMiddle, healthHeight, backgroundColor_Shield_Top, backgroundColor_Shield_Middle, backgroundColor_Shield_Bottom);
        }
        for (int localX = 0; localX < missingHealthWidth; localX++) {
            for (int y = innerY; y < (innerY + healthHeight); y++) {
                paintableImage.setPixel(innerX + currentHealthWidth + shieldWidth + localX, y, backgroundColor_Missing);
            }
        }
        if (currentManaWidth != null) {
            int manaY = (innerY + healthHeight + 1);
            for (int localX = 0; localX < currentManaWidth; localX++) {
                drawBar(paintableImage, innerX + localX, manaY, 2, 1, 4, backgroundColor_Mana_Top, backgroundColor_Mana_Middle, backgroundColor_Mana_Bottom);
            }
            for (int localX = 0; localX < missingManaWidth; localX++) {
                paintableImage.setPixel(innerX + currentManaWidth + localX, manaY, backgroundColor_Missing);
                paintableImage.setPixel(innerX + currentManaWidth + localX, manaY + 1, backgroundColor_Missing);
                paintableImage.setPixel(innerX + currentManaWidth + localX, manaY + 2, backgroundColor_Missing);
                paintableImage.setPixel(innerX + currentManaWidth + localX, manaY + 3, backgroundColor_Missing);
            }
        }
    }

    private void drawBar(PaintableImage paintableImage, int x, int y, int heightTop, int heightMiddle, int totalHeight, Color colorTop, Color colorMiddle, Color colorBottom) {
        for (int currentY = y; currentY < y + heightTop; currentY++) {
            paintableImage.setPixel(x, currentY, colorTop);
        }
        for (int currentY = y + heightTop; currentY < y + heightTop + heightMiddle; currentY++) {
            paintableImage.setPixel(x, currentY, colorMiddle);
        }
        for (int currentY = y + heightTop + heightMiddle; currentY < y + totalHeight; currentY++) {
            paintableImage.setPixel(x, currentY, colorBottom);
        }
    }

    protected void drawHealthSeparators(PaintableImage paintableImage, float currentHealth, float totalShieldAmount, int subBarWidth) {
        float partWidth = ((currentHealthWidth + shieldWidth) / ((currentHealth + totalShieldAmount) / subBarWidth));
        int finishedSubBars = 0;
        for (int localX = 0; localX < (currentHealthWidth + shieldWidth); localX++) {
            int subBarIndex = (int) (localX / partWidth);
            if (subBarIndex > finishedSubBars) {
                for (int y = innerY; y < (innerY + healthSeparatorHeight); y++) {
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
        if (currentManaWidth != null) {
            for (int x = 1; x < (imageWidth - 1); x++) {
                paintableImage.setPixel(x, (imageHeight - 6), colorBorder);
            }
        }
    }
}
