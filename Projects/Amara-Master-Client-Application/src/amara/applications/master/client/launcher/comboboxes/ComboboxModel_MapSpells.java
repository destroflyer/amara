/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.comboboxes;

import javax.swing.ImageIcon;
import java.util.HashMap;
import amara.applications.ingame.client.systems.gui.DisplaySpellsImagesSystem;
import amara.applications.ingame.shared.maps.MapSpell;
import amara.core.files.FileAssets;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.entitysystem.templates.EntityTemplate;

/**
 *
 * @author Carl
 */
public class ComboboxModel_MapSpells extends SimpleComboboxModel<MapSpell, ImageIcon>{

    public ComboboxModel_MapSpells(MapSpell[] mapSpells){
        super(mapSpells);
    }
    private static final EntityWorld mapSpellsEntityWorld = new EntityWorld();
    private static final HashMap<String, ImageIcon> loadedIcons = new HashMap<String, ImageIcon>();
    
    @Override
    protected ImageIcon getItem(MapSpell mapSpell){
        ImageIcon icon = loadedIcons.get(mapSpell.getEntityTemplate());
        if(icon == null){
            int spellEntity = mapSpellsEntityWorld.createEntity();
            EntityTemplate.loadTemplate(mapSpellsEntityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
            String imageFilePath = DisplaySpellsImagesSystem.getSpellImagePath(mapSpellsEntityWorld, spellEntity);
            icon = FileAssets.getImageIcon(imageFilePath, 48, 48);
            loadedIcons.put(mapSpell.getEntityTemplate(), icon);
        }
        return icon;
    }
}
