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

    public MapSpells(MapSpell... mapSpells){
        this.mapSpells = mapSpells;
    }
    private MapSpell[] mapSpells;

    public MapSpell[] getMapSpells(){
        return mapSpells;
    }
}
