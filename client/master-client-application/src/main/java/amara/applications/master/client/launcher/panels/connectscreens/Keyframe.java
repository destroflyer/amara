/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.connectscreens;

/**
 *
 * @author Carl
 */
public class Keyframe{

    public Keyframe(float time, float value){
        this.time = time;
        this.value = value;
    }
    private float time;
    private float value;

    public float getTime(){
        return time;
    }

    public float getValue(){
        return value;
    }
}
