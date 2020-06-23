/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Carl
 */
@AllArgsConstructor
@Getter
public class MapCamera_Zoom {
    private float interval;
    private float initialDistance;
    private float minimumDistance;
    private float maximumDistance;
}
