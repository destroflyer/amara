/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui.objects;

/**
 *
 * @author Carl
 */
public class SpellInformation{

    public SpellInformation(String name, String description, float cooldown){
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
    }
    private String name;
    private String description;
    private float cooldown;

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
