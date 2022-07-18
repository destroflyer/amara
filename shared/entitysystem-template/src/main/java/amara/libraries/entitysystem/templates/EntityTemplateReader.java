package amara.libraries.entitysystem.templates;

import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.EntityWrapper;

public abstract class EntityTemplateReader {

    public EntityWrapper createFromTemplate(EntityWorld entityWorld, String... templateNames) {
        int entity = entityWorld.createEntity();
        loadTemplates(entityWorld, entity, templateNames);
        return entityWorld.getWrapped(entity);
    }

    public void loadTemplates(EntityWorld entityWorld, int entity, String... templateNames) {
        for (String templateName : templateNames) {
            loadTemplate(entityWorld, entity, templateName);
        }
    }

    public void loadTemplate(EntityWorld entityWorld, int entity, String template) {
        loadTemplate(entityWorld, entity, EntityTemplate.parseTemplate(template));
    }

    protected abstract void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate template);
}
