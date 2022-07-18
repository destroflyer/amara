package amara.applications.ingame.shared.maps.lights;

import amara.applications.ingame.shared.maps.MapLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import lombok.Getter;
import lombok.Setter;

public class MapLight_Directional extends MapLight {

    public MapLight_Directional(ColorRGBA color, Vector3f direction){
        super(color);
        this.direction = direction;
    }
    @Getter
    private Vector3f direction;
    @Getter
    @Setter
    private MapLight_Directional_Shadows shadows;
}
