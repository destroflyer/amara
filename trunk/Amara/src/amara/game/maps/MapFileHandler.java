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
import amara.game.entitysystem.systems.physics.shapes.Circle;
import amara.game.entitysystem.systems.physics.shapes.Shape;
import amara.game.entitysystem.systems.physics.shapes.SimpleConvex;
import amara.game.entitysystem.systems.physics.shapes.Vector2D;
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
    
    public static void saveFile(File file, Map map){
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
                Element elementVisual = new Element("visuals");
                elementVisual.setAttribute("modelSkinPath", mapVisual.getModelSkinPath());
                elementVisual.setAttribute("position", generateVector3fText(mapVisual.getPosition()));
                elementVisual.setAttribute("direction", generateVector3fText(mapVisual.getDirection()));
                elementVisual.setAttribute("scale", "" + mapVisual.getScale());
                elementVisuals.addContent(elementVisual);
            }
            root.addContent(elementVisuals);
            FileManager.putFileContent(file.getPath(), new XMLOutputter().outputString(document));
        }catch(Exception ex){
            System.out.println("Error while saving the map: " + ex.toString());
        }
    }

    public static Map loadFile(File file){
        try{
            return loadDocument(new SAXBuilder().build(file));
        }catch(Exception ex){
            System.out.println("Error while loading the map: " + ex.toString());
        }
        return null;
    }

    public static Map loadInputStream(InputStream inputStream){
        try{
            return loadDocument(new SAXBuilder().build(inputStream));
        }catch(Exception ex){
            System.out.println("Error while loading the map: " + ex.toString());
        }
        return null;
    }

    private static Map loadDocument(Document document){
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
            MapPhysicsInformation physicsInformation = new MapPhysicsInformation("testmap", width, height, obstacles);
            map.setPhysicsInformation(physicsInformation);
            Element elementVisuals = root.getChild("visuals");
            List elementVisualsChildren = elementVisuals.getChildren();
            for(int i=0;i<elementVisualsChildren.size();i++){
                Element elementVisual = (Element) elementVisualsChildren.get(i);
                String modelSkinPath = elementVisual.getAttributeValue("modelSkinPath");
                Vector3f position = generateVector3f(elementVisual.getAttributeValue("position"));
                Vector3f direction = generateVector3f(elementVisual.getAttributeValue("direction"));
                float scale = elementVisual.getAttribute("scale").getFloatValue();
                MapVisual mapVisual = new MapVisual(modelSkinPath, position, direction, scale);
                map.getVisuals().addVisual(mapVisual);
            }
            return map;
        }catch(Exception ex){
            System.out.println("Error while loading the map: " + ex.toString());
        }
        return null;
    }
    
    private static String generateVector3fText(Vector3f vector3f){
        return (vector3f.getX() + VECTOR_COORDINATES_SEPARATOR + vector3f.getY() + VECTOR_COORDINATES_SEPARATOR + vector3f.getZ());
    }
    
    private static String generateVector2DText(Vector2D vector2D){
        return (vector2D.getX() + VECTOR_COORDINATES_SEPARATOR + vector2D.getY());
    }
    
    private static Vector3f generateVector3f(String text){
        float[] coordinates = Util.parseToFloatArray(text.split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector3f(coordinates[0], coordinates[1], coordinates[2]);
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
                elementPoint.setText(generateVector2DText(point));
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
}
