/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation.controls;

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
public class GoldChangeAttachmentControl extends AbstractControl{

    public GoldChangeAttachmentControl(float height, float duration){
        this.height = height;
        this.duration = duration;
    }
    private float height;
    private float duration;
    private float y;
    private float alpha = 1;
    
    @Override
    protected void controlUpdate(float lastTimePerFrame){
        y += ((height / duration) * lastTimePerFrame);
        alpha -= ((1 / duration) * lastTimePerFrame);
        if(alpha > 0){
            BitmapText bitmapText = (BitmapText) ((Node) spatial).getChild(0);
            bitmapText.setColor(new ColorRGBA(1, 1, 0, alpha));
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
