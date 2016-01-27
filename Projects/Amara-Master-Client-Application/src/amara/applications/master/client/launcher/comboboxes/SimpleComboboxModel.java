/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.comboboxes;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Carl
 */
public abstract class SimpleComboboxModel<E, T> implements ComboBoxModel<T>{

    public SimpleComboboxModel(E[] objects){
        this.objects = objects;
    }
    private E[] objects;
    private int selectedIndex;

    @Override
    public void setSelectedItem(Object anItem){
        for(int i=0;i<objects.length;i++){
            if(getItem(objects[i]).equals(anItem)){
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
    public T getElementAt(int index){
        if((index >= 0) && (index < objects.length)){
            return getItem(objects[index]);
        }
        return null;
    }
    
    protected abstract T getItem(E object);
    
    @Override
    public void addListDataListener(ListDataListener l){
        
    }

    @Override
    public void removeListDataListener(ListDataListener l){
        
    }
}
