package amara.applications.ingame.client.systems.gui.deathrecap;

import lombok.Getter;

@Getter
public class DeathRecapSpell {

    public DeathRecapSpell(String name) {
        this.name = name;
    }
    private String name;
    private int count;
    private float totalPhysicalDamage;
    private float totalMagicDamage;

    public void addPhysicalDamage(float physicalDamage) {
        count++;
        totalPhysicalDamage += physicalDamage;
    }

    public void addMagicDamage(float magicDamage) {
        count++;
        totalMagicDamage += magicDamage;
    }
}
