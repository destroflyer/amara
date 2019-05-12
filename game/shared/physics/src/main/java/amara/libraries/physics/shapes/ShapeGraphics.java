/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

/**
 *
 * @author Philipp
 */
public interface ShapeGraphics
{
    public void drawPoint(Vector2D position);
    
    public void drawCircle(Vector2D position, double radius);
    public void fillCircle(Vector2D position, double radius);
    
    public void drawPolygon(Vector2D[] points);
    public void fillPolygon(Vector2D[] points);
}
