/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import amara.applications.ingame.client.systems.visualisation.colors.StealthColorizer;
import amara.applications.ingame.entitysystem.components.units.IsStealthedComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class StealthSystem implements EntitySystem {

    public StealthSystem(ColorizerSystem colorizerSystem) {
        this.colorizerSystem = colorizerSystem;
    }
    private ColorizerSystem colorizerSystem;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsStealthedComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(IsStealthedComponent.class)) {
            colorizerSystem.addColorizer(entity, new StealthColorizer());
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsStealthedComponent.class)) {
            colorizerSystem.removeColorizer(entity, StealthColorizer.class);
        }
    }
}
