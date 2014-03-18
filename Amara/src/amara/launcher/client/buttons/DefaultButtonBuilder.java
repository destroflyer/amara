/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.buttons;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Carl
 */
public class DefaultButtonBuilder extends ImageButtonBuilder{

    public DefaultButtonBuilder(String buttonName, String text){
        super(buttonName, text);
        setFont(Font.BOLD, 12, Color.WHITE);
    }
}
