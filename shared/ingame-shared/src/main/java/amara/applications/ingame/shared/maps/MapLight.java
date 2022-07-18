package amara.applications.ingame.shared.maps;

import com.jme3.math.ColorRGBA;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MapLight {
    @Getter
    private ColorRGBA color;
}
