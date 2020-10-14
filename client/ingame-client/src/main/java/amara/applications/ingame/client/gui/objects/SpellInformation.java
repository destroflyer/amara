package amara.applications.ingame.client.gui.objects;

public class SpellInformation {

    public SpellInformation(int entity, String name, String description, float cooldown, Float manaCost) {
        this.entity = entity;
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
    }
    private int entity;
    private String name;
    private String description;
    private float cooldown;
    private Float manaCost;

    public int getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getCooldown() {
        return cooldown;
    }

    public Float getManaCost() {
        return manaCost;
    }
}
