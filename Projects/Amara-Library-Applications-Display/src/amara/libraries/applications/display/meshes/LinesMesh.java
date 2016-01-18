package amara.libraries.applications.display.meshes;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import amara.libraries.physics.shapes.Vector2D;

public class LinesMesh extends Mesh{

    public LinesMesh(ArrayList<ArrayList<Vector2D>> lines){
        this(0, 0, lines);
    }

    public LinesMesh(float x, float y, ArrayList<ArrayList<Vector2D>> lines){
        this.x = x;
        this.y = y;
        this.lines = lines;
        setMode(Mode.Lines);
        updateMesh();
    }
    private float x;
    private float y;
    private ArrayList<ArrayList<Vector2D>> lines;

    private void updateMesh(){
        int pointsCount = 0;
        for(ArrayList<Vector2D> line : lines){
            pointsCount += line.size();
        }
        short[] indices = new short[pointsCount * 2];
        FloatBuffer positions = BufferUtils.createFloatBuffer(pointsCount * 3);
        FloatBuffer normals = BufferUtils.createFloatBuffer(pointsCount * 3);
        short pointIndex = 0;
        int indicesIndex = 0;
        for(int i=0;i<lines.size();i++){
            ArrayList<Vector2D> line = lines.get(i);
            short firstLinePointIndex = -1;
            for(int r=0;r<line.size();r++){
                Vector2D point = line.get(r);
                if(r == 0){
                    firstLinePointIndex = pointIndex;
                }
                indices[indicesIndex] = pointIndex;
                indicesIndex++;
                if(r < (line.size() - 1)){
                    indices[indicesIndex] = (short) (pointIndex + 1);
                }
                else{
                    indices[indicesIndex] = firstLinePointIndex;
                }
                indicesIndex++;
                positions.put((float) (point.getX() - x));
                positions.put(0);
                positions.put((float) (point.getY() - y));
                normals.put(new float[]{0, 1, 0});
                pointIndex++;
            }
        }
        setBuffer(Type.Index, 2, indices);
        setBuffer(Type.Position, 3, positions);
        setBuffer(Type.Normal, 3, normals);
        updateBound();
    }
}