/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics;

import amara.engine.applications.DisplayApplication;

/**
 *
 * @author Carl
 */
public abstract class CinematicAction{
    
    private boolean wasFinished;
    
    public void reset(){
        wasFinished = false;
    }
    
    public abstract void trigger(DisplayApplication displayApplication);
    
    public void update(float lastTimePerFrame, DisplayApplication displayApplication){
        if((!wasFinished) && isFinished()){
            finish(displayApplication);
            wasFinished = true;
        }
    }
    
    protected boolean isFinished(){
        return true;
    }
    
    public void finish(DisplayApplication displayApplication){
        
    }

    public boolean wasFinished(){
        return wasFinished;
    }
}
