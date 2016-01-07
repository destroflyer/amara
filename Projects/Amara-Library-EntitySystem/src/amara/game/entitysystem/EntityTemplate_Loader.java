/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem;


/**
 *
 * @author Carl
 */
public interface EntityTemplate_Loader{
    
    public abstract void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parametersText);
}
