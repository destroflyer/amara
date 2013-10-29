/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Philipp
 */
@Serializable
public class Rectangle extends SimpleConvex {

    public Rectangle(double width, double height) {
        this(0, 0, width, height);
    }

    public Rectangle(double centerX, double centerY, double width, double height) {
        super(new Vector2D[] {
            new Vector2D(width / 2, height / 2),
            new Vector2D(width / 2, -height / 2),
            new Vector2D(-width / 2, -height / 2),
            new Vector2D(-width / 2, height / 2)
        });
        transform.setPosition(centerX, centerY);
    }
    
    
}
