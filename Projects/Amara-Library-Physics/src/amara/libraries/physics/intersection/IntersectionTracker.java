/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.intersection;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class IntersectionTracker<Element>
{
    private Set<Element> last = new HashSet<Element>();
    private Set<Element> current = new HashSet<Element>();
    
    public void next()
    {
        next(new HashSet<Element>());
    }
    public void next(Collection<Element> nextGroup)
    {
        last = current;
        current = new HashSet<Element>(nextGroup);
    }
    public void add(Element element)
    {
        current.add(element);
    }
    public void addAll(Collection<Element> elements)
    {
        current.addAll(elements);
    }
    
    public Set<Element> getLeavers()
    {
        HashSet<Element> leavers = new HashSet<Element>();
        leavers.addAll(last);
        leavers.removeAll(current);
        return leavers;
    }
    public Set<Element> getEntries()
    {
        HashSet<Element> entries = new HashSet<Element>();
        entries.addAll(current);
        entries.removeAll(last);
        return entries;
    }
    public Set<Element> getRepeaters()
    {
        HashSet<Element> repeaters = new HashSet<Element>();
        repeaters.addAll(current);
        repeaters.retainAll(last);
        return repeaters;
    }
    public Set<Element> getAll()
    {
        HashSet<Element> all = new HashSet<Element>();
        all.addAll(current);
        return all;
    }
}