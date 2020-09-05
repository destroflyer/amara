/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.controls;

import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Carl
 */
public class TextNotificationAttachmentControl extends AbstractControl{

    public TextNotificationAttachmentControl(float height, float duration, ColorRGBA color){
        this.height = height;
        this.duration = duration;
        this.color = color;
    }
    private float height;
    private float duration;
    private ColorRGBA color;
    private float y;
    private float alpha = 1;
    
    @Override
    protected void controlUpdate(float lastTimePerFrame){
        y += ((height / duration) * lastTimePerFrame);
        alpha -= ((1 / duration) * lastTimePerFrame);
        if(alpha > 0){
            BitmapText bitmapText = (BitmapText) ((Node) spatial).getChild(0);
            bitmapText.setColor(new ColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            bitmapText.setLocalTranslation(0, y, 0);
        }
        else{
            spatial.removeFromParent();
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort){
        
    }
}
