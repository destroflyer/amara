/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.skillshots;

import amara.game.entitysystem.EntitySystem;
import amara.game.entitysystem.EntityWorld;
import amara.game.entitysystem.components.physics.AntiGhostComponent;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.skillshots.SkillDamageComponent;
import amara.game.entitysystem.systems.physics.intersectionHelper.IntersectionInformant;
import intersections.Pair;

/**
 *
 * @author Philipp
 */
public class SkillDamageSystem implements EntitySystem
{
    private IntersectionInformant info;

    public SkillDamageSystem(IntersectionInformant info) {
        this.info = info;
    }
    
    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(Pair<Integer> pair: info.getEntries())
        {
            if(entityWorld.getCurrent().hasAllComponents(pair.getA(), SkillDamageComponent.class))
            {
                if(entityWorld.getCurrent().hasAllComponents(pair.getB()))
                {
                    applyDamage(entityWorld, pair.getA(), pair.getB());
                }
            }
            else if(entityWorld.getCurrent().hasAllComponents(pair.getB(), SkillDamageComponent.class))
            {
                if(entityWorld.getCurrent().hasAllComponents(pair.getA()))
                {
                    applyDamage(entityWorld, pair.getB(), pair.getA());
                }
            }
        }
    }
    
    private void applyDamage(EntityWorld entityWorld, int damageEntity, int damagedEntity)
    {
        //entityWorld.removeEntity(damageEntity);
        entityWorld.removeEntity(damagedEntity);
    }
}
