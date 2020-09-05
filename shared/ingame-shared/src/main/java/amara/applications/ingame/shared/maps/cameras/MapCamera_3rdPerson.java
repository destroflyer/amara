/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps.cameras;

import amara.applications.ingame.shared.maps.MapCamera;
import amara.applications.ingame.shared.maps.MapCamera_Zoom;
import lombok.Getter;

@Getter
public class MapCamera_3rdPerson extends MapCamera {

    public static final String TYPE = "3rdperson";

    public MapCamera_3rdPerson(MapCamera_Zoom zoom, float initialRotationHorizontal, float initialRotationVertical) {
        super(TYPE, zoom);
        this.initialRotationHorizontal = initialRotationHorizontal;
        this.initialRotationVertical = initialRotationVertical;
    }
    private float initialRotationHorizontal;
    private float initialRotationVertical;
}
