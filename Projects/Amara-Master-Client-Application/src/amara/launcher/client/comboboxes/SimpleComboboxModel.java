/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.comboboxes;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import amara.engine.applications.masterserver.server.protocol.*;

/**
 *
 * @author Carl
 */
public abstract class SimpleComboboxModel<E> implements ComboBoxModel<String>{

    public SimpleComboboxModel(E[] objects){
        this.objects = objects;
    }
    private E[] objects;
    private int selectedIndex;

    @Override
    public void setSelectedItem(Object anItem){
        for(int i=0;i<objects.length;i++){
            if(getItemTitle(objects[i]).equals(anItem)){
                selectedIndex = i;
                break;
            }
        }
    }

    @Override
    public Object getSelectedItem(){
        return getElementAt(selectedIndex);
    }

    @Override
    public int getSize(){
        return objects.length;
    }

    @Override
    public String getElementAt(int index){
        return getItemTitle(objects[index]);
    }
    
    protected abstract String getItemTitle(E object);
    
    @Override
    public void addListDataListener(ListDataListener l){
        
    }

    @Override
    public void removeListDataListener(ListDataListener l){
        
    }
}
