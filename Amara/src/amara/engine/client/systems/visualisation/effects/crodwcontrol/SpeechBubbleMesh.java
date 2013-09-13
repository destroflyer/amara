/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation.effects.crodwcontrol;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;

/**
 *
 * @author Carl
 */
public class SpeechBubbleMesh extends Mesh{
    
    public SpeechBubbleMesh(){
        float x = -0.5f;
        float y = 2;
        float z = 2.5f;
        float width = 1.8f;
        float height = 1.75f;
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
