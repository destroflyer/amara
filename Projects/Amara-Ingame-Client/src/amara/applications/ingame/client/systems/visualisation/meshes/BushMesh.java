/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.meshes;

import java.util.LinkedList;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import amara.core.Util;
import amara.libraries.physics.shapes.Circle;
import amara.libraries.physics.shapes.SimpleConvexPolygon;
import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Carl
 */
public class BushMesh extends Mesh{

    public BushMesh(SimpleConvexPolygon simpleConvexPolygon, float bladeInterval, float bladeWidth, float bladeHeight){
        this.bladeInterval = bladeInterval;
        this.bladeWidth = bladeWidth;
        this.bladeHeight = bladeHeight;
        float shapeX = (float) simpleConvexPolygon.getTransform().extractX();
        float shapeY = (float) simpleConvexPolygon.getTransform().extractY();
        Vector2D[] localPoints = simpleConvexPolygon.getLocalPoints();
        //Edges
        Vector2D lastPoint = localPoints[localPoints.length - 1];
        for(Vector2D point : localPoints){
            float x1 = (float) lastPoint.getX();
            float y1 = (float) lastPoint.getY();
            float x2 = (float) point.getX();
            float y2 = (float) point.getY();
            int parts = (int) Math.ceil(point.distance(lastPoint) / bladeInterval);
            for(int i=0;i<parts;i++){
                float progress = (((float) i) / (parts - 1));
                float x = (x1 + (progress * (x2 - x1)));
                float y = (y1 + (progress * (y2 - y1)));
                addBlade(x, y);
                lastPoint = point;
            }
        }
        //Inner
        Circle boundCircle = simpleConvexPolygon.getBoundCircle();
        float boundX = (float) (boundCircle.getLocalPosition().getX() - boundCircle.getLocalRadius());
        float boundY = (float) (boundCircle.getLocalPosition().getY() - boundCircle.getLocalRadius());
        float boundWidth = (float) (2 * boundCircle.getLocalRadius());
        float boundHeight = (float) (2 * boundCircle.getLocalRadius());
        int partsX = (int) Math.ceil(boundWidth / bladeInterval);
        int partsY = (int) Math.ceil(boundHeight / bladeInterval);
        for(int i=0;i<partsX;i++){
            for(int r=0;r<partsY;r++){
                float progressX = (((float) i) / (partsX - 1));
                float progressY = (((float) r) / (partsX - 1));
                float x = (boundX + (progressX * boundWidth));
                float y = (boundY + (progressY * boundHeight));
                if(!isTooNearToBlade(x, y, bladeInterval)){
                    Vector2D globalPoint = new Vector2D(shapeX + x, shapeY + y);
                    if(simpleConvexPolygon.contains(globalPoint)){
                        addBlade(x, y);
                    }
                }
            }
        }
        setBuffer(Type.Index, 3, Util.convertToArray_Short(indices));
        setBuffer(Type.Position, 3, Util.convertToArray_Float(positions));
        setBuffer(Type.Normal, 3, Util.convertToArray_Float(normals));
        setBuffer(Type.TexCoord, 2, Util.convertToArray_Float(textureCoordinates));
        updateBound();
    }
    private float bladeInterval;
    private float bladeWidth;
    private float bladeHeight;
    private short currentIndex = 0;
    private LinkedList<Vector2f> bladePositions = new LinkedList<Vector2f>();
    private LinkedList<Short> indices = new LinkedList<Short>();
    private LinkedList<Float> positions = new LinkedList<Float>();
    private LinkedList<Float> normals = new LinkedList<Float>();
    private LinkedList<Float> textureCoordinates = new LinkedList<Float>();
    
    private void addBlade(float x, float y){
        bladePositions.add(new Vector2f(x, y));
        for(int i=0;i<3;i++){
            indices.add((short) (currentIndex + 1));
            indices.add(currentIndex);
            indices.add((short) (currentIndex + 2));
            currentIndex += 3;
            textureCoordinates.add(0f);
            textureCoordinates.add(1f);
            textureCoordinates.add(1f);
            textureCoordinates.add(1f);
            textureCoordinates.add(0.5f);
            textureCoordinates.add(0f);
        }
        float distanceToPoints = ((FastMath.sqrt(3) / 3) * bladeWidth);
        //Side 1
        positions.add(x - distanceToPoints);
        positions.add(0f);
        positions.add(y + distanceToPoints);
        positions.add(x);
        positions.add(0f);
        positions.add(y - distanceToPoints);
        positions.add(x);
        positions.add(bladeHeight);
        positions.add(y);
        for(int i=0;i<3;i++){
            normals.add(1f);
            normals.add(0f);
            normals.add(-1f);
        }
        //Side 2
        positions.add(x);
        positions.add(0f);
        positions.add(y - distanceToPoints);
        positions.add(x + distanceToPoints);
        positions.add(0f);
        positions.add(y + distanceToPoints);
        positions.add(x);
        positions.add(bladeHeight);
        positions.add(y);
        for(int i=0;i<3;i++){
            normals.add(-1f);
            normals.add(0f);
            normals.add(-1f);
        }
        //Side 3
        positions.add(x + distanceToPoints);
        positions.add(0f);
        positions.add(y + distanceToPoints);
        positions.add(x - distanceToPoints);
        positions.add(0f);
        positions.add(y + distanceToPoints);
        positions.add(x);
        positions.add(bladeHeight);
        positions.add(y);
        for(int i=0;i<3;i++){
            normals.add(0f);
            normals.add(0f);
            normals.add(1f);
        }
    }
    
    private boolean isTooNearToBlade(float x, float y, float maximumDistance){
        for(Vector2f bladePosition : bladePositions){
            if(FastMath.sqrt(bladePosition.distanceSquared(x, y)) < maximumDistance){
                return true;
            }
        }
        return false;
    }
}
