/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.popups;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ResultingPopupComponent {

    public ResultingPopupComponent() {

    }

    public ResultingPopupComponent(String text) {
        this.text = text;
    }
    private String text;

    public String getText() {
        return text;
    }
}
