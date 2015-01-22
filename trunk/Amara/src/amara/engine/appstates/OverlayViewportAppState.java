/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class OverlayViewportAppState extends BaseDisplayAppState{

    public OverlayViewportAppState(){
        rootNode.setCullHint(Spatial.CullHint.Never);
    }
    protected ViewPort viewPort;
    protected Node rootNode = new Node();

    @Override
    public void initialize(AppStateManager appStateManager, Application application){
        super.initialize(appStateManager, application);
        viewPort = mainApplication.getRenderManager().createPostView("OverlayViewPort", mainApplication.getCamera().clone());
        viewPort.setClearFlags(false, true, false);
        viewPort.attachScene(rootNode);
        rootNode.updateLogicalState(1);
        rootNode.updateGeometricState();
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getRenderManager().removePostView(viewPort);
    }

    @Override
    public void render(RenderManager renderManager){
        rootNode.updateGeometricState();
    }

    @Override
    public void update(float lastTimePerFrame){
        rootNode.updateLogicalState(lastTimePerFrame);
    }

    public ViewPort getViewPort(){
        return viewPort;
    }

    public Node getRootNode(){
        return rootNode;
    }
}
