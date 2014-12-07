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
public class Cinematic{

    public Cinematic(CinematicPart[] parts){
        this.parts = parts;
    }
    private CinematicPart[] parts;
    private float time;
    
    public void reset(){
        for(CinematicPart part : parts){
            part.setTriggered(false);
            part.getCinematicAction().reset();
        }
    }
    
    public void update(float lastTimePerFrame, DisplayApplication displayApplication){
        time += lastTimePerFrame;
        for(CinematicPart part : parts){
            if(part.isTriggered()){
                part.getCinematicAction().update(lastTimePerFrame, displayApplication);
            }
            else if(time >= part.getStartTime()){
                part.getCinematicAction().trigger(displayApplication);
                part.setTriggered(true);
            }
        }
    }
    
    public void stop(DisplayApplication displayApplication){
        for(CinematicPart part : parts){
            if(part.isTriggered() && (!part.getCinematicAction().isFinished())){
                part.getCinematicAction().finish(displayApplication);
            }
        }
    }
    
    public boolean isFinished(){
        for(CinematicPart part : parts){
            if(!part.getCinematicAction().wasFinished()){
                return false;
            }
        }
        return true;
    }
}
