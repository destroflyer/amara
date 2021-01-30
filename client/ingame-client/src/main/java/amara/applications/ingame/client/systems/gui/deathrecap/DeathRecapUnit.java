package amara.applications.ingame.client.systems.gui.deathrecap;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DeathRecapUnit {

    public DeathRecapUnit(String name) {
        this.name = name;
        spells = new LinkedList<>();
    }
    private String name;
    private List<DeathRecapSpell> spells;

    public void addSpell(DeathRecapSpell spell) {
        spells.add(spell);
    }
}
