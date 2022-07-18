package amara.libraries.entitysystem.templates;

import java.util.HashMap;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;
import lombok.Getter;

public class StaticEntityWorld {

    @Getter
    private static EntityWorld entityWorld = new EntityWorld();
    private static HashMap<String, Integer> cachedEntities = new HashMap<>();

    public static EntityWrapper loadTemplateWrapped(String template) {
        return entityWorld.getWrapped(loadTemplate(template));
    }

    public static int loadTemplate(String template) {
        Integer entity = cachedEntities.get(template);
        if (entity == null) {
            entity = entityWorld.createEntity();
            EntityTemplate.createReader().loadTemplate(entityWorld, entity, template);
            cachedEntities.put(template, entity);
        }
        return entity;
    }
}
