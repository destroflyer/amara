/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.templates;

import java.lang.reflect.Field;
import java.util.ArrayList;
import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.libraries.entitysystem.synchronizing.*;
import amara.libraries.entitysystem.synchronizing.fieldserializers.*;
import amara.libraries.entitysystem.templates.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.PolygonMath.*;
import org.jdom.Element;

/**
 *
 * @author Carl
 */
public class CustomSerializer_Ingame{
    
    public static void registerClasses(){
        BitstreamClassManager.getInstance().register(
            ArrayList.class,
            //physics/HitboxComponent
            Circle.class,
            Rectangle.class,
            RegularCyclic.class,
            Shape.class,
            ConvexShape.class,
            SimpleConvexPolygon.class,
            PointShape.class,
            Transform2D.class,
            Vector2D.class,
            PolygonShape.class,
                BoundRectangle.class,
                Polygon.class,
                    SetPolygon.class,
                    HolePolygon.class,
                    SimplePolygon.class,
            //units/DamageHistoryComponent
            DamageHistoryComponent.DamageHistoryEntry.class
        );
        ComponentsRegistrator.registerComponents();
        try{
            ComponentSerializer.registerFieldSerializer(new Field[]{ 
                Vector2f.class.getDeclaredField("x"),
                Vector2f.class.getDeclaredField("y")
            }, new FieldSerializer_Float(20, 8));
            ComponentSerializer.registerFieldSerializer(new Field[]{ 
                Vector2D.class.getDeclaredField("x"),
                Vector2D.class.getDeclaredField("y"),
                Transform2D.class.getDeclaredField("scalecos"),
                Transform2D.class.getDeclaredField("scalesin"),
                Transform2D.class.getDeclaredField("x"),
                Transform2D.class.getDeclaredField("y"),
                Circle.class.getDeclaredField("localRadius"),
            }, new FieldSerializer_DoubleAsFloat(20, 8));
        }catch(NoSuchFieldException ex){
            ex.printStackTrace();
        }
        XMLTemplateManager xmlTemplateManager = XMLTemplateManager.getInstance();
        //physics
        xmlTemplateManager.registerComponent(HitboxComponent.class, new XMLComponentConstructor<HitboxComponent>("hitbox"){

            @Override
            public HitboxComponent construct(){
                Shape shape = null;
                Element childElement = (Element) element.getChildren().get(0);
                String shapeType = childElement.getName();
                double x = 0;
                String xText = childElement.getAttributeValue("x");
                if(xText != null){
                    x = Double.parseDouble(xText);
                }
                double y = 0;
                String yText = childElement.getAttributeValue("y");
                if(yText != null){
                    y = Double.parseDouble(yText);
                }
                if(shapeType.equals("regularCyclic")){
                    int edges = Integer.parseInt(childElement.getAttributeValue("edges"));
                    double radius = Double.parseDouble(childElement.getAttributeValue("radius"));
                    shape = new RegularCyclic(edges, radius);
                }
                else if(shapeType.equals("circle")){
                    double radius = Double.parseDouble(childElement.getAttributeValue("radius"));
                    shape = new Circle(x, y, radius);
                }
                else if(shapeType.equals("rectangle")){
                    double width = Double.parseDouble(childElement.getAttributeValue("width"));
                    double height = Double.parseDouble(childElement.getAttributeValue("height"));
                    shape = new Rectangle(x, y, width, height);
                }
                else if(shapeType.equals("point")){
                    Vector2D localPoint = new Vector2D();
                    String[] positionCoordinates = element.getText().split(",");
                    if(positionCoordinates.length > 1){
                        double localPointX = Double.parseDouble(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[0]));
                        double localPointY = Double.parseDouble(xmlTemplateManager.parseValue(entityWorld, positionCoordinates[1]));
                        localPoint = new Vector2D(localPointX, localPointY);
                    }
                    shape = new PointShape(localPoint);
                }
                if(shape == null){
                    throw new UnsupportedOperationException("Unsupported shape type '" + shapeType + "'.");
                }
                return new HitboxComponent(shape);
            }
        });
        //spawns
        xmlTemplateManager.registerComponent(SpawnTemplateComponent.class, new XMLComponentConstructor<SpawnTemplateComponent>("spawnTemplate"){

            @Override
            public SpawnTemplateComponent construct(){
                String[] templates = element.getText().split("\\|");
                for(int i=0;i<templates.length;i++){
                    templates[i] = xmlTemplateManager.parseTemplate(entityWorld, templates[i]);
                }
                return new SpawnTemplateComponent(templates);
            }
        });
        //spells
        xmlTemplateManager.registerComponent(CastTypeComponent.class, new XMLComponentConstructor<CastTypeComponent>("castType"){

            @Override
            public CastTypeComponent construct(){
                return new CastTypeComponent(CastTypeComponent.CastType.valueOf(element.getText().toUpperCase()));
            }
        });
        //units
        xmlTemplateManager.registerComponent(CollisionGroupComponent.class, new XMLComponentConstructor<CollisionGroupComponent>("collisionGroup"){

            @Override
            public CollisionGroupComponent construct(){
                long collisionGroups = getCollisionBitMask(element.getAttributeValue("group"));
                long collidesWithGroups = getCollisionBitMask(element.getAttributeValue("collidesWith"));
                return new CollisionGroupComponent(collisionGroups, collidesWithGroups);
            }
            
            private long getCollisionBitMask(String text){
                long bitMask = 0;
                String[] groupNames = text.split("\\|");
                for(String groupName : groupNames){
                    bitMask |= getCollisionGroup(groupName);
                }
                return bitMask;
            }
            
            private long getCollisionGroup(String name){
                if(name.equals("none")){
                    return CollisionGroupComponent.COLLISION_GROUP_NONE;
                }
                else if(name.equals("map")){
                    return CollisionGroupComponent.COLLISION_GROUP_MAP;
                }
                else if(name.equals("units")){
                    return CollisionGroupComponent.COLLISION_GROUP_UNITS;
                }
                else if(name.equals("spells")){
                    return CollisionGroupComponent.COLLISION_GROUP_SPELLS;
                }
                else if(name.equals("all")){
                    return CollisionGroupComponent.COLLISION_GROUP_ALL;
                }
                throw new UnsupportedOperationException("Unsupported collision group name '" + name + "'.");
            }
        });
        xmlTemplateManager.registerComponent(HealthBarStyleComponent.class, new XMLComponentConstructor<HealthBarStyleComponent>("healthBarStyle"){

            @Override
            public HealthBarStyleComponent construct(){
                return new HealthBarStyleComponent(HealthBarStyleComponent.HealthBarStyle.valueOf(element.getText().toUpperCase()));
            }
        });
    }
}
