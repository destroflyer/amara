package amara.libraries.entitysystem.templates;

import amara.libraries.entitysystem.EntityWorld;

public interface EntityTemplate_Loader {

    void loadTemplate(EntityWorld entityWorld, int entity, EntityTemplate template);
}
