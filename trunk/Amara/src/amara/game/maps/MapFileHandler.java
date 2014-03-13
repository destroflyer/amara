/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;
import com.jme3.math.Vector3f;
import amara.Util;
import amara.engine.files.FileManager;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.maps.visuals.*;
import com.jme3.math.Vector2f;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Carl
 */
public class MapFileHandler{
    
    private static final String VECTOR_COORDINATES_SEPARATOR = ",";
    public static String FILE_EXTENSION = "xml";
    public static FileFilter FILE_FILTER = new FileFilter(){

        @Override
        public boolean accept(File file){
            return (file.isDirectory() || file.getPath().toLowerCase().endsWith("." + FILE_EXTENSION));
        }

        @Override
        public String getDescription(){
            return "Amara map file (*." + FILE_EXTENSION + ")";
        }
    };
    
    public static void saveFile(Map map, File file){
        try{
            Element root = new Element("map");
            root.setAttribute("author", System.getProperty("user.name"));
            root.setAttribute("date", "" + System.currentTimeMillis());
            Class mapClass = map.getClass();
            if(mapClass == TestMapToWrite.class){
                mapClass = TestMap.class;
            }
            root.setAttribute("class", mapClass.getName());
            Document document = new Document(root);
            Element elementPhysics = new Element("physics");
            elementPhysics.setAttribute("width", "" + map.getPhysicsInformation().getWidth());
            elementPhysics.setAttribute("height", "" + map.getPhysicsInformation().getHeight());
            Element elementObstacles = new Element("obstacles");
            for(Shape shape : map.getPhysicsInformation().getObstacles()){
                Element elementShape = generateElement(shape);
                if(elementShape != null){
                    elementObstacles.addContent(elementShape);
                }
            }
            elementPhysics.addContent(elementObstacles);
            root.addContent(elementPhysics);
            Element elementVisuals = new Element("visuals");
            for(MapVisual mapVisual : map.getVisuals().getMapVisuals()){
                Element elementVisual = null;
                if(mapVisual instanceof ModelVisual){
                    ModelVisual modelVisual = (ModelVisual) mapVisual;
                    elementVisual = new Element("model");
                    elementVisual.setAttribute("modelSkinPath", modelVisual.getModelSkinPath());
                    elementVisual.setAttribute("position", generateVectorText(modelVisual.getPosition()));
                    elementVisual.setAttribute("direction", generateVectorText(modelVisual.getDirection()));
                    elementVisual.setAttribute("scale", "" + modelVisual.getScale());
                }
                else if(mapVisual instanceof WaterVisual){
                    WaterVisual waterVisual = (WaterVisual) mapVisual;
                    elementVisual = new Element("water");
                    elementVisual.setAttribute("position", generateVectorText(waterVisual.getPosition()));
                    elementVisual.setAttribute("size", generateVectorText(waterVisual.getSize()));
                }
                if(elementVisual != null){
                    elementVisuals.addContent(elementVisual);
                }
            }
            root.addContent(elementVisuals);
            FileManager.putFileContent(file.getPath(), new XMLOutputter().outputString(document));
        }catch(Exception ex){
            System.out.println("Error while saving the map: " + ex.toString());
        }
    }

    public static Map load(String mapName){
        try{
            InputStream inputStream = Util.getResourceInputStrean("/Maps/" + mapName + "/map.xml");
            Map map = load(new SAXBuilder().build(inputStream));
            map.setName(mapName);
            return map;
        }catch(Exception ex){
            System.out.println("Error while loading the map: " + ex.toString());
        }
        return null;
    }

