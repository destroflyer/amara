/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Philipp
 */
public class IntersectionTracker<Element> {
    
    public void next() {
        next(new HashSet<Element>());
    }
    public void next(HashSet<Element> nextGroup) {
        last = current;
        current = nextGroup;
    }
    public void add(Element element) {
        current.add(element);
    }
    public void addAll(Collection<Element> elements) {
        current.addAll(elements);
    }
    
    public HashSet<Element> getLeavers() {
        HashSet<Element> leavers = new HashSet<Element>();
        leavers.addAll(last);
        leavers.removeAll(current);
        return leavers;
    }
    public HashSet<Element> getEntries() {
        HashSet<Element> entries = new HashSet<Element>();
        entries.addAll(current);
        entries.removeAll(last);
        return entries;
    }
    public HashSet<Element> getRepeaters() {
        HashSet<Element> repeaters = new HashSet<Element>();
        repeaters.addAll(current);
        repeaters.retainAll(last);
        return repeaters;
    }
    public HashSet<Element> getAll() {
        HashSet<Element> all = new HashSet<Element>();
        all.addAll(current);
        return all;
    }
    
    protected HashSet<Element> last = new HashSet<Element>();
    protected HashSet<Element> current = new HashSet<Element>();
}