/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network;

import java.lang.reflect.Field;
import java.util.ArrayList;
import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializer;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.commands.casting.*;
import amara.engine.applications.masterserver.server.network.messages.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.messages.*;
import amara.engine.network.messages.entitysystem.*;
import amara.engine.network.messages.protocol.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spawns.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.synchronizing.*;
import amara.game.entitysystem.synchronizing.fieldserializers.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.entitysystem.systems.physics.shapes.PolygonMath.*;
import amara.game.entitysystem.templates.*;
import org.jdom.Element;

/**
 *
 * @author Carl
 */
public class MessagesSerializer{
    
    public static void registerClasses(){
        Serializer.registerClasses(
            //These update classes have to be always registered first to keep the updater backwards compatible
            Message_GetUpdateFiles.class,
            Message_GetUpdateFile.class,
            Message_UpdateFilePart.class,
            Message_UpdateFiles.class,
                UpdateFile.class,
            
            Message_Ping.class,
            Message_Pong.class,
                
            Message_EditActiveCharacterSkin.class,
            Message_EditCharacterInventory.class,
            Message_EditUserMeta.class,
            Message_GameContents.class,
                GameCharacter.class,
                    GameCharacterSkin.class,
                Item.class,
            Message_GetGameContents.class,
            Message_GetPlayerProfileData.class,
            Message_GetPlayerStatus.class,
            Message_Login.class,
                AuthentificationInformation.class,
            Message_LoginResult.class,
            Message_Logout.class,
            Message_OwnedCharacters.class,
                OwnedGameCharacter.class,
            Message_OwnedItems.class,
                OwnedItem.class,
            Message_PlayerProfileData.class,
                PlayerProfileData.class,
            Message_PlayerProfileDataNotExistant.class,
            Message_PlayerStatus.class,
            
            Message_CreateLobby.class,
            Message_SetLobbyData.class,
                Lobby.class,
                LobbyPlayer.class,
                LobbyData.class,
            Message_SetLobbyPlayerData.class,
                LobbyPlayerData.class,
            Message_InviteLobbyPlayer.class,
            Message_LeaveLobby.class,
            Message_LobbyClosed.class,
            Message_KickLobbyPlayer.class,
            Message_LobbyUpdate.class,
            Message_StartGame.class,
            Message_GameCreated.class,
            
            Message_SendChatMessage.class,
            Message_ChatMessage.class,
            Message_Command.class,
                AutoAttackCommand.class,
                BuyItemCommand.class,
                Command.class,
                LearnSpellCommand.class,
                MoveCommand.class,
                SellItemCommand.class,
                ShowReactionCommand.class,
                StopCommand.class,
                    CastLinearSkillshotSpellCommand.class,
                    CastPositionalSkillshotSpellCommand.class,
                    CastSelfcastSpellCommand.class,
                    CastSingleTargetSpellCommand.class,
                    SpellIndex.class,
                UpgradeSpellCommand.class,
            Message_ClientConnection.class,
            Message_ClientDisconnection.class,
            Message_ClientInitialized.class,
            Message_EntityChanges.class,
            Message_GameInfo.class,
            Message_GameStarted.class,
            Message_GameOver.class,
            Message_PlayerAuthentification.class
        );
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
                if(shapeType.equals("regularCyclic")){
                    int edges = Integer.parseInt(childElement.getAttributeValue("edges"));
                    double radius = Double.parseDouble(childElement.getAttributeValue("radius"));
                    shape = new RegularCyclic(edges, radius);
                }
                else if(shapeType.equals("circle")){
                    double radius = Double.parseDouble(childElement.getAttributeValue("radius"));
                    shape = new Circle(radius);
                }
                else if(shapeType.equals("rectangle")){
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
                    double width = Double.parseDouble(childElement.getAttributeValue("width"));
                    double height = Double.parseDouble(childElement.getAttributeValue("height"));
                    shape = new Rectangle(x, y, width, height);
                }
                else if(shapeType.equals("point")){
                    Vector2D localPoint = new Vector2D();
                    String[] positionCoordinates = element.getText().split(",");
                    if(positionCoordinates.length > 1){
                        double localPointX = Double.parseDouble(xmlTemplateManager.parseValue(positionCoordinates[0]));
                        double localPointY = Double.parseDouble(xmlTemplateManager.parseValue(positionCoordinates[1]));
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
                String[] templates = element.getText().split(",");
                for(int i=0;i<templates.length;i++){
                    templates[i] = xmlTemplateManager.parseTemplate(templates[i]);
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
