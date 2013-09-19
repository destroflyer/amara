/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation.meshes;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;

/**
 *
 * @author Carl
 */
public class RectangleMesh extends Mesh{
    
    public RectangleMesh(float x, float y, float z, float width, float height){
        setBuffer(Type.Index, 3, new short[]{
            0, 1, 2,
            0, 2, 3
        });
        setBuffer(Type.Position, 3, new float[]{
            x,          y,          z,
            x + width,  y,          z,
            x + width,  y + height, z,
            x,          y + height, z
        });
        setBuffer(Type.TexCoord, 2, new float[]{
            0, 1,
            1, 1,
            1, 0,
            0, 0
        });
        updateBound();
    }
}
