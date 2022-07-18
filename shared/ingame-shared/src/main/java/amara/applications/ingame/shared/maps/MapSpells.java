package amara.applications.ingame.shared.maps;

import lombok.Getter;

public class MapSpells {

    public MapSpells(String key, MapSpell mapSpell) {
        this(new String[]{key}, mapSpell);
    }

    public MapSpells(String[] keys, MapSpell... mapSpells) {
        this.keys = keys;
        this.mapSpells = mapSpells;
    }
    @Getter
    private String[] keys;
    @Getter
    private MapSpell[] mapSpells;
}
