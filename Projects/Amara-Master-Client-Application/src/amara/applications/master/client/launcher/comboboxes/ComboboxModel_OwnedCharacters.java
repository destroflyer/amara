/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.comboboxes;

import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class ComboboxModel_OwnedCharacters extends SimpleComboboxModel<OwnedGameCharacter, String>{

    public ComboboxModel_OwnedCharacters(OwnedGameCharacter[] ownedCharacters){
        super(ownedCharacters);
    }
    
    @Override
    protected String getItem(OwnedGameCharacter ownedCharacter){
        return getItemTitle(ownedCharacter.getCharacter());
    }

    public static String getItemTitle(GameCharacter character){
        return character.getTitle();
    }
}
