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

    public ComboboxModel_MapSpells(MapSpell[] mapSpells, int iconSize){
        super(mapSpells);
        this.iconSize = iconSize;
    }
    private int iconSize;
    private static final EntityWorld mapSpellsEntityWorld = new EntityWorld();
    private static final HashMap<String, ImageIcon> loadedIcons = new HashMap<String, ImageIcon>();
    private static final int[] tmpSpellEntities = new int[1];
    
    @Override
    protected ImageIcon getItem(MapSpell mapSpell){
        return getMapSpellIcon(mapSpell, iconSize);
    }
    
    public static ImageIcon getMapSpellIcon(MapSpell mapSpell, int iconSize){
        String key = (mapSpell.getEntityTemplate() + "_" + iconSize);
        ImageIcon icon = loadedIcons.get(key);
        if(icon == null){
            int spellEntity = mapSpellsEntityWorld.createEntity();
            EntityTemplate.loadTemplate(mapSpellsEntityWorld, spellEntity, EntityTemplate.parseToOldTemplate(mapSpell.getEntityTemplate()));
            tmpSpellEntities[0] = spellEntity;
            String imageFilePath = DisplaySpellsImagesSystem.getSpellImageFilePath(mapSpellsEntityWorld, tmpSpellEntities, 0);
            icon = FileAssets.getImageIcon(imageFilePath, iconSize, iconSize);
            loadedIcons.put(key, icon);
        }
        return icon;
    }
}
