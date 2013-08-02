/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class Queue<T>{
    
    private LinkedList<T> elements = new LinkedList<T>();
    
    public void clear(){
        elements.clear();
    }
    
    public void add(T event){
        elements.add(event);
    }

    public Iterator<T> getIterator(){
        return elements.iterator();
    }
}
