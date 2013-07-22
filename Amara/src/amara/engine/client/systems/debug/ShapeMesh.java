package amara.engine.client.systems.debug;

import java.nio.FloatBuffer;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import shapes.*;

public class ShapeMesh extends Mesh{

    public ShapeMesh(SimpleConvex simpleConvex){
        this.simpleConvex = simpleConvex;
        setMode(Mode.Lines);
        updateMesh();
    }
    private SimpleConvex simpleConvex;

    protected void updateMesh(){
        Vector2D[] points = simpleConvex.getPoints();
        short[] indices = new short[points.length * 2];
        FloatBuffer positions = BufferUtils.createFloatBuffer(points.length * 3);
        FloatBuffer normals = BufferUtils.createFloatBuffer(points.length * 3);
        int indicesIndex = 0;
        for(int i=0;i<points.length;i++){
            Vector2D point = points[i];
            indices[indicesIndex] = (short) i;
            indicesIndex++;
            if(i < (points.length - 1)){
                indices[indicesIndex] = (short) (i+1);
            }
            else{
                indices[indicesIndex] = 0;
            }
            indicesIndex++;
            positions.put((float) point.getX());
            positions.put(0);
            positions.put((float) point.getY());
            normals.put(new float[]{0, 1, 0});
        }
        setBuffer(Type.Index, 2, indices);
        setBuffer(Type.Position, 3, positions);
        setBuffer(Type.Normal, 3, normals);
        updateBound();
    }
}