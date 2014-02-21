/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications;

/**
 *
 * @author Carl
 */
public abstract class HeadlessAppState{
    
    public abstract void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application);
    
    public void update(float lastTimePerFrame){
        
    }
}
