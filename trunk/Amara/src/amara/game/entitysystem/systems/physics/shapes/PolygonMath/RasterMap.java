/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public interface RasterMap
{
    public int getWidth();
    public int getHeight();
    public void setValue(int x, int y, float value);
    public float getScale();
}
