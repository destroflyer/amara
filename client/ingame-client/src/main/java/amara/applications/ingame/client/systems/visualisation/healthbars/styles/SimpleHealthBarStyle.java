/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.healthbars.styles;

import java.awt.Color;
import amara.applications.ingame.client.systems.visualisation.healthbars.HealthBarStyle;
import amara.libraries.applications.display.materials.PaintableImage;

/**
 *
 * @author Carl
 */
public abstract class SimpleHealthBarStyle extends HealthBarStyle{

    public SimpleHealthBarStyle(int barWidth, int barHeight){
        super(barWidth, barHeight);
    }
    private static final Color COLOR_ALLIES = new Color(0.3f, 0.9f, 0.2f);
    private static final Color COLOR_ENEMIES = new Color(0.95f, 0.2f, 0.2f);
    protected Color backgroundColor;
    protected Color backgroundColor_Brighter;
    protected Color backgroundColor_Darker;

    @Override
    protected void drawMaximumHealth(PaintableImage paintableImage, float maximumHealth, boolean isAllied){
        backgroundColor = (isAllied?COLOR_ALLIES:COLOR_ENEMIES);
        backgroundColor_Brighter = backgroundColor.brighter().brighter();
        backgroundColor_Darker = backgroundColor.darker();
    }
}
