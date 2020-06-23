/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import com.jme3.math.Vector2f;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MapCamera_Limit {
    private Vector2f minimum;
    private Vector2f maximum;
}
