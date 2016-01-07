package amara.engine.applications.ingame.client.systems.debug;

import java.nio.FloatBuffer;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class CircleMesh extends Mesh{

    public CircleMesh(float radius, int samples){
        this(Vector3f.ZERO, radius, samples);
    }

    public CircleMesh(Vector3f center, float radius, int samples){
        super();
        this.center = center;
        this.radius = radius;
        this.samples = samples;
        setMode(Mode.Lines);
        updateMesh();
    }
    private Vector3f center;
    private float radius;
    private int samples;

    private void updateMesh(){
        FloatBuffer positions = BufferUtils.createFloatBuffer(samples * 3);
        FloatBuffer normals = BufferUtils.createFloatBuffer(samples * 3);
        short[] indices = new short[samples * 2];
        float rate = FastMath.TWO_PI / (float) samples;
        float angle = 0;
        int indicesIndex = 0;
        for(int i=0;i<samples;i++){
            indices[indicesIndex] = (short) i;
            indicesIndex++;
            if(i < (samples - 1)){
                indices[indicesIndex] = (short) (i+1);
            }
            else{
                indices[indicesIndex] = 0;
            }
            indicesIndex++;
            float x = FastMath.cos(angle) + center.x;
            float z = FastMath.sin(angle) + center.z;
            positions.put(x * radius);
            positions.put(center.y);
            positions.put(z * radius);
            normals.put(new float[]{0, 0, 1});
            angle += rate;
        }
        setBuffer(Type.Index, 2, indices);
        setBuffer(Type.Position, 3, positions);
        setBuffer(Type.Normal, 3, normals);
        setBuffer(Type.TexCoord, 2, new float[] { 0, 0, 1, 1 });
        updateBound();
    }
}