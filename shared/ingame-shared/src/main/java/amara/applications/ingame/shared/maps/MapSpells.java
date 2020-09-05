/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

/**
 *
 * @author Carl
 */
public class MapSpells{

    public MapSpells(String key, MapSpell mapSpell){
        this(new String[]{key}, mapSpell);
    }

    public MapSpells(String[] keys, MapSpell... mapSpells){
        this.keys = keys;
        this.mapSpells = mapSpells;
    }
    private String[] keys;
    private MapSpell[] mapSpells;

    public String[] getKeys(){
        return keys;
    }

    public MapSpell[] getMapSpells(){
        return mapSpells;
    }
}
