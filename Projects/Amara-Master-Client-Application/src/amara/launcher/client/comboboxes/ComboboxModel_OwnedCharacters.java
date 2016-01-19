/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.comboboxes;

import amara.applications.master.network.messages.objects.*;

/**
 *
 * @author Carl
 */
public class ComboboxModel_OwnedCharacters extends SimpleComboboxModel<OwnedGameCharacter>{

    public ComboboxModel_OwnedCharacters(OwnedGameCharacter[] ownedCharacters){
        super(ownedCharacters);
    }
    
    @Override
    protected String getItemTitle(OwnedGameCharacter ownedCharacter){
        return getItemTitle(ownedCharacter.getCharacter());
    }

    public static String getItemTitle(GameCharacter character){
        return character.getTitle();
    }
}
