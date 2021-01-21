package amara.applications.ingame.shared.maps.cameras;

import amara.applications.ingame.shared.maps.MapCamera;
import amara.applications.ingame.shared.maps.MapCamera_Limit;
import amara.applications.ingame.shared.maps.MapCamera_Zoom;
import com.jme3.math.Vector3f;
import lombok.Getter;

@Getter
public class MapCamera_TopDown extends MapCamera {

    public static final String TYPE = "topdown";

    public MapCamera_TopDown(MapCamera_Zoom zoom, Vector3f initialPosition, Vector3f initialDirection, MapCamera_Limit limit) {
        super(TYPE, zoom);
        this.initialPosition = initialPosition;
        this.initialDirection = initialDirection;
        this.limit = limit;
    }
    private Vector3f initialPosition;
    private Vector3f initialDirection;
    private MapCamera_Limit limit;
}