    private static Map load(Document document){
        try{
            Element root = document.getRootElement();
            Map map = Util.createObjectByClassName(root.getAttributeValue("class"), Map.class);
            Element elementPhysics = root.getChild("physics");
            int width = elementPhysics.getAttribute("width").getIntValue();
            int height = elementPhysics.getAttribute("height").getIntValue();
            Element elementObstacles = elementPhysics.getChild("obstacles");
            List elementObstaclesChildren = elementObstacles.getChildren();
            ArrayList<Shape> obstacles = new ArrayList<Shape>();
            for(int i=0;i<elementObstaclesChildren.size();i++){
                Element elementShape = (Element) elementObstaclesChildren.get(i);
                Shape shape = generateShape(elementShape);
                obstacles.add(shape);
            }
            MapPhysicsInformation physicsInformation = new MapPhysicsInformation(width, height, obstacles);
            map.setPhysicsInformation(physicsInformation);
            Element elementVisuals = root.getChild("visuals");
            List elementVisualsChildren = elementVisuals.getChildren();
            for(int i=0;i<elementVisualsChildren.size();i++){
                Element elementVisual = (Element) elementVisualsChildren.get(i);
                MapVisual mapVisual = null;
                if(elementVisual.getName().equals("model")){
                    String modelSkinPath = elementVisual.getAttributeValue("modelSkinPath");
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector3f direction = generateVector3f(elementVisual.getAttributeValue("direction"));
                    float scale = elementVisual.getAttribute("scale").getFloatValue();
                    mapVisual = new ModelVisual(modelSkinPath, position, direction, scale);
                }
                else if(elementVisual.getName().equals("water")){
                    Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                    Vector2f size = generateVector2f(elementVisual.getAttributeValue("size"));
                    mapVisual = new WaterVisual(position, size);
                }
                if(mapVisual != null){
                    map.getVisuals().addVisual(mapVisual);
                }
            }
            return map;
        }catch(Exception ex){
            System.out.println("Error while loading the map: " + ex.toString());
        }
        return null;
    }
    
    private static Vector2D generateVector2D(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector2D(coordinates[0], coordinates[1]);
    }
    
    private static Element generateElement(Shape shape){
        Element element = null;
        if(shape instanceof Circle){
            Circle circle = (Circle) shape;
            element = new Element("circle");
            element.setAttribute("radius", "" + circle.getBoundRadius());
        }
        else if(shape instanceof SimpleConvex){
            SimpleConvex simpleConvex = (SimpleConvex) shape;
            element = new Element("simpleConvex");
            Element elementBase = new Element("base");
            for(Vector2D point : simpleConvex.getBase()){
                Element elementPoint = new Element("point");
                elementPoint.setText(generateVectorText(point));
                elementBase.addContent(elementPoint);
            }
            element.addContent(elementBase);
        }
        Element elementTransform = new Element("transform");
        elementTransform.setAttribute("x", "" + shape.getTransform().getX());
        elementTransform.setAttribute("y", "" + shape.getTransform().getY());
        elementTransform.setAttribute("direction", "" + shape.getTransform().getRadian());
        elementTransform.setAttribute("scale", "" + shape.getTransform().getScale());
        element.addContent(elementTransform);
        return element;
    }
    
    private static Shape generateShape(Element element){
        Shape shape = null;
        if(element.getName().equals("circle")){
            double radius = Double.parseDouble(element.getAttributeValue("radius"));
            shape = new Circle(radius);
        }
        else if(element.getName().equals("simpleConvex")){
            Element elementBase = element.getChild("base");
            List elementBaseChildren = elementBase.getChildren();
            Vector2D[] base = new Vector2D[elementBaseChildren.size()];
            for(int i=0;i<elementBaseChildren.size();i++){
                Element elementPoint = (Element) elementBaseChildren.get(i);
                base[i] = generateVector2D(elementPoint.getText());
            }
            shape = new SimpleConvex(base);
        }
        Element elementTransform = element.getChild("transform");
        shape.getTransform().setX(Double.parseDouble(elementTransform.getAttributeValue("x")));
        shape.getTransform().setY(Double.parseDouble(elementTransform.getAttributeValue("y")));
        shape.getTransform().setRadian(Double.parseDouble(elementTransform.getAttributeValue("direction")));
        shape.getTransform().setScale(Double.parseDouble(elementTransform.getAttributeValue("scale")));
        return shape;
    }
    
    private static String generateVectorText(Vector2f vector2f){
        return (vector2f.getX() + VECTOR_COORDINATES_SEPARATOR + vector2f.getY());
    }
    
    private static String generateVectorText(Vector3f vector3f){
        return (vector3f.getX() + VECTOR_COORDINATES_SEPARATOR + vector3f.getY() + VECTOR_COORDINATES_SEPARATOR + vector3f.getZ());
    }
    
    private static String generateVectorText(Vector2D vector2D){
        return (vector2D.getX() + VECTOR_COORDINATES_SEPARATOR + vector2D.getY());
    }
    
    private static Vector2f generateVector2f(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector2f(coordinates[0], coordinates[1]);
    }
    
    private static Vector3f generateVector3f(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector3f(coordinates[0], coordinates[1], coordinates[2]);
    }
}
