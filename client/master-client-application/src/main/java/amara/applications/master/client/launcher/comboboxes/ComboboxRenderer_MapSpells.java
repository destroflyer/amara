
package amara.applications.master.client.launcher.comboboxes;

import amara.applications.ingame.entitysystem.components.general.DescriptionComponent;
import amara.applications.ingame.entitysystem.components.general.NameComponent;
import amara.applications.ingame.shared.maps.MapSpell;
import amara.libraries.entitysystem.templates.StaticEntityWorld;

public class ComboboxRenderer_MapSpells extends ComboboxRenderer_ToolTips {

    public ComboboxRenderer_MapSpells(MapSpell[] mapSpells) {
        String[] toolTips = new String[mapSpells.length];
        for (int i = 0; i < toolTips.length; i++) {
            int mapSpellEntity = StaticEntityWorld.loadTemplate(mapSpells[i].getEntityTemplate());
            NameComponent nameComponent = StaticEntityWorld.getEntityWorld().getComponent(mapSpellEntity, NameComponent.class);
            DescriptionComponent descriptionComponent = StaticEntityWorld.getEntityWorld().getComponent(mapSpellEntity, DescriptionComponent.class);
            String name = ((nameComponent != null) ? nameComponent.getName() : "Unnamed");
            String description = ((descriptionComponent != null) ? descriptionComponent.getDescription() : "No description available.");
            toolTips[i] = (name + ": " + description);
        }
        setToolTips(toolTips);
    }
}