/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class HUDAttachmentInfo{

    public HUDAttachmentInfo(int entity, String attachmentID, Vector3f worldOffset, Vector3f hudOffset, boolean followEntity){
        this.entity = entity;
        this.attachmentID = attachmentID;
        this.worldOffset = worldOffset;
        this.hudOffset = hudOffset;
        this.followEntity = followEntity;
    }
    private int entity;
    private String attachmentID;
    private Vector3f worldOffset;
    private Vector3f hudOffset;
    private boolean followEntity;
    private boolean wasHandled;

    public int getEntity(){
        return entity;
    }

    public String getAttachmentID(){
        return attachmentID;
    }

    public Vector3f getWorldOffset(){
        return worldOffset;
    }

    public Vector3f getHUDOffset(){
        return hudOffset;
    }

    public boolean isFollowEntity(){
        return followEntity;
    }

    public void setWasHandled(){
        this.wasHandled = true;
    }

    public boolean wasHandled(){
        return wasHandled;
    }
}
