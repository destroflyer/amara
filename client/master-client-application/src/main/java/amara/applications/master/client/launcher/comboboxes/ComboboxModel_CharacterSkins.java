/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.comboboxes;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import amara.applications.master.network.messages.objects.GameCharacterSkin;

/**
 *
 * @author Carl
 */
public class ComboboxModel_CharacterSkins implements ComboBoxModel<String>{

    public ComboboxModel_CharacterSkins(GameCharacterSkin[] characterSkins){
        this.characterSkins = characterSkins;
    }
    private GameCharacterSkin[] characterSkins;
    private int selectedIndex;

    @Override
    public void setSelectedItem(Object anItem){
        if(getItemTitle(null).equals(anItem)){
            selectedIndex = 0;
        }
        else{
            for(int i=0;i<characterSkins.length;i++){
                if(getItemTitle(characterSkins[i]).equals(anItem)){
                    selectedIndex = (i + 1);
                    break;
                }
            }
        }
    }

    @Override
    public Object getSelectedItem(){
        return getElementAt(selectedIndex);
    }

    @Override
    public int getSize(){
        return (characterSkins.length + 1);
    }

    @Override
    public String getElementAt(int index){
        GameCharacterSkin characterSkin = null;
        if(index != 0){
            characterSkin = characterSkins[index - 1];
        }
        return getItemTitle(characterSkin);
    }
    
    private String getItemTitle(GameCharacterSkin characterSkin){
        if(characterSkin == null){
            return "Default";
        }
        return characterSkin.getTitle();
    }
    
    @Override
    public void addListDataListener(ListDataListener l){
        
    }

    @Override
    public void removeListDataListener(ListDataListener l){
        
    }
}
