/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.entitysystem;

/**
 *
 * @author Philipp
 */
public interface EntitySystem {
    void update(EntityWorld entityWorld, float deltaSeconds);
}
