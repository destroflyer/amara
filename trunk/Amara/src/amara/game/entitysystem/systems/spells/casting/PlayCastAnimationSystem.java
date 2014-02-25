/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;

/**
 *
 * @author Carl
 */
public class PlayCastAnimationSystem extends SimpleCastingSystem{

    @Override
    public void onCasting(EntityWorld entityWorld, int casterEntity, int spellEntity){
        CastAnimationComponent castAnimationComponent = entityWorld.getComponent(spellEntity, CastAnimationComponent.class);
        if(castAnimationComponent != null){
            entityWorld.setComponent(castAnimationComponent.getAnimationEntity(), new RemainingLoopsComponent(1));
            entityWorld.setComponent(casterEntity, new AnimationComponent(castAnimationComponent.getAnimationEntity()));
        }
    }
}
