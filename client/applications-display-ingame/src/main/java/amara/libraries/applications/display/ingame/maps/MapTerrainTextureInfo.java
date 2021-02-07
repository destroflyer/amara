package amara.libraries.applications.display.ingame.maps;

import amara.applications.ingame.shared.maps.terrain.TerrainSkin_Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MapTerrainTextureInfo {
    private TerrainSkin_Texture texture;
    private int textureIndex;
}
