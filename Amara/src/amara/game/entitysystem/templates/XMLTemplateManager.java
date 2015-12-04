/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.game.entitysystem.templates;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
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
    private HashMap<String, Document> cachedDocuments = new HashMap<String, Document>();
    private String currentDirectory;
    private Stack<HashMap<String, Integer>> cachedEntities = new Stack<HashMap<String, Integer>>();
    private Stack<HashMap<String, String>> cachedValues = new Stack<HashMap<String, String>>();
    
    public <T> void registerComponent(Class<T> componentClass, XMLComponentConstructor<T> xmlComponentConstructor){
        xmlComponentConstructors.put(xmlComponentConstructor.getElementName(), xmlComponentConstructor);
    }
    
    public void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parameters){
        currentDirectory = "";
        String[] directories = templateName.split("/");
        for(int i=0;i<(directories.length - 1);i++){
            currentDirectory += directories[i] + "/";
        }
        String templateResourcePath = (resourcePath + templateName + ".xml");
        Document document = getDocument(templateResourcePath);
        if(document != null){
            loadTemplate(entityWorld, entity, document, parameters);
        }
    }
    
    private Document getDocument(String resourcePath){
        Document document = cachedDocuments.get(resourcePath);
        if((document == null) && Util.existsResource(resourcePath)){
            try{
                InputStream inputStream = Util.getResourceInputStream(resourcePath);
                document = new SAXBuilder().build(inputStream);
            }catch(Exception ex){
                System.err.println("Error while loading template resource '" + resourcePath + "'.");
                ex.printStackTrace();
            }
            cachedDocuments.put(resourcePath, document);
        }
        return document;
    }
    
    public void loadTemplate(EntityWorld entityWorld, int entity, Document document, String[] parameters){
        Element templateElement = document.getRootElement();
        cachedEntities.push(new HashMap<String, Integer>(10));
        HashMap<String, String> values = new HashMap<String, String>();
        String defaultParameterText = templateElement.getAttributeValue("defaultParameters");
        if(defaultParameterText != null){
            String[] defaultParameters = defaultParameterText.split(",");
            for(int i=0;i<defaultParameters.length;i++){
                values.put("parameter" + i, defaultParameters[i]);
            }
        }
        for(int i=0;i<parameters.length;i++){
            if(!parameters[i].equals("default")){
                values.put("parameter" + i, parameters[i]);
            }
        }
        cachedValues.push(values);
        boolean isFirstEntity = true;
        for(Object entityElementObject : templateElement.getChildren()){
            Element entityElement = (Element) entityElementObject;
            if(entityElement.getName().equals("entity")){
                if(isFirstEntity){
                    String id = entityElement.getAttributeValue("id");
                    if(id != null){
                        cachedEntities.lastElement().put(id, entity);
                    }
                    loadEntity(entityWorld, entity, entityElement);
                }
                else{
                    createEntity(entityWorld, entityElement);
                }
                isFirstEntity = false;
            }
            else if(entityElement.getName().equals("value")){
                String valueName = entityElement.getAttributeValue("name");
                String value = parseValue(entityElement.getText());
                cachedValues.lastElement().put(valueName, value);
            }
        }
        cachedEntities.pop();
        cachedValues.pop();
    }
    
    public int createEntity(EntityWorld entityWorld, Element entityElement){
        if(entityElement.getName().equals("empty")){
            return -1;
        }
        Integer entity = null;
        String id = entityElement.getAttributeValue("id");
        if(id != null){
            entity = cachedEntities.lastElement().get(id);
        }
        if(entity == null){
            entity = entityWorld.createEntity();
            if(id != null){
                cachedEntities.lastElement().put(id, entity);
            }
        }
        loadEntity(entityWorld, entity, entityElement);
        return entity;
    }
    
    private void loadEntity(EntityWorld entityWorld, int entity, Element entityElement){
        String templateXMLText = entityElement.getAttributeValue("template");
        if(templateXMLText != null){
            EntityTemplate.loadTemplate(entityWorld, entity, parseTemplate(templateXMLText));
        }
        for(Object componentElementObject : entityElement.getChildren()){
            Element componentElement = (Element) componentElementObject;
            Object component = constructComponent(entityWorld, componentElement);
            if(component != null){
                entityWorld.setComponent(entity, component);
            }
        }
    }
    
    public String parseTemplate(String templateXMLText){
        String template = templateXMLText.replaceAll("\\./", currentDirectory);
        if(template.matches("(.*)\\((.*)\\)")){
            int bracketStart = template.indexOf("(");
            int bracketEnd = template.indexOf(")");
            String[] parameters = template.substring(bracketStart + 1, bracketEnd).split(",");
            template = template.substring(0, bracketStart);
            for(String parameter : parameters){
                template += "," + parseValue(parameter);
            }
        }
        return template;
    }
    
    public String parseValue(String text){
        if(text.startsWith("#")){
            String entityID = text.substring(1);
            Integer entity = cachedEntities.lastElement().get(entityID);
            if(entity != null){
                return entity.toString();
            }
            System.err.println("Undefined entity id '" + entityID + "'.");
        }
        HashMap<String, String> values = cachedValues.lastElement();
        for(Entry<String, String> valueEntry : values.entrySet()){
            text = text.replaceAll("\\[" + valueEntry.getKey() + "\\]", valueEntry.getValue());
        }
        return text;
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
