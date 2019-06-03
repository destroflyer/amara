/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.templates;

import java.util.ArrayList;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class EntityTemplate{
    
    private static ArrayList<EntityTemplate_Loader> loaders = new ArrayList<>();
    
    public static void addLoader(EntityTemplate_Loader loader){
        loaders.add(loader);
    }
    
    public static EntityWrapper createFromTemplate(EntityWorld entityWorld, String... templateNames){
        int entity = entityWorld.createEntity();
        loadTemplates(entityWorld, entity, templateNames);
        return entityWorld.getWrapped(entity);
    }
    
    public static void loadTemplates(EntityWorld entityWorld, int entity, String... templateNames){
        for(int i=0;i<templateNames.length;i++){
            loadTemplate(entityWorld, entity, templateNames[i]);
        }
    }
    
    public static String parseToOldTemplate(String template){
        if(template.matches("(.*)\\((.*)\\)")){
            int bracketStart = template.indexOf("(");
            int bracketEnd = template.indexOf(")");
            String[] parameters = template.substring(bracketStart + 1, bracketEnd).split(",");
            template = template.substring(0, bracketStart);
            for(String parameter : parameters){
                template += "," + parameter;
            }
        }
        return template;
    }
    
    public static void loadTemplate(EntityWorld entityWorld, int entity, String template){
        String[] parts = template.split(",");
        String templateName = parts[0];
        String[] parameters = new String[parts.length - 1];
        for(int i=0;i<parameters.length;i++){
            parameters[i] = parts[1 + i];
        }
        loadTemplate(entityWorld, entity, templateName, parameters);
    }
    
    public static void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parametersText){
        for(EntityTemplate_Loader loader : loaders){
            loader.loadTemplate(entityWorld, entity, templateName, parametersText);
        }
    }
}
