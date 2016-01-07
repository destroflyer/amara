/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector3f;
import amara.game.entitysystem.*;

/**
 *
 * @author Carl
 */
public abstract class TopHUDAttachmentSystem extends SimpleHUDAttachmentSystem{

    public TopHUDAttachmentSystem(HUDAttachmentsSystem hudAttachmentsSystem, EntityHeightMap entityHeightMap, Class componentClass){
        super(hudAttachmentsSystem, componentClass);
        this.entityHeightMap = entityHeightMap;
    }
    private EntityHeightMap entityHeightMap;

    @Override
    protected Vector3f getWorldOffset(EntityWorld entityWorld, int entity){
        return entityHeightMap.getWorldOffset(entity);
    }
}
