package amara.applications.ingame.client.gui.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpellInformation {
    private int entity;
    private String name;
    private String description;
    private Float cooldown;
    private Float manaCost;
}
