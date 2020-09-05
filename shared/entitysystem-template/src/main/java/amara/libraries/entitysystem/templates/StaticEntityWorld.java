/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.templates;

import java.util.HashMap;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;

/**
 *
 * @author Carl
 */
public class StaticEntityWorld{
    
    private static EntityWorld entityWorld = new EntityWorld();
    private static HashMap<String, Integer> cachedEntities = new HashMap<>();
    
    public static EntityWrapper loadTemplateWrapped(String template){
        return entityWorld.getWrapped(loadTemplate(template));
    }
    
    public static int loadTemplate(String template){
        Integer entity = cachedEntities.get(template);
        if(entity == null){
            entity = entityWorld.createEntity();
            EntityTemplate.loadTemplate(entityWorld, entity, template);
            cachedEntities.put(template, entity);
        }
        return entity;
    }

    public static EntityWorld getEntityWorld(){
        return entityWorld;
    }
}
