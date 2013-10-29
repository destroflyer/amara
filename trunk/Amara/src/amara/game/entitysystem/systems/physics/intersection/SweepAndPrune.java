/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class SweepAndPrune<Hitbox extends HasShape> {
    
    public void add(Hitbox h) {
        if(!contains(h)) {
            listX.add(new SweepPoint(h, true));
            listX.add(new SweepPoint(h, false));
            listY.add(new SweepPoint(h, true));
            listY.add(new SweepPoint(h, false));
        }
    }
    public void remove(Hitbox h) {
        for(int i = 0; i < listX.size();) {
            if(listX.get(i).getHitbox() == h) {
                listX.remove(listX.get(i));
            } else i++;
        }
        for(int i = 0; i < listY.size();) {
            if(listY.get(i).getHitbox() == h) {
                listY.remove(listY.get(i));
            } else i++;
        }
    }
    public boolean contains(Hitbox h) {
        for(SweepPoint p: listX) {
            if(p.getHitbox().equals(h)) return true;
        }
        return false;
    }
//    public HashSet<Pair<Hitbox>> checkAll() {
//        sort();
//        HashSet<Pair<Hitbox>> contacts = check(listX);
//        contacts.retainAll(check(listY));
//        return contacts;
//    }
//    protected HashSet<Pair<Hitbox>> contactsX = new HashSet<>();
//    protected HashSet<Pair<Hitbox>> contactsY = new HashSet<>();
    
//    public HashSet<Hitbox> getAllPartners(Hitbox h) {
//        sort();
//        HashSet<Hitbox> contacts = new HashSet<Hitbox>(getPartners(listX, h));
//        contacts.retainAll(new HashSet<Hitbox>(getPartners(listY, h)));
//        return contacts;
//    }
    public HashSet<Pair<Hitbox>> getAllPairs() {
        return getAllPairs(new Filter<Hitbox>());
    }
    public HashSet<Pair<Hitbox>> getAllPairs(Filter<Hitbox> filter) {
        sort();
//        contactsX.clear();
//        contactsX.addAll(check(listX));
//        contactsY.clear();
//        contactsY.addAll(check(listY));
//        contactsX.retainAll(contactsY);
//        return contactsX;
        HashSet<Pair<Hitbox>> contacts = new HashSet<Pair<Hitbox>>(getPairs(listX, filter));
        contacts.retainAll(new HashSet<Pair<Hitbox>>(getPairs(listY, filter)));
        return contacts;
    }
//    protected HashSet<Pair<Hitbox>> check(ArrayList<SweepPoint> list) {
//        HashSet<Pair<Hitbox>> contacts = new HashSet<>();
//        ArrayList<Hitbox> active = new ArrayList<>();
//        for(SweepPoint<Hitbox> p: list) {
//            if(p.isStart()) {
//                for(Hitbox h: active) {
//                    contacts.add(new Pair<Hitbox>(h, p.getHitbox()));
//                }
//                active.add(p.getHitbox());
//            }
//            else active.remove(p.getHitbox());
//        }
//        return contacts;
//    }
    protected ArrayList<Pair<Hitbox>> getPairs(ArrayList<SweepPoint> list, Filter<Hitbox> filter) {
        ArrayList<Pair<Hitbox>> contacts = new ArrayList<Pair<Hitbox>>();
        ArrayList<Hitbox> active = new ArrayList<Hitbox>();
        for(SweepPoint<Hitbox> p: list) {
            if(p.isStart()) {
                for(Hitbox h: active) {
                    if(filter.pass(h, p.getHitbox())) {
                        if(h.getShape().intersects(p.getHitbox().getShape())) {
                            contacts.add(new Pair<Hitbox>(h, p.getHitbox()));
                        }
                    }
                }
                active.add(p.getHitbox());
            }
            else active.remove(p.getHitbox());
        }
        return contacts;
    }
//    protected ArrayList<Hitbox> getPartners(ArrayList<SweepPoint> list, Hitbox hitbox) {
//        ArrayList<Hitbox> contacts = new ArrayList<>();
//        ArrayList<Hitbox> active = new ArrayList<>();
//        for(SweepPoint<Hitbox> p: list) {
//            if(p.isStart()) {
//                for(Hitbox h: active) {
//                    if(h == hitbox && p.getHitbox() != hitbox) contacts.add(p.getHitbox());
//                    else if(h != hitbox && p.getHitbox() == hitbox) contacts.add(h);
//                }
//                active.add(p.getHitbox());
//            }
//            else active.remove(p.getHitbox());
//        }
//        return contacts;
//    }
    
    protected void sort() {
        for(int i = 1; i < listX.size(); i++) {
            for(int j = i; j > 0 && listX.get(j - 1).getValueX() > listX.get(j).getValueX(); j--) {
                SweepPoint tmp = listX.get(j);
                listX.set(j, listX.get(j - 1));
                listX.set(j - 1, tmp);
            }
        }
        for(int i = 1; i < listY.size(); i++) {
            for(int j = i; j > 0 && listY.get(j - 1).getValueY() > listY.get(j).getValueY(); j--) {
                SweepPoint tmp = listY.get(j);
                listY.set(j, listY.get(j - 1));
                listY.set(j - 1, tmp);
            }
        }
    }
//    public void initSort() {
//        Collections.sort(listX, new Comparator<SweepPoint>() {
//            @Override
//            public int compare(SweepPoint o1, SweepPoint o2) {
//                return (int)Math.signum(o1.getValueX() - o2.getValueX());
//            }
//        });
//        Collections.sort(listY, new Comparator<SweepPoint>() {
//            @Override
//            public int compare(SweepPoint o1, SweepPoint o2) {
//                return (int)Math.signum(o1.getValueY() - o2.getValueY());
//            }
//        });
//    }
    
    public ArrayList<Hitbox> asList() {
        ArrayList<Hitbox> list = new ArrayList<Hitbox>();
        for(SweepPoint<Hitbox> p: listX) {
            if(p.isStart()) list.add(p.getHitbox());
        }
        return list;
    }
    
    public void clear() {
        listX.clear();
        listY.clear();
    }
    
    protected ArrayList<SweepPoint> listX = new ArrayList<SweepPoint>();
    protected ArrayList<SweepPoint> listY = new ArrayList<SweepPoint>();
}
