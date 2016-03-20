/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.panels.loginscreens;

import java.awt.Graphics2D;

/**
 *
 * @author Carl
 */
public abstract class SwingLoginScreen{

    protected int width;
    protected int height;
    private boolean isInitialized;
    protected float time;
    protected float loopDuration = -1;
    
    public void update(float lastTimePerFrame){
        if(!isInitialized){
            initialize();
            isInitialized = true;
        }
        time += lastTimePerFrame;
        if((loopDuration != -1) && (time >= loopDuration)){
            time = 0;
        }
    }
    
    public void initialize(){
        
    }
    
    public abstract void paint(Graphics2D graphics);
    
    public void close(){
        
    }
    
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
}
