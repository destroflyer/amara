/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.headless.applications;

import java.util.ArrayList;

/**
 *
 * @author Carl
 */
public class HeadlessAppStateManager{

    public HeadlessAppStateManager(HeadlessApplication application){
        this.application = application;
    }
    private HeadlessApplication application;
    private ArrayList<HeadlessAppState> states = new ArrayList<HeadlessAppState>();
    
    public void attach(HeadlessAppState state){
        states.add(state);
        state.initialize(this, application);
    }
    
    public void detach(HeadlessAppState state){
        states.remove(state);
    }
    
    public void update(float lastTimePerFrame){
        for(HeadlessAppState state : states){
            state.update(lastTimePerFrame);
        }
    }
    
    public <T extends HeadlessAppState> T getState(Class<T> stateClass){
        for(HeadlessAppState state : states){
            if(stateClass.isAssignableFrom(state.getClass())){
                return (T) state;
            }
        }
        return null;
    }
}
