/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.comboboxes;

import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.shared.maps.MapSpell;
import amara.libraries.entitysystem.templates.*;

/**
 *
 * @author Carl
 */
public class ComboboxRenderer_MapSpells extends ComboboxRenderer_ToolTips{
    
    public ComboboxRenderer_MapSpells(MapSpell[] mapSpells){
        String[] toolTips = new String[mapSpells.length];
        for(int i=0;i<toolTips.length;i++){
            int mapSpellEntity = StaticEntityWorld.loadTemplate(EntityTemplate.parseToOldTemplate(mapSpells[i].getEntityTemplate()));
            NameComponent nameComponent = StaticEntityWorld.getEntityWorld().getComponent(mapSpellEntity, NameComponent.class);
            DescriptionComponent descriptionComponent = StaticEntityWorld.getEntityWorld().getComponent(mapSpellEntity, DescriptionComponent.class);
            String name = ((nameComponent != null)?nameComponent.getName():"Unnamed");
            String description = ((descriptionComponent != null)?descriptionComponent.getDescription():"No description available.");
            toolTips[i] = (name + ": " + description);
        }
        setToolTips(toolTips);
    }
}