/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem.templates;

import amara.libraries.entitysystem.EntityWorld;


/**
 *
 * @author Carl
 */
public interface EntityTemplate_Loader{
    
    public abstract void loadTemplate(EntityWorld entityWorld, int entity, String templateName, String[] parametersText);
}
