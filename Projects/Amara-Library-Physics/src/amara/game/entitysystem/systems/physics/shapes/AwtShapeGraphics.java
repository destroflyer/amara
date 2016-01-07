/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import java.awt.*;

/**
 *
 * @author Philipp
 */
public class AwtShapeGraphics implements ShapeGraphics
{
    private Graphics graphics = null;

    public AwtShapeGraphics()
    {
    }

    public AwtShapeGraphics(Graphics graphics)
    {
        this.graphics = graphics;
    }

    public Graphics getGraphics()
    {
        return graphics;
    }

    public void setGraphics(Graphics graphics)
    {
        this.graphics = graphics;
    }

    @Override
    public void drawCircle(Vector2D position, double radius)
    {
        graphics.drawOval((int)(position.getX() - radius), (int)(position.getY() - radius), (int)(2 * radius), (int)(2 * radius));
    }

    @Override
    public void fillCircle(Vector2D position, double radius)
    {
        graphics.fillOval((int)(position.getX() - radius), (int)(position.getY() - radius), (int)(2 * radius), (int)(2 * radius));
    }

    @Override
    public void drawPolygon(Vector2D[] points)
    {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        toDrawPoly(xPoints, yPoints, points);
        graphics.drawPolygon(xPoints, yPoints, points.length);
    }

    @Override
    public void fillPolygon(Vector2D[] points)
    {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        toDrawPoly(xPoints, yPoints, points);
        graphics.fillPolygon(xPoints, yPoints, points.length);
    }
    
    private void toDrawPoly(int[] xPoints, int[] yPoints, Vector2D[] points)
    {
        for (int i = 0; i < points.length; i++)
        {
            xPoints[i] = (int)points[i].getX();
            yPoints[i] = (int)points[i].getY();
        }
    }

    public void drawPoint(Vector2D position) {
        graphics.drawOval((int)(position.getX() - 0.5), (int)(position.getY() - 0.5), 1, 1);
    }
    
}
