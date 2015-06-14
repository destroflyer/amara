/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.game.entitysystem.templates;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;
import amara.Util;
import amara.game.entitysystem.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Carl
 */
public class XMLTemplateManager{

    public static XMLTemplateManager getInstance(){
        if(instance == null){
            instance = new XMLTemplateManager("/Templates/");
        }
        return instance;
    }
    
    private XMLTemplateManager(String resourcePath){
        this.resourcePath = resourcePath;
    }
    private static XMLTemplateManager instance;
    private String resourcePath;
    private HashMap<String, XMLComponentConstructor> xmlComponentConstructors = new HashMap<String, XMLComponentConstructor>();
    private Stack<HashMap<String, Integer>> cachedEntities = new Stack<HashMap<String, Integer>>();
    
    public <T> void registerComponent(Class<T> componentClass, XMLComponentConstructor<T> xmlComponentConstructor){
        xmlComponentConstructors.put(xmlComponentConstructor.getElementName(), xmlComponentConstructor);
        //registerMessageSerializer(componentClass);
    }
    
    public void loadTemplate(EntityWorld entityWorld, int entity, String templateName){
        String templateResourcePath = (resourcePath + templateName + ".xml");
        if(Util.existsResource(templateResourcePath)){
            try{
                InputStream inputStream = Util.getResourceInputStream(templateResourcePath);
                loadTemplate(entityWorld, entity, new SAXBuilder().build(inputStream));
            }catch(Exception ex){
                System.out.println("Error while loading template '" + templateName + "': " + ex.toString());
            }
        }
    }
    
    public void loadTemplate(EntityWorld entityWorld, int entity, Document document){
        Element templateElement = document.getRootElement();
        cachedEntities.push(new HashMap<String, Integer>(10));
        boolean isFirstChild = true;
        for(Object entityElementObject : templateElement.getChildren()){
            Element entityElement = (Element) entityElementObject;
            if(isFirstChild){
                loadEntity(entityWorld, entity, entityElement);
            }
            else{
                createEntity(entityWorld, entityElement);
            }
            isFirstChild = false;
        }
        cachedEntities.pop();
    }
    
    public int createEntity(EntityWorld entityWorld, Element entityElement){
        int entity = entityWorld.createEntity();
        loadEntity(entityWorld, entity, entityElement);
        return entity;
    }
    
    private void loadEntity(EntityWorld entityWorld, int entity, Element entityElement){
        String id = entityElement.getAttributeValue("id");
        if(id != null){
            getCachedEntities().put(id, entity);
        }
        String template = entityElement.getAttributeValue("template");
        if(template != null){
            EntityTemplate.loadTemplate(entityWorld, entity, template);
        }
        for(Object componentElementObject : entityElement.getChildren()){
            Element componentElement = (Element) componentElementObject;
            Object component = constructComponent(entityWorld, componentElement);
            if(component != null){
                entityWorld.setComponent(entity, component);
            }
        }
    }
    
    public HashMap<String, Integer> getCachedEntities(){
        return cachedEntities.lastElement();
    }
    
    private <T> T constructComponent(EntityWorld entityWorld, Element element){
        XMLComponentConstructor<T> xmlComponentConstructor = xmlComponentConstructors.get(element.getName());
        if(xmlComponentConstructor != null){
            xmlComponentConstructor.prepare(this, entityWorld, element);
            return xmlComponentConstructor.construct();
        }
        System.err.println("Unregistered component '" + element.getName() + "'");
        return null;
    }
}
