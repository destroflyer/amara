/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author Carl
 */
public class MapLight{

    public MapLight(ColorRGBA color){
        this.color = color;
    }
    private ColorRGBA color;

    public ColorRGBA getColor(){
        return color;
    }
}
