/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui.objects;

/**
 *
 * @author Carl
 */
public class SpellInformation{

    public SpellInformation(int entity, String name, String description, float cooldown){
        this.entity = entity;
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
    }
    private int entity;
    private String name;
    private String description;
    private float cooldown;

    public int getEntity(){
        return entity;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public float getCooldown(){
        return cooldown;
    }
}
