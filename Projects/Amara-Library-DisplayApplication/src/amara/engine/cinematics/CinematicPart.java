/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.cinematics;

/**
 *
 * @author Carl
 */
public class CinematicPart{

    public CinematicPart(float startTime, CinematicAction cinematicAction){
        this.startTime = startTime;
        this.cinematicAction = cinematicAction;
    }
    private float startTime;
    private CinematicAction cinematicAction;
    private boolean isTriggered;

    public float getStartTime(){
        return startTime;
    }

    public CinematicAction getCinematicAction(){
        return cinematicAction;
    }

    public void setTriggered(boolean isTriggered){
        this.isTriggered = isTriggered;
    }

    public boolean isTriggered(){
        return isTriggered;
    }
}
