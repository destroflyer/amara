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
public class SweepAndPrune<Hitbox extends BoundAabb>
{
    private ArrayList<SweepPoint> listX = new ArrayList<SweepPoint>();
    private ArrayList<SweepPoint> listY = new ArrayList<SweepPoint>();
    
    public void add(Hitbox hitbox)
    {
        if(!contains(hitbox))
        {
            listX.add(new SweepPoint(hitbox, true));
            listX.add(new SweepPoint(hitbox, false));
            listY.add(new SweepPoint(hitbox, true));
            listY.add(new SweepPoint(hitbox, false));
        }
    }
    public void remove(Hitbox hitbox)
    {
        for(int i = 0; i < listX.size();)
        {
            if(listX.get(i).getHitbox() == hitbox)
            {
                listX.remove(listX.get(i));
            } else i++;
        }
        for(int i = 0; i < listY.size();)
        {
            if(listY.get(i).getHitbox() == hitbox)
            {
                listY.remove(listY.get(i));
            } else i++;
        }
    }
    public boolean contains(Hitbox hitbox)
    {
        for(SweepPoint p: listX)
        {
            if(p.getHitbox().equals(hitbox)) return true;
        }
        return false;
    }
    public HashSet<Pair<Hitbox>> getAllPairs(Filter<Hitbox> filter)
    {
        sort();
        HashSet<Pair<Hitbox>> contacts = new HashSet<Pair<Hitbox>>(getPairs(listX, filter));
        contacts.retainAll(new HashSet<Pair<Hitbox>>(getPairs(listY, filter)));
        return contacts;
    }
    private ArrayList<Pair<Hitbox>> getPairs(ArrayList<SweepPoint> list, Filter<Hitbox> filter)
    {
        ArrayList<Pair<Hitbox>> contacts = new ArrayList<Pair<Hitbox>>();
        ArrayList<Hitbox> active = new ArrayList<Hitbox>();
        for(SweepPoint<Hitbox> p: list)
        {
            if(p.isStart())
            {
                for(Hitbox h: active)
                {
                    if(filter.pass(h, p.getHitbox()))
                    {
                        contacts.add(new Pair<Hitbox>(h, p.getHitbox()));
                    }
                }
                active.add(p.getHitbox());
            }
            else active.remove(p.getHitbox());
        }
        return contacts;
    }
    
    private void sort()
    {
        for(int i = 1; i < listX.size(); i++)
        {
            for(int j = i; j > 0 && listX.get(j - 1).getValueX() > listX.get(j).getValueX(); j--)
            {
                SweepPoint tmp = listX.get(j);
                listX.set(j, listX.get(j - 1));
                listX.set(j - 1, tmp);
            }
        }
        for(int i = 1; i < listY.size(); i++)
        {
            for(int j = i; j > 0 && listY.get(j - 1).getValueY() > listY.get(j).getValueY(); j--)
            {
                SweepPoint tmp = listY.get(j);
                listY.set(j, listY.get(j - 1));
                listY.set(j - 1, tmp);
            }
        }
    }
    
    public ArrayList<Hitbox> toList()
    {
        ArrayList<Hitbox> list = new ArrayList<Hitbox>();
        for(SweepPoint<Hitbox> p: listX)
        {
            if(p.isStart()) list.add(p.getHitbox());
        }
        return list;
    }
    
    public void clear()
    {
        listX.clear();
        listY.clear();
    }
}
